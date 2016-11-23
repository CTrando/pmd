package com.mygdx.pmd.Controller;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;
import com.mygdx.pmd.Comparators.PokemonDistanceComparator;
import com.mygdx.pmd.Enumerations.*;
import com.mygdx.pmd.Interfaces.Renderable;
import com.mygdx.pmd.Interfaces.Updatable;
import com.mygdx.pmd.Model.Factory.PokemonFactory;
import com.mygdx.pmd.Model.FloorComponent.Floor;
import com.mygdx.pmd.Model.FloorComponent.Room;
import com.mygdx.pmd.Model.Generator.FloorGenerator;
import com.mygdx.pmd.Model.Entity.Pokemon.Pokemon;
import com.mygdx.pmd.Model.Entity.Pokemon.PokemonMob;
import com.mygdx.pmd.Model.Entity.Pokemon.PokemonPlayer;
import com.mygdx.pmd.Model.Tile.Tile;
import com.mygdx.pmd.Screen.DungeonScreen;
import com.mygdx.pmd.utils.Constants;
import com.mygdx.pmd.Model.Entity.Entity;
import com.mygdx.pmd.Model.Entity.Projectile.Projectile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicBoolean;

public class Controller {
    public DungeonScreen controllerScreen;
    public final boolean dungeonMode = true;

    ArrayList<Renderable> renderList;
    ArrayList<Room> roomList;
    public ArrayList<Pokemon> pokemonList;
    ArrayList<Pokemon> updatePokemonList;
    ArrayList<Pokemon> enemyList;
    public Array<Projectile> projectiles;

    public ArrayList<Tile> loadedArea;
    public ArrayList<Tile> renderableArea;

    public static final int windowWidth = 1000;
    public static final int windowLength = 1000; //TODO the stutter might be because of having to reload everything on player movement

    public int playerXOffset;
    public int playerYOffset;

    public int renderableRowOffset = 100;
    public int renderableColOffset = 100;

    public static final int windowRows = windowLength / Constants.TILE_SIZE;
    public static final int windowCols = windowWidth / Constants.TILE_SIZE;

    private Pokemon pokemonPlayer;

    public boolean isShadowed;

    public Tile[][] tileBoard;
    private FloorGenerator floorGenerator;

    public boolean keyFrozen = false;

    public Floor currentFloor;

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

        this.isShadowed = false;

        floorGenerator = new FloorGenerator(this, 15);
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
            playerXOffset = (pokemonPlayer.x - DungeonScreen.APP_WIDTH / 2);
            playerYOffset = (pokemonPlayer.y - DungeonScreen.APP_HEIGHT / 2);
        }
    }

    public void loadPokemon() {
        PokemonFactory pokemonFactory = new PokemonFactory(this);
        XmlReader xmlReader = new XmlReader();
        XmlReader.Element root = null;
        try {
            root = xmlReader.parse(Gdx.files.internal("utils/PokemonStorage.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Array<XmlReader.Element> elementList = root.getChildrenByName("Pokemon");
        XmlReader.Element player = root.getChildByName("PokemonPlayer");
        PokemonName playerName = Enum.valueOf(PokemonName.class, player.get("name"));
        Pokemon pokemonPlayer = pokemonFactory.createPokemon(playerName, PokemonPlayer.class);
        this.addEntity(pokemonPlayer);

        for (XmlReader.Element e : elementList) {
            PokemonName pokemonName = Enum.valueOf(PokemonName.class, e.get("name"));
            Pokemon pokemon = pokemonFactory.createPokemon(pokemonName, PokemonMob.class);
            this.addEntity(pokemon);
        } //TODO have thing here
        this.randomizeAllPokemonLocation();
    }

    public Pokemon getPokemonPlayer() {
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

    public void randomizeAllPokemonLocation() {
        for (Pokemon pokemon : pokemonList) {
            pokemon.randomizeLocation();
            pokemon.actionState = Action.IDLE;
        }
        this.updatePlayerOffset();
        this.updateLoadedArea();
    }

    public void update() {
        try {
            this.tileBoard = currentFloor.getTileBoard();
            this.updatePlayerOffset();
            Collections.sort(pokemonList, new PokemonDistanceComparator(this.getPokemonPlayer()));

            for (Pokemon pokemon : pokemonList) {
                pokemon.update();
            }

            Pokemon pokemon = pokemonList.get(pokemonListCounter % pokemonList.size());
            if (pokemon.turnState == Turn.COMPLETE) {
                pokemon = pokemonList.get((++pokemonListCounter) % pokemonList.size());
                pokemon.turnState = Turn.WAITING;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < projectiles.size; i++) {
            projectiles.get(i).update();
            if (projectiles.get(i).destroy) {
                projectiles.removeIndex(i);
                this.controllerScreen.manager.get("sfx/wallhit.wav", Sound.class).play();

            }
        }
    }


    public boolean isKeyPressed(Key key) { //TODO perhaps add a buffer system for more control later - amend definitely need a buffer system
        return DungeonScreen.keys.get(key.getValue()).get();
    }

    public boolean isKeyPressed() {
        for (AtomicBoolean b : DungeonScreen.keys.values()) {
            if (b.get())
                return true;
        }
        return false;
    }

    public void freezeKeys() {
        keyFrozen = true;
    }

    public void addEntity(Entity entity) {
        if (entity instanceof Renderable) {
            renderList.add(entity);
        }

        if (entity instanceof Updatable) {
            updateList.add(entity);
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

        if (entity instanceof PokemonMob && ((PokemonMob) entity).aggression == Aggression.aggressive) {
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