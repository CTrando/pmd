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

public class Controller {
    public DungeonScreen controllerScreen;
    public Tile[][] tileBoard;

    public ArrayList<Renderable> renderList;
    public ArrayList<Entity> entityList;
    public ArrayList<Entity> turnBasedEntities;
    public Pokemon pokemonPlayer;

    private FloorGenerator floorGenerator;
    public Floor currentFloor;

    private int turnBasedEntityCount;

    public Controller(DungeonScreen controllerScreen) {
        this.controllerScreen = controllerScreen;

        turnBasedEntities = new ArrayList<Entity>();
        renderList = new ArrayList<Renderable>();
        entityList = new ArrayList<Entity>();

        floorGenerator = new FloorGenerator(this, 15);
        floorGenerator.generateFloor();
        currentFloor = floorGenerator.getFloor();
        renderList.add(currentFloor);

        tileBoard = currentFloor.getTileBoard();

        this.loadPokemon();
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
        return DungeonScreen.keys.get(key.getValue()).get();
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
            entity.actionState = Action.IDLE;
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