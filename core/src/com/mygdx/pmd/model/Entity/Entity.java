package com.mygdx.pmd.model.Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.*;
import com.mygdx.pmd.PMD;
import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.interfaces.Renderable;
import com.mygdx.pmd.interfaces.Updatable;
import com.mygdx.pmd.model.Behavior.*;
import com.mygdx.pmd.model.Behavior.Entity.*;
import com.mygdx.pmd.model.Floor.Floor;
import com.mygdx.pmd.model.Tile.Tile;
import com.mygdx.pmd.utils.*;
import com.mygdx.pmd.utils.observers.NoObserver;
import com.mygdx.pmd.utils.observers.Observable;
import com.mygdx.pmd.utils.observers.Observer;

import java.util.HashMap;

/**
 * Created by Cameron on 10/18/2016.
 */
public abstract class Entity implements Renderable, Updatable, Observable {
    public Array<Component> components;

    private Action actionState;
    private Action previousState;

    public boolean isTurnBased; // replace with turn component

    public int hp = 100;
    /**
     * The next tile the entity will currentMove to
     */
    private Tile nextTile;
    /**
     * The tile the entity is facing
     */
    public Tile facingTile;
    /**
     * Tile that needs to be legalized before it becomes the next tile, prerequisite of tile movement system
     */
    public Tile possibleNextTile;

    public Direction direction;
    public int speed = 1;

    public PAnimation currentAnimation;

    public boolean shouldBeDestroyed;

    public int x;
    public int y;

    public int row;
    public int col;

    public Floor floor;

    private Tile currentTile;
    public Tile[][] tileBoard;

    public Sprite currentSprite;
    public HashMap<String, PAnimation> animationMap;
    protected Observer[] observers;

    private Turn turnState;

    public Entity(){
        initComponents();
    }

    /**
        current tile is defined by the initial x and y
     */
    public Entity(Floor floor, int x, int y) {
        this.floor = floor;
        this.tileBoard = floor.tileBoard;
        this.currentTile = tileBoard[y/ Constants.TILE_SIZE][x/Constants.TILE_SIZE];

        this.x = x;
        this.y = y;

        animationMap = new HashMap<String, PAnimation>();

        //initialize behaviors array
        initObservers();
        initComponents();
    }

    public void initComponents(){
        components = new Array<Component>();
        for(int i = 0; i< 10; i++) {
            components.add(Component.NOCOMPONENT);
        }
    }

    public boolean componentExists(int index){
        return components.get(index) != null;
    }

    public void addComponent(int index,  Component component){
        components.set(index, component);
    }

    public void removeComponent(int index){
        components.removeIndex(index);
    }

    public Component getComponent(int index){
        return components.get(index);
    }

    private void initObservers(){
        observers = new Observer[10];
        for(int i = 0; i< observers.length; i++){
            observers[i] = new NoObserver(this);
        }
    }

    @Override
    public void update() {
        for(Component component: components){
            component.update();
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

    public void randomizeLocation() {
        Tile random = floor.chooseUnoccupiedTile();

        if (random.isWalkable) {
            this.setNextTile(random);
            this.setCurrentTile(random);
            this.possibleNextTile = null;
        } else randomizeLocation();

        this.setActionState(Action.IDLE);
        this.setTurnState(Turn.COMPLETE);
    }

    public boolean equals(Tile tile) {
        if(tile == null) return false;
        return (tile.x == x && tile.y == y);
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
        this.x = nextTile.x;
        this.y = nextTile.y;
        this.notifyObservers();
    }

    public Tile getCurrentTile() {
        return currentTile;
    }

    public Tile getNextTile(){ return nextTile;}

    public void setNextTile(Tile nextTile) {
        this.nextTile = nextTile;
        if (nextTile == null) return;

        currentTile.removeEntity(this);
        nextTile.addEntity(this);

        // should be done in main logic  entity.directionComponent.setFacingTile(nextTile);
    }

    public abstract void dispose();


    public void setActionState(Action actionState){
        this.previousState = this.actionState;
        this.actionState = actionState;
    }

    public Action getActionState(){
        return actionState;
    }

    public void setSpeed(int speed){
        this.speed = speed;
    }

}
