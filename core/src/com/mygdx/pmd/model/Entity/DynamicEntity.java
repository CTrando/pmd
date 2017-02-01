package com.mygdx.pmd.model.Entity;

import com.mygdx.pmd.interfaces.Damageable;
import com.mygdx.pmd.controller.Controller;
import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.model.Tile.Tile;

import java.util.ArrayList;


/**
 * Created by Cameron on 10/18/2016.
 */
public abstract class DynamicEntity extends Entity{
    public boolean isTurnBased;
    private Action actionState;
    public Action previousState;

    public boolean isForcedMove;

    public boolean shouldBeDestroyed;

    public int hp = 100;

    private Tile nextTile;
    public Tile facingTile;
    public Tile possibleNextTile;

    public Direction direction;
    public Aggression aggression;
    public int speed = 1;

    public DynamicEntity(Controller controller, int x, int y) {
        super(controller, x, y);
        this.direction = Direction.down;
    }

    public abstract boolean isLegalToMoveTo(Tile tile);

    public void move(int dx, int dy) {
        x += dx;
        y += dy;
    }

    public void forceMoveToTile(Tile nextTile){
        isForcedMove = true;
        this.setNextTile(nextTile);
    }

    public void moveToTile(Tile nextTile, int speed) {
        if (nextTile == null)
            return;

        if (this.equals(nextTile)) {
            return;
        }

        if (this.y > nextTile.y && this.x > nextTile.x) {
            this.move(-speed, -speed);
        } else if (this.y < nextTile.y && this.x > nextTile.x) {
            this.move(-speed, speed);
        } else if (this.y < nextTile.y && this.x < nextTile.x) {
            this.move(speed, speed);
        } else if (this.y > nextTile.y && this.x < nextTile.x) {
            this.move(speed, -speed);
        } else if (this.y > nextTile.y) {
            this.move(0, -speed);
        } else if (this.y < nextTile.y) {
            this.move(0, speed);
        } else if (this.x < nextTile.x) {
            this.move(speed, 0);
        } else if (this.x > nextTile.x) {
            this.move(-speed, 0);
        }
    }

    public void goToTileImmediately(Tile nextTile) {
        this.x = nextTile.x;
        this.y = nextTile.y;
    }

    public void moveSlow() {
        this.moveToTile(this.nextTile, 1);
    }

    public void moveFast() {
        this.goToTileImmediately(this.currentTile);
    }

    public boolean currentTileIsWithinList(ArrayList<Tile> list) {
        for (Tile t : list) {
            if (t == this.currentTile) {
                return true;
            }
        }
        return false;
    }

    public Tile getCurrentTile() {
        return currentTile;
    }

    public void setCurrentTile(Tile nextTile) {
        this.currentTile = nextTile;
        this.x = currentTile.x;
        this.y = currentTile.y;
        this.notifyObservers();
    }

    public Tile getNextTile() {
        return nextTile;
    }

    public void setNextTile(Tile tile) {
        if (tile == null) return;

        if (this.currentTile != null)
            this.currentTile.removeEntity(this);
        tile.addEntity(this);

        this.nextTile = tile;
    }

    public void updateCurrentTile(){
        Tile tile = Tile.getTileAt(x, y, tileBoard);
        if(this.equals(tile))
            this.setCurrentTile(tile);
    }

    public void randomizeLocation() {
        Tile random = Controller.chooseUnoccupiedTile(tileBoard);

        if (random.isWalkable) {
            this.setNextTile(random);
            this.setCurrentTile(random);
            this.possibleNextTile = null;
        } else randomizeLocation();

        this.setActionState(Action.IDLE);
        this.setTurnState(Turn.COMPLETE);
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
        if (this.hp <= 0) {
            this.hp = 0;
        }

        if(this.hp > 100){
            this.hp = 100;
        }
    }

    public void takeDamage(DynamicEntity aggressor, int damage) {
        this.setHp(this.getHp() - damage);
    }

    public void dealDamage(Damageable damageable, int damage) {
        damageable.takeDamage(damage);
    }

    public void setFacingTileBasedOnDirection(Direction d) {
        try {
            switch (direction) {
                case up:
                    facingTile = tileBoard[currentTile.row + 1][currentTile.col];
                    break;
                case down:
                    facingTile = tileBoard[currentTile.row - 1][currentTile.col];
                    break;
                case right:
                    facingTile = tileBoard[currentTile.row][currentTile.col + 1];
                    break;
                case left:
                    facingTile = tileBoard[currentTile.row][currentTile.col - 1];
                    break;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        }
    }

    public void setDirectionBasedOnTile(Tile tile) {
        if (this.isToLeft(tile))
            this.direction = Direction.right;
        if (this.isToRight(tile))
            this.direction = Direction.left;
        if (this.isAbove(tile))
            this.direction = Direction.down;
        if (this.isBelow(tile))
            this.direction = Direction.up;
    }

    public boolean isToRight(Tile tile) {
        return currentTile.x > tile.x;
    }

    public boolean isToLeft(Tile tile) {
        return currentTile.x < tile.x;
    }

    public boolean isAbove(Tile tile) {
        return currentTile.y > tile.y;
    }

    public boolean isBelow(Tile tile) {
        return currentTile.y < tile.y;
    }

    public void setActionState(Action actionState){
        this.previousState = this.actionState;
        this.actionState = actionState;
        //this.notifyObservers();
    }

    public boolean isAggressive(){
        return aggression == Aggression.aggressive;
    }

    public Action getActionState(){
        return actionState;
    }

    public void setSpeed(int speed){
        this.speed = speed;
    }
}
