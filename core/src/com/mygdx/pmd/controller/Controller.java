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

    public ArrayList<Renderable> renderList;
    public ArrayList<Entity> entityList;
    public Array<DynamicEntity> dEntities;
    public ArrayList<DynamicEntity> turnBasedEntities;
    public Pokemon pokemonPlayer;

    public MobSpawner mobSpawner;

    FloorFactory floorFactory;

    private int turnBasedEntityCount;

    public Controller(DungeonScreen controllerScreen) {
        this.controllerScreen = controllerScreen;

        turnBasedEntities = new ArrayList<DynamicEntity>();
        renderList = new ArrayList<Renderable>();
        entityList = new ArrayList<Entity>();
        dEntities = new Array<DynamicEntity>();

        floorFactory = new FloorFactory(this);
        tileBoard = floorFactory.createFloor();
        tileBoard = FloorDecorator.placeItems(tileBoard);
        tileBoard = FloorDecorator.skinTiles(tileBoard);


        this.loadPokemon();
        this.randomizeAllPokemonLocation();
        mobSpawner = new MobSpawner(this);
        this.addEntity(mobSpawner);
    }

    public void nextFloor() {
        tileBoard = floorFactory.createFloor();
        tileBoard = FloorDecorator.placeItems(tileBoard);
        tileBoard = FloorDecorator.skinTiles(tileBoard);
        this.randomizeAllPokemonLocation();
    }

    public void update() {

        Collections.sort(turnBasedEntities, new PokemonDistanceComparator(this.pokemonPlayer));

        for (int i = 0; i < entityList.size(); i++) { //TODO there may be an error here later, fix when projectiles are implemented better
            entityList.get(i).update();
        }

        DynamicEntity dentity = turnBasedEntities.get(turnBasedEntityCount % turnBasedEntities.size());
        if (dentity.turnState == Turn.COMPLETE) {
            dentity = turnBasedEntities.get((++turnBasedEntityCount) % turnBasedEntities.size());
            dentity.turnState = Turn.WAITING;
        }

    }

    public boolean isKeyPressed(Key key) { //TODO perhaps add a buffer system for more control later - amend definitely need a buffer system
        return keys.get(key.getValue()).get();
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
        }
    }

    public void randomizeAllPokemonLocation() {
        for (DynamicEntity dEntity : dEntities) {
            dEntity.randomizeLocation();
            dEntity.setActionState(Action.IDLE);
            dEntity.turnState = Turn.COMPLETE;

            if (dEntity instanceof PokemonPlayer) dEntity.turnState = Turn.WAITING;
        }
    }

    public void addEntity(Entity entity) {
        renderList.add(entity);
        entityList.add(entity);

        if (entity instanceof DynamicEntity) {
            dEntities.add((DynamicEntity) entity);
        }

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
    }

    public static Tile chooseUnoccupiedTile(Tile[][] tileBoard) {
        int randRow = PRandomInt.random(0, tileBoard.length - 1);
        int randCol = PRandomInt.random(0, tileBoard[0].length - 1);

        if (tileBoard[randRow][randCol] instanceof RoomTile) {
            return tileBoard[randRow][randCol];
        } else return chooseUnoccupiedTile(tileBoard);
    }
}