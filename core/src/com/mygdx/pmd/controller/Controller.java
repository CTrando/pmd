package com.mygdx.pmd.controller;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.*;
import com.mygdx.pmd.comparators.PokemonDistanceComparator;
import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.model.Decorators.*;
import com.mygdx.pmd.model.Entity.*;
import com.mygdx.pmd.model.Entity.Pokemon.*;
import com.mygdx.pmd.model.Factory.*;
import com.mygdx.pmd.model.Floor.*;
import com.mygdx.pmd.model.Spawner.*;
import com.mygdx.pmd.model.Tile.*;
import com.mygdx.pmd.screens.DungeonScreen;
import com.mygdx.pmd.test.TileTester;

import java.util.*;

import static com.mygdx.pmd.PMD.keys;

public class Controller {
    public DungeonScreen screen;

    private ArrayList<Entity> entityList;
    public Array<DynamicEntity> dEntities;
    private LinkedList<Entity> turnBasedEntities;
    private Entity updatedTurnEntity;

    public Pokemon pokemonPlayer;

    private Array<Entity> toBeRemoved;
    private Array<Entity> toBeAdded;

    private FloorFactory floorFactory;
    public Floor currentFloor;

    public static int floorCount = 1;
    public static int turns = 20;
    public static boolean turnsPaused = false;


    public Controller(DungeonScreen screen) {
        this.screen = screen;
        this.reset();
    }

    public void reset() {
        turns = 20;
        turnsPaused = false;
        floorCount = 1;

        //list of entities
        turnBasedEntities = new LinkedList<Entity>();
        dEntities = new Array<DynamicEntity>();
        entityList = new ArrayList<Entity>();
        toBeRemoved = new Array<Entity>();
        toBeAdded = new Array<Entity>();

        //init tileboard
        floorFactory = new FloorFactory(this);
        currentFloor = floorFactory.createFloor(this);
        this.directlyAddEntity(currentFloor);

        //decorate floor
        FloorDecorator.placeItems(currentFloor);
        FloorDecorator.skinTiles(currentFloor);
        FloorDecorator.placeEventTiles(currentFloor);

        //load pMob from xml
        this.loadPokemonFromJson(Gdx.files.internal("utils/PokemonStorage.json"));
        this.randomizeAllPokemonLocation();
        updatedTurnEntity = turnBasedEntities.poll();

        //add in a mob spawner
        MobSpawner mobSpawner = new MobSpawner(this);
        this.directlyAddEntity(mobSpawner);
    }

    public void nextFloor() {
        floorCount++;

        //does not actually create a new floor object, resets the floor
        currentFloor = floorFactory.createFloor(this);
        FloorDecorator.placeItems(currentFloor);
        FloorDecorator.skinTiles(currentFloor);
        FloorDecorator.placeEventTiles(currentFloor);

        if (!TileTester.checkCorners(getTileBoard())) throw new AssertionError("Uh oh, this floor is invalid!");

        this.randomizeAllPokemonLocation();
    }

    public void update() {
        //first update entities, then their turns should turn immediately to complete which allows them to continue on with the next one
        //this process allows for super quick movement
        for (int i = 0; i < entityList.size(); i++) {
            Entity entity = entityList.get(i);
            entity.update();

            if (updatedTurnEntity.isTurnComplete()) {
                //need to sort both entity list and turn based entities in order to update them in order
                if (updatedTurnEntity instanceof PokemonPlayer) {
                    if (!turnsPaused) {
                        turns--;
                    }

                    Collections.sort(turnBasedEntities, new PokemonDistanceComparator(this.pokemonPlayer));
                    Collections.sort(entityList, new PokemonDistanceComparator(this.pokemonPlayer));
                }

                //stack system
                //TODO replace with custom data structure
                turnBasedEntities.offer(updatedTurnEntity);
                updatedTurnEntity = turnBasedEntities.poll();
                updatedTurnEntity.setTurnState(Turn.WAITING);
            }
        }
        removeEntities();
        addEntities();
    }

    private void loadPokemonFromJson(FileHandle file) {
        JsonReader json = new JsonReader();
        JsonValue entities = json.parse(file);

        for (JsonValue entity : entities.get("entities")) {
            Pokemon pokemon = PokemonFactory.createPokemonFromJson(this, entity);
            this.directlyAddEntity(pokemon);
        }
    }

    private void randomizeAllPokemonLocation() {
        for (DynamicEntity dEntity : dEntities) {
            dEntity.randomizeLocation();
        }
    }

    private void directlyAddEntity(Entity entity) {
        screen.renderList.add(entity);
        entityList.add(entity);

        if (entity instanceof DynamicEntity) {
            dEntities.add((DynamicEntity) entity);
        }

        if (entity.isTurnBaseable()) {
            turnBasedEntities.addLast(entity);
        }

        if (entity instanceof PokemonPlayer) {
            pokemonPlayer = (PokemonPlayer) entity;
        }
    }

    private void addEntities() {
        for (Entity entity : toBeAdded) {
            directlyAddEntity(entity);
        }
        toBeAdded.clear();
    }

    private void removeEntities() {
        if (toBeRemoved.size == 0) return;

        for (Entity entity : toBeRemoved) {
            screen.renderList.removeValue(entity, true);
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

    public void toBeAdded(Entity entity) {
        toBeAdded.add(entity);
    }

    public void toBeRemoved(Entity entity) {
        toBeRemoved.add(entity);
    }

    public boolean isKeyPressed(Key key) { //TODO perhaps add a buffer system for more control later
        return keys.get(key.getValue()).get();
    }

    /**
     * Time sensitive key hits - hits are not consecutive
     *
     * @param key the key entered
     * @return true if the key has been pressed after a certain period of time - returns false if the key is not pressed or if the key has been pressed too soon
     */
    public boolean isKeyPressedTimeSensitive(Key key) {
        if (keys.get(key.getValue()).get()) {
            if (TimeUtils.timeSinceMillis(key.getLastTimeHit()) > 1000) {
                key.setLastTimeHit(TimeUtils.millis());
                return true;
            }
        }
        return false;
    }

    public Tile[][] getTileBoard() {
        return currentFloor.tileBoard;
    }
}