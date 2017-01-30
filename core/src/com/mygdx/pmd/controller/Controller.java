package com.mygdx.pmd.controller;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.*;
import com.mygdx.pmd.comparators.PokemonDistanceComparator;
import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.interfaces.Renderable;
import com.mygdx.pmd.model.Decorators.FloorDecorator;
import com.mygdx.pmd.model.Entity.DynamicEntity;
import com.mygdx.pmd.model.Factory.FloorFactory;
import com.mygdx.pmd.model.Factory.PokemonFactory;
import com.mygdx.pmd.model.Entity.Pokemon.Pokemon;
import com.mygdx.pmd.model.Entity.Pokemon.PokemonMob;
import com.mygdx.pmd.model.Entity.Pokemon.PokemonPlayer;
import com.mygdx.pmd.model.Floor.Floor;
import com.mygdx.pmd.model.Spawner.MobSpawner;
import com.mygdx.pmd.model.Tile.RoomTile;
import com.mygdx.pmd.model.Tile.Tile;
import com.mygdx.pmd.screens.DungeonScreen;
import com.mygdx.pmd.model.Entity.Entity;
import com.mygdx.pmd.test.TileTester;
import com.mygdx.pmd.utils.PRandomInt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import static com.mygdx.pmd.PMD.keys;

public class Controller {
    public DungeonScreen controllerScreen;
    public static final int NUM_MAX_ENTITY = 7;

    public boolean turnsPaused = false;
    public ArrayList<Renderable> renderList;
    public ArrayList<Entity> entityList;
    public Array<DynamicEntity> dEntities;
    public ArrayList<Entity> turnBasedEntities;
    public Pokemon pokemonPlayer;

    private long lastTimeKeyHit = 0;


    private Array<Entity> toBeRemoved;
    private Array<Entity> toBeAdded;

    public MobSpawner mobSpawner;

    FloorFactory floorFactory;
    public Floor currentFloor;

    public int floorCount = 1;
    public int turns = 20;

    private int turnBasedEntityCount;

    public Controller(DungeonScreen controllerScreen) {
        this.controllerScreen = controllerScreen;

        //list of entities
        turnBasedEntities = new ArrayList<Entity>();
        dEntities = new Array<DynamicEntity>();
        entityList = new ArrayList<Entity>();
        toBeRemoved = new Array<Entity>();
        toBeAdded = new Array<Entity>();

        //list of renderables
        renderList = new ArrayList<Renderable>();

        //init tileboard
        floorFactory = new FloorFactory(this);
        currentFloor = floorFactory.createFloor(this);

        //decorate floor
        FloorDecorator.placeItems(currentFloor);
        FloorDecorator.skinTiles(currentFloor);
        FloorDecorator.placeEventTiles(currentFloor, floorFactory);

        //load pMob from xml
        this.loadPokemon();
        this.randomizeAllPokemonLocation();

        //add in a mob spawner
        mobSpawner = new MobSpawner(this);
        this.directlyAddEntity(mobSpawner);
    }

    public void nextFloor() {
        floorCount++;

        //does not actually create a new floor object, but basically a new floor
        currentFloor = floorFactory.createFloor(this);
        FloorDecorator.placeItems(currentFloor);
        FloorDecorator.skinTiles(currentFloor);
        FloorDecorator.placeEventTiles(currentFloor, floorFactory);

        System.out.println(TileTester.checkCorners(getTileBoard()));

        this.randomizeAllPokemonLocation();
    }

    public void update() {
        //watch for modulus errors so don't use it
        Collections.sort(turnBasedEntities, new PokemonDistanceComparator(this.pokemonPlayer));
        for (int i = 0; i < entityList.size(); i++) {
            entityList.get(i).update();
        }

        Entity entity = turnBasedEntities.get(turnBasedEntityCount);
        if (entity.turnState == Turn.COMPLETE) {
            //update the turns
            //currently have no better way of updating
            if (!this.turnsPaused) {
                if(entity instanceof PokemonPlayer){
                    turns--;
                }
            }

            if (++turnBasedEntityCount >= turnBasedEntities.size()) {
                turnBasedEntityCount = 0;
            }

            entity = turnBasedEntities.get(turnBasedEntityCount);
            entity.turnState = Turn.WAITING;

            //TODO bug here with entities updating even when Turn is pending, and that allows me to move again
            addEntities();
            removeEntities();
        }
    }

    public boolean isKeyPressed(Key key) { //TODO perhaps add a buffer system for more control later
        return keys.get(key.getValue()).get();
    }

    /**
     * Time sensitive key hits - hits are not consecutive
     * @param key the key entered
     * @return true if the key has been pressed after a certain period of time - returns false if the key is not pressed or if the key has been pressed too soon
     */
    public boolean isKeyPressedTimeSensitive(Key key){
        if(keys.get(key.getValue()).get()){
            if(TimeUtils.timeSinceMillis(key.getLastTimeHit()) > 1000) {
                key.setLastTimeHit(TimeUtils.millis());
                return true;
            }
        }
        return false;
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
        PokemonName playerName = Enum.valueOf(PokemonName.class, player.get("name"));

        //init player
        Pokemon pokemonPlayer = PokemonFactory.createPokemon(this, playerName, PokemonPlayer.class);
        this.directlyAddEntity(pokemonPlayer);

        //init mobs
        for (XmlReader.Element e : elementList) {
            PokemonName pokemonName = Enum.valueOf(PokemonName.class, e.get("name"));
            Pokemon pokemon = PokemonFactory.createPokemon(this, pokemonName, PokemonMob.class);
            this.directlyAddEntity(pokemon);
        }
    }

    public void randomizeAllPokemonLocation() {
        for (DynamicEntity dEntity : dEntities) {
            dEntity.randomizeLocation();
        }
    }

    public void toBeAdded(Entity entity){
        toBeAdded.add(entity);
    }

    public void addEntities() {
        for (Entity entity : toBeAdded) {
            directlyAddEntity(entity);
        }
        toBeAdded.clear();
    }

    public void directlyAddEntity(Entity entity) {
        renderList.add(entity);
        entityList.add(entity);

        if (entity instanceof DynamicEntity) {
            dEntities.add((DynamicEntity) entity);
        }

        //TODO decouple turn based entities from dynamic entities
        if (entity.isTurnBaseable()) {
            turnBasedEntities.add(entity);
        }

        if (entity instanceof PokemonPlayer) {
            pokemonPlayer = (PokemonPlayer) entity;
        }
    }

    private void removeEntities() {
        if (toBeRemoved.size == 0) return;

        for (Entity entity : toBeRemoved) {
            renderList.remove(entity);
            entityList.remove(entity);

            if (entity.isTurnBaseable()) {
                turnBasedEntities.remove(entity);
            }

            if (entity instanceof DynamicEntity) {
                dEntities.removeValue((DynamicEntity) entity, true);
            }
        }
        toBeRemoved.clear();
    }

    //TODO fix this method so it only removes after the end of an interation
    public void addToRemoveList(Entity entity) {
        toBeRemoved.add(entity);
    }

    public static Tile chooseUnoccupiedTile(Tile[][] tileBoard) {
        int randRow = PRandomInt.random(0, tileBoard.length - 1);
        int randCol = PRandomInt.random(0, tileBoard[0].length - 1);

        Tile chosenTile = tileBoard[randRow][randCol];

        if (chosenTile instanceof RoomTile && !chosenTile.hasDynamicEntities()) {
            return tileBoard[randRow][randCol];
        } else return chooseUnoccupiedTile(tileBoard);
    }

    public Tile[][] getTileBoard(){
        return currentFloor.tileBoard;
    }
}