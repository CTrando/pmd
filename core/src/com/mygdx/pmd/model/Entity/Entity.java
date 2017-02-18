package com.mygdx.pmd.model.Entity;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.interfaces.*;
import com.mygdx.pmd.model.Behavior.BaseBehavior;
import com.mygdx.pmd.model.Behavior.NoBehavior;
import com.mygdx.pmd.model.Floor.Floor;
import com.mygdx.pmd.model.Tile.Tile;
import com.mygdx.pmd.utils.*;

import java.util.HashMap;

/**
 * Created by Cameron on 10/18/2016.
 */
public class Entity implements Renderable, Updatable, Disposable, Directional, ActionStateable {
    public BaseBehavior[] behaviors;

    /******************************************/
    //Inherited variables from interfaces
    private Action actionState;
    private Direction direction;
    protected Turn turnState;

    public boolean shouldBeDestroyed;
    /******************************************/

    public BaseBehavior noBehavior;

    /*-----------------------------------------*/
    //Position variables
    public int x;
    public int y;
    public Floor floor;
    private Tile currentTile;
    public Tile[][] tileBoard;
    /*-----------------------------------------*/

    /*******************************************/
    //Render variables
    public Sprite currentSprite;
    public HashMap<String, PAnimation> animationMap;
    public PAnimation currentAnimation;
    /********************************************/

    public Entity(){
        initBehaviors();
    }

    /**
        current tile is defined by the initial x and y
     */
    public Entity(Floor floor, int x, int y) {
        this.shouldBeDestroyed = false;

        this.floor = floor;
        this.tileBoard = floor.tileBoard;
        this.x = x;
        this.y = y;
        this.currentTile = tileBoard[y/ Constants.TILE_SIZE][x/Constants.TILE_SIZE];

        setDirection(Direction.down);
        setActionState(Action.IDLE);

        animationMap = new HashMap<String, PAnimation>();

        //initialize behaviors array
        initBehaviors();
    }

    private void initBehaviors(){
        noBehavior = new NoBehavior(this);
        behaviors = new BaseBehavior[10];
        for (int i = 0; i < behaviors.length; i++) {
            behaviors[i] = this.noBehavior;
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

    public boolean isTurnBaseable(){
        return turnState != null;
    }

    public boolean equals(Tile tile) {
        if(tile == null) return false;
        return (tile.x == x && tile.y == y);
    }

    public void setDirection(Tile tile) {
        if (this.isToLeft(tile))
            setDirection(Direction.right);
        if (this.isToRight(tile))
            setDirection(Direction.left);
        if (this.isAbove(tile))
            setDirection(Direction.down);
        if (this.isBelow(tile))
            setDirection(Direction.up);
    }

    private boolean isToRight(Tile tile) {
        return getCurrentTile().x > tile.x;
    }

    private boolean isToLeft(Tile tile) {
        return getCurrentTile().x < tile.x;
    }

    private boolean isAbove(Tile tile) {
        return getCurrentTile().y > tile.y;
    }

    private boolean isBelow(Tile tile) {
        return getCurrentTile().y < tile.y;
    }

    public void setCurrentTile(Tile nextTile) {
        this.currentTile = nextTile;
    }

    public Tile getCurrentTile() {
        return currentTile;
    }

    @Override
    public void setActionState(Action actionState){
        this.actionState = actionState;
    }

    @Override
    public Action getActionState(){
        return actionState;
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public void dispose() {

    }
}
