package com.mygdx.pmd.Controller;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;
import com.mygdx.pmd.Comparators.PokemonDistanceComparator;
import com.mygdx.pmd.Enumerations.Key;
import com.mygdx.pmd.Enumerations.PokemonName;
import com.mygdx.pmd.Enumerations.Turn;
import com.mygdx.pmd.Enumerations.State;
import com.mygdx.pmd.Interfaces.Renderable;
import com.mygdx.pmd.Interfaces.Updatable;
import com.mygdx.pmd.Model.FloorComponent.Floor;
import com.mygdx.pmd.Model.FloorComponent.Room;
import com.mygdx.pmd.Model.Generator.FloorGenerator;
import com.mygdx.pmd.Model.Pokemon.Pokemon;
import com.mygdx.pmd.Model.Pokemon.PokemonMob;
import com.mygdx.pmd.Model.Pokemon.PokemonPlayer;
import com.mygdx.pmd.Model.TileType.StairTile;
import com.mygdx.pmd.Model.TileType.Tile;
import com.mygdx.pmd.Screen.DungeonScreen;
import com.mygdx.pmd.utils.Entity;
import com.mygdx.pmd.utils.Projectile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Controller {
    public DungeonScreen controllerScreen;
    public boolean isFullView = false;

    ArrayList<Renderable> renderList;
    ArrayList<Room> roomList;
    public ArrayList<Pokemon> pokemonList;
    ArrayList<Pokemon> updatePokemonList;
    ArrayList<Pokemon> enemyList;
    public Array<Projectile> projectiles;

    private int pokemonCounter;

    public ArrayList<Tile> loadedArea;
    public ArrayList<Tile> renderableArea;

    private int windowWidth;
    private int windowLength;

    public int playerXOffset;
    public int playerYOffset;

    public int renderableRowOffset = 100;
    public int renderableColOffset = 100;

    private int windowRows;
    private int windowCols;

    private PokemonPlayer pokemonPlayer;

    public boolean isShadowed;

    private Tile[][] tileBoard;
    private FloorGenerator floorGenerator;


    public boolean isUpPressed;
    public boolean isDownPressed;
    public boolean isRightPressed;
    public boolean isLeftPressed;
    public boolean isSPressed;
    public boolean isSpacePressed;
    public boolean isAPressed;
    public boolean isBPressed;
    public boolean isTPressed;

    public boolean keyFrozen = false;

    Floor currentFloor;

    ArrayList<Updatable> updateList = new ArrayList<Updatable>();
    ArrayList<Tile> roomTileList;
    public Pokemon priorityPokemon;
    private int pokemonListCounter;

    public Controller(DungeonScreen controllerScreen) {
        this.controllerScreen = controllerScreen;
        renderList = new ArrayList<Renderable>();
        pokemonList = new ArrayList<Pokemon>();
        enemyList = new ArrayList<Pokemon>();
        updatePokemonList = new ArrayList<Pokemon>();
        projectiles = new Array<Projectile>();

        this.windowLength = 2 * 500;
        this.windowWidth = 2 * 500;

        this.windowRows = windowLength / Tile.size;
        this.windowCols = windowWidth / Tile.size;

        this.isShadowed = false;

        floorGenerator = new FloorGenerator(windowRows, windowCols, 10, this);
        floorGenerator.generateFloor();
        currentFloor = floorGenerator.getFloor();

        tileBoard = currentFloor.getTileBoard();
        roomTileList = currentFloor.getRoomTileList();

        loadedArea = new ArrayList<Tile>();
        renderableArea = new ArrayList<Tile>();

        this.loadPokemon();
        priorityPokemon = this.getPokemonPlayer();
    }

    public void updatePlayerOffset() {
        if (pokemonPlayer != null) {
            playerXOffset = (pokemonPlayer.getX() - DungeonScreen.APP_WIDTH / 2);
            playerYOffset = (pokemonPlayer.getY() - DungeonScreen.APP_HEIGHT / 2);
        }
    }

    public void loadPokemon() {
        XmlReader xmlReader = new XmlReader();
        XmlReader.Element root = null;
        try {
            root = xmlReader.parse(Gdx.files.internal("utils/PokemonStorage.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Array<XmlReader.Element> elementList = root.getChildrenByName("Pokemon");
        XmlReader.Element player = root.getChildByName("PokemonPlayer");
        PokemonPlayer pokemonPlayer = new PokemonPlayer(0, 0, this, true, Enum.valueOf(PokemonName.class, player.get("name")));
        this.addEntity(pokemonPlayer);
        for (XmlReader.Element e : elementList) {
            PokemonName pokemonName = Enum.valueOf(PokemonName.class, e.get("name"));
            PokemonMob pokemonMob = new PokemonMob(0, 0, this, true, pokemonName, State.friendly);
            this.addEntity(pokemonMob);
        }
        this.randomizeAllPokemonLocation();
    }

    public PokemonPlayer getPokemonPlayer() {
        return pokemonPlayer;
    }

    public ArrayList<Renderable> getRenderList() {
        return renderList;
    }

    public Tile[][] getTileBoard() {
        return tileBoard;
    }

    public Floor getCurrentFloor() {
        return currentFloor;
    }

    public void randomizePokemonLocation(Pokemon pokemon) {

        int rand = (int) (Math.random() * currentFloor.getRoomTileList().size());

        Tile random = currentFloor.getRoomTileList().get(rand);

        if (!(random instanceof StairTile) && random.getEntityList().size() == 0) {
            pokemon.setCurrentTile(random);
            random.addEntity(pokemon);

            pokemon.setX(random.x);
            pokemon.setY(random.y);

        }
        this.updatePlayerOffset();
        this.updateLoadedArea();
    }

    public void randomizeAllPokemonLocation() {
        for (Pokemon pokemon : pokemonList)
            this.randomizePokemonLocation(pokemon);
    }

    //TODO fix this method
    public void update() {
        this.tileBoard = currentFloor.getTileBoard();
        this.updateKeys();
        this.updatePlayerOffset();
        Collections.sort(pokemonList, new PokemonDistanceComparator(this.getPokemonPlayer()));

        for (Pokemon pokemon : pokemonList) {
            if (pokemonList.get(pokemonListCounter).getTurnState() == Turn.COMPLETE) {
                if (pokemonList.get(pokemonListCounter) instanceof PokemonPlayer)
                    this.updateLoadedArea();
                pokemonListCounter++;
                if (pokemonListCounter > pokemonList.size() - 1)
                    pokemonListCounter = 0;
                pokemonList.get(pokemonListCounter).setTurnState(Turn.WAITING);
            }
            pokemon.update();

        }

        for(int i = 0; i< projectiles.size; i++){
            projectiles.get(i).update();
            if(projectiles.get(i).hasHit)
                projectiles.removeIndex(i);
        }
    }

    public void updateKeys() {
        if (!keyFrozen) {
            if (DungeonScreen.keys.get(Input.Keys.UP).get()) {
                isUpPressed = true;
            } else isUpPressed = false;

            if (DungeonScreen.keys.get(Input.Keys.DOWN).get()) {
                isDownPressed = true;
            } else isDownPressed = false;

            if (DungeonScreen.keys.get(Input.Keys.LEFT).get()) {
                isLeftPressed = true;
            } else isLeftPressed = false;

            if (DungeonScreen.keys.get(Input.Keys.RIGHT).get()) {
                isRightPressed = true;
            } else isRightPressed = false;

            if (DungeonScreen.keys.get(Input.Keys.S).get()) {
                isSPressed = true;
            } else isSPressed = false;

            if (DungeonScreen.keys.get(Input.Keys.SPACE).get()) {
                isSpacePressed = true;
            } else isSpacePressed = false;

            if (DungeonScreen.keys.get(Input.Keys.A).get()) {
                isAPressed = true;
            } else isAPressed = false;

            if (DungeonScreen.keys.get(Input.Keys.B).get()) {
                isBPressed = true;
            } else isBPressed = false;

            if(DungeonScreen.keys.get(Input.Keys.T).get()) {
                isTPressed = true;
            } else isTPressed = false;
        }
    }

    public boolean isKeyPressed(Key key) {
        return DungeonScreen.keys.get(key.getValue()).get();
    }

    public boolean isKeyPressed() {
        return isDownPressed || isLeftPressed || isRightPressed || isUpPressed || isAPressed;
    }

    public boolean isUpPressed() {
        return isUpPressed;
    }

    public boolean isSPressed() {
        return isSPressed;
    }

    public boolean isDownPressed() {
        return isDownPressed;
    }

    public boolean isRightPressed() {
        return isRightPressed;
    }

    public boolean isLeftPressed() {
        return isLeftPressed;
    }


    public boolean isSpacePressed() {
        return isSpacePressed;
    }

    public void freezeKeys() {
        keyFrozen = true;
    }

    public void addEntity(Entity entity) {
        if (entity instanceof Renderable) {
            renderList.add((Renderable) entity);
        }

        if (entity instanceof Updatable) {
            updateList.add((Updatable) entity);
        }

        if (entity instanceof PokemonPlayer) {
            pokemonPlayer = (PokemonPlayer) entity;
            priorityPokemon = pokemonPlayer;
        }

        if (entity instanceof Pokemon) {
            pokemonList.add((Pokemon) entity);

            if (!(entity instanceof PokemonPlayer))
                updatePokemonList.add((Pokemon) entity);
        }

        if (entity instanceof PokemonMob && ((PokemonMob) entity).state == State.aggressive) {
            enemyList.add((Pokemon) entity);
        }
    }

    public void unfreezeKeys() {
        keyFrozen = false;
    }

    public void removeEntity(Entity entity) {

    }

    public void updateLoadedArea() {
        ArrayList<Tile> tempLoadedArea = new ArrayList<Tile>();
        ArrayList<Tile> tempRenderableArea = new ArrayList<Tile>();

        int loadedColOffset;
        int loadedRowOffset;

        if (true) {
            loadedColOffset = 15;
            loadedRowOffset = 15;

            renderableColOffset = 10;
            renderableRowOffset = 10;
        } else {
            loadedColOffset = 15;
            loadedRowOffset = 15;

            renderableColOffset = 10;
            renderableRowOffset = 10;
        }

        for (int r = pokemonPlayer.getCurrentTile().row - loadedRowOffset; r <= pokemonPlayer.getCurrentTile().row + loadedRowOffset; r++) {
            for (int c = pokemonPlayer.getCurrentTile().col - loadedColOffset; c <= pokemonPlayer.getCurrentTile().col + loadedColOffset; c++) { //set this up so it gets it from the controller controller.getLeftRenderBound() etc.
                try {
                    tempLoadedArea.add(tileBoard[r][c]);

                    if (r > pokemonPlayer.getCurrentTile().row - renderableRowOffset && r < pokemonPlayer.getCurrentTile().row + renderableRowOffset)
                        if (c > pokemonPlayer.getCurrentTile().col - renderableColOffset && c < pokemonPlayer.getCurrentTile().col + renderableColOffset)
                            tempRenderableArea.add(tileBoard[r][c]);

                } catch (ArrayIndexOutOfBoundsException e) {
                }
            }
        }
        loadedArea = tempLoadedArea;
        renderableArea = tempRenderableArea;
    }
}