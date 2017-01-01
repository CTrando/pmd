package com.mygdx.pmd.controller;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;
import com.mygdx.pmd.interfaces.Turnbaseable;
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
import com.mygdx.pmd.model.Spawner.MobSpawner;
import com.mygdx.pmd.model.Tile.RoomTile;
import com.mygdx.pmd.model.Tile.Tile;
import com.mygdx.pmd.screens.DungeonScreen;
import com.mygdx.pmd.model.Entity.Entity;
import com.mygdx.pmd.utils.PRandomInt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import static com.mygdx.pmd.PMD.keys;

public class Controller {
    public DungeonScreen controllerScreen;
    public Tile[][] tileBoard;
    public static final int NUM_MAX_ENTITY = 10;

    public ArrayList<Renderable> renderList;
    public ArrayList<Entity> entityList;
    public Array<DynamicEntity> dEntities;
    public ArrayList<DynamicEntity> turnBasedEntities;
    public Pokemon pokemonPlayer;

    public MobSpawner mobSpawner;

    FloorFactory floorFactory;

    public int floorCount;

    private int turnBasedEntityCount;

    public Controller(DungeonScreen controllerScreen) {
        this.controllerScreen = controllerScreen;

        //list of entities
        turnBasedEntities = new ArrayList<DynamicEntity>();
        dEntities = new Array<DynamicEntity>();
        entityList = new ArrayList<Entity>();

        //list of renderables
        renderList = new ArrayList<Renderable>();

        //init tileboard
        floorFactory = new FloorFactory(this);
        tileBoard = floorFactory.createFloor();

        //decorate tileboard
        tileBoard = FloorDecorator.placeItems(tileBoard);
        tileBoard = FloorDecorator.skinTiles(tileBoard);
        tileBoard = FloorDecorator.placeEventTiles(tileBoard, floorFactory);

        //load pokemon from xml
        this.loadPokemon();
        this.randomizeAllPokemonLocation();

        //add in a mob spawner
        mobSpawner = new MobSpawner(this);
        //this.addEntity(mobSpawner);
    }

    public void nextFloor() {
        floorCount++;

        tileBoard = floorFactory.createFloor();
        tileBoard = FloorDecorator.placeItems(tileBoard);
        tileBoard = FloorDecorator.skinTiles(tileBoard);
        tileBoard = FloorDecorator.placeEventTiles(tileBoard, floorFactory);

        this.randomizeAllPokemonLocation();
    }

    public void update() {
        Collections.sort(turnBasedEntities, new PokemonDistanceComparator(this.pokemonPlayer));
        for (int i = 0; i < entityList.size(); i++) {
            entityList.get(i).update();
        }

        //need to fix this to decouple turn based entities from dynamic entities
        DynamicEntity dEntity = turnBasedEntities.get(turnBasedEntityCount % turnBasedEntities.size());
        if (dEntity.turnState == Turn.COMPLETE) {
            dEntity = turnBasedEntities.get((++turnBasedEntityCount) % turnBasedEntities.size());
            dEntity.turnState = Turn.WAITING;
        }
    }

    public boolean isKeyPressed(Key key) { //TODO perhaps add a buffer system for more control later
        return keys.get(key.getValue()).get();
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
        this.addEntity(pokemonPlayer);

        //init mobs
        for (XmlReader.Element e : elementList) {
            PokemonName pokemonName = Enum.valueOf(PokemonName.class, e.get("name"));
            Pokemon pokemon = PokemonFactory.createPokemon(this, pokemonName, PokemonMob.class);
            this.addEntity(pokemon);
        }
    }

    public void randomizeAllPokemonLocation() {
        for (DynamicEntity dEntity : dEntities) {
            dEntity.randomizeLocation();
        }
    }

    public void addEntity(Entity entity) {
        renderList.add(entity);
        entityList.add(entity);

        if (entity instanceof DynamicEntity) {
            dEntities.add((DynamicEntity) entity);
        }

        //TODO decouple turn based entities from dynamic entities
        if (entity instanceof Turnbaseable) {
            turnBasedEntities.add((DynamicEntity) entity);
        }

        if (entity instanceof PokemonPlayer) {
            pokemonPlayer = (PokemonPlayer) entity;
        }
    }

    public void removeEntity(Entity entity) {
        renderList.remove(entity);
        entityList.remove(entity);

        if (entity instanceof Turnbaseable) {
            turnBasedEntities.remove(entity);
        }

        if (entity instanceof DynamicEntity){
            dEntities.removeValue((DynamicEntity)entity, true);
        }
    }

    public static Tile chooseUnoccupiedTile(Tile[][] tileBoard) {
        int randRow = PRandomInt.random(0, tileBoard.length - 1);
        int randCol = PRandomInt.random(0, tileBoard[0].length - 1);

        Tile chosenTile = tileBoard[randRow][randCol];

        if (chosenTile instanceof RoomTile && chosenTile.isDynamicEntityEmpty()) {
            return tileBoard[randRow][randCol];
        } else return chooseUnoccupiedTile(tileBoard);
    }
}