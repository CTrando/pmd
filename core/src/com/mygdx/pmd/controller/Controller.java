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
import java.util.*;

import static com.mygdx.pmd.PMD.keys;

public class Controller {
    public DungeonScreen screen;

    public ArrayList<Renderable> renderList;
    private ArrayList<Entity> entityList;
    public Array<DynamicEntity> dEntities;
    //private ArrayList<Entity> turnBasedEntities;

    private LinkedList<Entity> turnBasedEntities;
    public Entity updatedTurnEntity;

    public Pokemon pokemonPlayer;

    private Array<Entity> toBeRemoved;
    private Array<Entity> toBeAdded;

    private FloorFactory floorFactory;
    public Floor currentFloor;

    public int floorCount = 1;
    public int turns = 20;
    private int turnBasedEntityCount;
    public boolean turnsPaused = false;

    public Controller(DungeonScreen screen) {
        this.screen = screen;

        //list of entities
        turnBasedEntities = new LinkedList<Entity>();
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
        FloorDecorator.placeEventTiles(currentFloor);

        //load pMob from xml
        this.loadPokemon();
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
        for (int i = 0; i< entityList.size(); i++) {
            Entity entity = entityList.get(i);
            entity.update();

            if (updatedTurnEntity.isTurnComplete()) {
                if (!this.turnsPaused) {
                    if (updatedTurnEntity instanceof PokemonPlayer) {
                        turns--;
                    }
                }

                //need to sort both entity list and turn based entities in order to update them in order
                if (updatedTurnEntity instanceof PokemonPlayer) {
                    Collections.sort(turnBasedEntities, new PokemonDistanceComparator(this.pokemonPlayer));
                    Collections.sort(entityList, new PokemonDistanceComparator(this.pokemonPlayer));
                }

                turnBasedEntities.offer(updatedTurnEntity);
                updatedTurnEntity = turnBasedEntities.poll();
                updatedTurnEntity.setTurnState(Turn.WAITING);
            }
        }

       /* for(Entity entity: turnBasedEntities) {
            //Entity entity = turnBasedEntities.get(turnBasedEntityCount);
            if (entity.isTurnComplete()) {
                //watch for this method it can mess up the order of the entities
                Collections.sort(turnBasedEntities, new PokemonDistanceComparator(this.pokemonPlayer));
                //update the turns
                //currently have no better way of updating
*//*
                if (++turnBasedEntityCount >= turnBasedEntities.size()) {
                    turnBasedEntityCount = 0;
                }*//*

                //entity = turnBasedEntities.get(turnBasedEntityCount);
                //entity.setTurnState(Turn.WAITING);


                turnBasedEntities.get(turnBasedEntities.indexOf(entity)+1).setTurnState(Turn.WAITING);
                //TODO fix all these bugs
                //TODO bug here with entities updating even when Turn is pending, and that allows me to move again
            }
        }*/
        //remove entities regardless so they don't get updated again
        //TODO bug when something dies is because turn counter loses its place
        removeEntities();
        addEntities();
    }

    private void loadPokemon() {
        JsonReader json = new JsonReader();
        JsonValue entities = json.parse(Gdx.files.internal("utils/PokemonStorage.json"));

        for (JsonValue entity : entities.get("entities")) {
            //check for key player
            if (entity.getString("type").contains("player")) {
                //init players
                Pokemon pokemonPlayer = PokemonFactory.createPokemon(this,
                        Enum.valueOf(PokemonName.class, entity.getString("name")), PokemonPlayer.class);
                this.directlyAddEntity(pokemonPlayer);
                //check for key mob
            } else if (entity.getString("type").contains("mob")) {
                //init mobs
                Pokemon mob = PokemonFactory.createPokemon(this, Enum.valueOf(PokemonName.class,
                        entity.getString("name")), PokemonMob.class);
                this.directlyAddEntity(mob);
            }
        }
    }

    private void randomizeAllPokemonLocation() {
        for (DynamicEntity dEntity : dEntities) {
            dEntity.randomizeLocation();
        }
    }

    private void directlyAddEntity(Entity entity) {
        renderList.add(entity);
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