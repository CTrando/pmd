package com.mygdx.pmd.controller;


import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.*;
import com.mygdx.pmd.comparators.PokemonDistanceComparator;
import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.model.Behavior.*;
import com.mygdx.pmd.model.Behavior.Entity.*;
import com.mygdx.pmd.model.Behavior.Pokemon.PokePlayer.*;
import com.mygdx.pmd.model.Decorators.*;
import com.mygdx.pmd.model.Entity.*;
import com.mygdx.pmd.model.Entity.Pokemon.*;
import com.mygdx.pmd.model.Factory.*;
import com.mygdx.pmd.model.Floor.*;
import com.mygdx.pmd.screens.DungeonScreen;
import com.mygdx.pmd.test.TileTester;

import java.util.*;

public class Controller {
    public DungeonScreen screen;

   /* public ArrayList<Entity> entities;
    private LinkedList<Entity> turnBasedEntities;
    private Entity updatedTurnEntity;

    public Pokemon pokemonPlayer;

    //must be lists
    private Array<Entity> toBeRemoved;
    private Array<Entity> toBeAdded;*/

    public PokemonPlayer player;

    private FloorFactory floorFactory;
    public Floor floor;

    public static int floorCount = 1;
    public static int turns = 20;
    public static boolean turnsPaused = false;

    public Engine engine;


    public Controller(DungeonScreen screen) {
        this.screen = screen;
        this.reset();
    }

    public void reset() {
        turns = 20;
        turnsPaused = false;
        floorCount = 1;

        engine = new Engine();
        engine.addSystem(new PlayerLogicSystem());
        engine.addSystem(new MoveSystem());
        engine.addSystem(new RenderSystem());

        //engine.addSystem(new MobLogicSystem());

        //list of entities
       /* turnBasedEntities = new LinkedList<Entity>();
        entities = new ArrayList<Entity>();
        toBeRemoved = new Array<Entity>();
        toBeAdded = new Array<Entity>();*/

        //init tileboard
        floorFactory = new FloorFactory(this);
        floor = floorFactory.createFloor();
        screen.renderList.add(floor);
        //this.directlyAddEntity(floor);

        //decorate floor
        FloorDecorator.placeItems(floor);
        FloorDecorator.skinTiles(floor);
        FloorDecorator.placeEventTiles(floor);

        //load pMob from xml
        this.loadPokemonFromJson(Gdx.files.internal("utils/PokemonStorage.json"));
        //this.randomizeAllPokemonLocation();
        //updatedTurnEntity = turnBasedEntities.peek();

        //add in a mob spawner
        //MobSpawner mobSpawner = new MobSpawner(floor);
        //this.directlyAddEntity(mobSpawner);
    }

    public void nextFloor() {
        floorCount++;

        //does not actually create a new floor object, resets the floor
        floor = floorFactory.createFloor();
        FloorDecorator.placeItems(floor);
        FloorDecorator.skinTiles(floor);
        FloorDecorator.placeEventTiles(floor);

        if (!TileTester.checkCorners(floor.tileBoard)) throw new AssertionError("Uh oh, this floor is invalid!");

        //this.randomizeAllPokemonLocation();
    }

    public void update() {
        //first update entities, then their turns should turn immediately to complete which allows them to continue on with the next one
        //this process allows for super quick movement

        engine.update(0);

        ImmutableArray<Entity> turnBasedEntities = engine.getEntitiesFor(Family.all(TurnComponent.class).get());

        for(int i = 0; i< turnBasedEntities.size(); i++){
            TurnComponent tm = Mappers.tm.get(turnBasedEntities.get(i));
            if(tm.isTurnComplete())
                TurnComponent tm1 = Mappers.tm.get(turnBasedEntities.get(i+1));
                tm1.setTurnState(Turn.WAITING);
            }
        }

       /* for (int i = 0; i < entities.size(); i++) {
            Entity entity = entities.get(i);
            entity.update();

            if (updatedTurnEntity.isTurnComplete()) {
                //need to sort both entity list and turn based entities in order to update them in order
                if (updatedTurnEntity instanceof PokemonPlayer) {
                    if (!turnsPaused) {
                        turns--;
                    }

                    Collections.sort(turnBasedEntities, new PokemonDistanceComparator(this.pokemonPlayer));
                    Collections.sort(entities, new PokemonDistanceComparator(this.pokemonPlayer));
                }

                //stack system
                //TODO replace with custom data structure
                turnBasedEntities.remove(updatedTurnEntity);
                turnBasedEntities.offer(updatedTurnEntity);
                updatedTurnEntity = turnBasedEntities.peek();
                updatedTurnEntity.setTurnState(Turn.WAITING);
            }

            if(entity.shouldBeDestroyed){
                entity.dispose();
                this.toBeRemoved(entity);
            }
        }
        removeEntities();
        addEntities();*/
    }

    private void loadPokemonFromJson(FileHandle file) {
        JsonReader json = new JsonReader();
        JsonValue entities = json.parse(file);

        for (JsonValue entity : entities.get("entities")) {
            Pokemon pokemon = PokemonFactory.createPokemonFromJson(floor, entity);

            if(pokemon instanceof PokemonPlayer){
                player = (PokemonPlayer) pokemon;
            }
            screen.renderList.add(pokemon);
            engine.addEntity(pokemon);
        }
    }
/*

    private void randomizeAllPokemonLocation() {
        for (Entity entity : entities) {
            entity.randomizeLocation();
        }
    }
*/

   /* private void directlyAddEntity(Entity entity) {
        screen.renderList.add(entity);
        entities.add(entity);

        //fix later
        if (entity.getTurnState() != null) {
            turnBasedEntities.addLast(entity);
        }

        if (entity instanceof PokemonPlayer) {
            pokemonPlayer = (PokemonPlayer) entity;
        }
    }*/

  /*  private void addEntities() {
        for (Entity entity : toBeAdded) {
            directlyAddEntity(entity);
        }
        toBeAdded.clear();
    }

    private void removeEntities() {
        if (toBeRemoved.size == 0) return;

        for (Entity entity : toBeRemoved) {
            screen.renderList.removeValue(entity, true);
            entities.remove(entity);

            if (entity.isTurnBaseable()) {
                turnBasedEntities.remove(entity);
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

    public ArrayList<Entity> getEntities(){
        return entities;
    }*/
}
