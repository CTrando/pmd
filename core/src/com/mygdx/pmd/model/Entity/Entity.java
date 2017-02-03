package com.mygdx.pmd.model.Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;
import com.mygdx.pmd.PMD;
import com.mygdx.pmd.controller.Controller;
import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.interfaces.Renderable;
import com.mygdx.pmd.interfaces.Updatable;
import com.mygdx.pmd.model.Behavior.BaseBehavior;
import com.mygdx.pmd.model.Behavior.NoBehavior;
import com.mygdx.pmd.model.Floor.Floor;
import com.mygdx.pmd.model.Tile.Tile;
import com.mygdx.pmd.utils.*;
import com.mygdx.pmd.utils.observers.NoObserver;
import com.mygdx.pmd.utils.observers.Observable;
import com.mygdx.pmd.utils.observers.Observer;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Cameron on 10/18/2016.
 */
public abstract class Entity implements Renderable, Updatable, Observable {
    public BaseBehavior[] behaviors;

    public boolean shouldBeDestroyed;
    public BaseBehavior noBehavior;

    public int x;
    public int y;

    public int row;
    public int col;

    public Floor floor;

    private Tile currentTile;
    public Tile[][] tileBoard;

    public Sprite currentSprite;
    public HashMap<String, PAnimation> animationMap;
    public Observer[] observers;

    public Controller controller;
    private Turn turnState;

    /**
        current tile is defined by the initial x and y
     */
    public Entity(Controller controller, int x, int y) {
        this.controller = controller;
        this.tileBoard = controller.currentFloor.tileBoard;

        this.floor = controller.currentFloor;
        this.x = x;
        this.y = y;
        this.currentTile = tileBoard[y/ Constants.TILE_SIZE][x/Constants.TILE_SIZE];

        animationMap = new HashMap<String, PAnimation>();
        noBehavior = new NoBehavior(this);

        //initialize behaviors array
        behaviors = new BaseBehavior[10];
        for (int i = 0; i < behaviors.length; i++) {
            behaviors[i] = this.noBehavior;
        }
        observers = new Observer[10];
        for(int i = 0; i< observers.length; i++){
            observers[i] = new NoObserver(this);
        }
    }

    @Override
    public void update() {
        for(int i = 0; i < behaviors.length; i++) {
            behaviors[i].execute();
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        if (currentSprite != null) {
            batch.draw(currentSprite, x, y, currentSprite.getWidth(), currentSprite.getHeight());
        }
    }

    public void notifyObservers(){
        for(int i = 0; i< observers.length; i++){
            observers[i].update();
        }
    }

    public void setBehavior(BaseBehavior behavior, int index) {
        behaviors[index] = behavior;
    }

    public boolean isTurnBaseable(){
        return turnState != null;
    }

    public boolean equals(Tile tile) {
        if(tile == null) return false;
        return (tile.x == x && tile.y == y);
    }

    public void loadAnimations(PokemonName pokemonName) {
        XmlReader xmlReader = new XmlReader();
        try {
            XmlReader.Element root = xmlReader.parse(Gdx.files.internal("utils/AnimationStorage.xml"));
            for (XmlReader.Element element : root.getChildrenByName("Animation")) {
                String name = element.get("name");
                Array<Sprite> spriteArray = new Array<Sprite>();
                for (XmlReader.Element child : element.getChildrenByName("sprite")) {
                    Sprite sprite = PMD.sprites.get(pokemonName + child.get("name"));
                    if (sprite != null)
                        spriteArray.add(sprite);
                }
                Sprite finalSprite = PMD.sprites.get(pokemonName + element.getChildByName("finalsprite").get("name"));
                boolean isLooping = element.getChildByName("loopstatus").getBoolean("looping");
                PAnimation animation = new PAnimation(name, spriteArray, finalSprite, 30, isLooping);
                animationMap.put(name, animation);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //methods for turn state
    public boolean isTurnComplete(){
        return turnState == Turn.COMPLETE;
    }

    public boolean isTurnWaiting(){
        return turnState == Turn.WAITING;
    }

    public Turn getTurnState() {
        return turnState;
    }

    public void setTurnState(Turn turnState) {
        this.turnState = turnState;
    }

    public void setCurrentTile(Tile nextTile) {
        this.currentTile = nextTile;
    }

    public Tile getCurrentTile() {
        return currentTile;
    }

}
