package com.mygdx.pmd.controller;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;
import com.mygdx.pmd.comparators.PokemonDistanceComparator;
import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.interfaces.Renderable;
import com.mygdx.pmd.model.Factory.FloorFactory;
import com.mygdx.pmd.model.Factory.PokemonFactory;
import com.mygdx.pmd.model.Entity.Pokemon.Pokemon;
import com.mygdx.pmd.model.Entity.Pokemon.PokemonMob;
import com.mygdx.pmd.model.Entity.Pokemon.PokemonPlayer;
import com.mygdx.pmd.model.Tile.Tile;
import com.mygdx.pmd.screens.DungeonScreen;
import com.mygdx.pmd.model.Entity.Entity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import static com.mygdx.pmd.PMD.keys;

public class Controller {
    public DungeonScreen controllerScreen;
    public Tile[][] tileBoard;

    public ArrayList<Renderable> renderList;
    public ArrayList<Entity> entityList;
    public ArrayList<Entity> turnBasedEntities;
    public Pokemon pokemonPlayer;

    FloorFactory floorFactory;

    private int turnBasedEntityCount;

    public Controller(DungeonScreen controllerScreen) {
        this.controllerScreen = controllerScreen;

        turnBasedEntities = new ArrayList<Entity>();
        renderList = new ArrayList<Renderable>();
        entityList = new ArrayList<Entity>();

        floorFactory = new FloorFactory(this);

        tileBoard = floorFactory.createFloor();

        this.loadPokemon();
        this.randomizeAllPokemonLocation();
    }

    public void nextFloor(){
        tileBoard = floorFactory.createFloor();
        this.randomizeAllPokemonLocation();
    }

    public void update() {
        try {
            Collections.sort(turnBasedEntities, new PokemonDistanceComparator(this.pokemonPlayer));

            for (int i = 0; i< entityList.size(); i++) { //TODO there may be an error here later, fix when projectiles are implemented better
                entityList.get(i).update();
            }

            Entity entity = turnBasedEntities.get(turnBasedEntityCount % turnBasedEntities.size());
            if (entity.turnState == Turn.COMPLETE) {
                entity = turnBasedEntities.get((++turnBasedEntityCount) % turnBasedEntities.size());
                entity.turnState = Turn.WAITING;
            }

        } catch (Exception e) {
            e.printStackTrace();
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
        for (Entity entity : entityList) {
            entity.randomizeLocation();
            entity.setActionState(Action.IDLE);
            entity.turnState = Turn.COMPLETE;
            if(entity instanceof PokemonPlayer) entity.turnState = Turn.WAITING;
        }
    }

    public void addEntity(Entity entity) {
        renderList.add(entity);
        entityList.add(entity);

        if(entity.isTurnBased){
            turnBasedEntities.add(entity);
        }

        if (entity instanceof PokemonPlayer) {
            pokemonPlayer = (PokemonPlayer) entity;
        }
    }

    public void removeEntity(Entity entity) {
        renderList.remove(entity);
        entityList.remove(entity);

        if(entity.isTurnBased){
            turnBasedEntities.remove(entity);
        }
    }
}