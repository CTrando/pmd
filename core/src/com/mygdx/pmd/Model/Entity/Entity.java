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
import com.mygdx.pmd.model.Tile.StairTile;
import com.mygdx.pmd.model.Tile.Tile;
import com.mygdx.pmd.screens.DungeonScreen;
import com.mygdx.pmd.utils.PAnimation;
import com.mygdx.pmd.utils.observers.NoObserver;
import com.mygdx.pmd.utils.observers.Observable;
import com.mygdx.pmd.utils.observers.Observer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Cameron on 10/18/2016.
 */
public abstract class Entity implements Renderable, Updatable, Observable {
    public BaseBehavior[] behaviors;
    public boolean isTurnBased;
    public Turn turnState;
    private Action actionState;
    public Action previousState;

    public boolean shouldBeDestroyed;

    public int x;
    public int y;

    public int row;
    public int col;

    public int hp = 100;

    public Tile[][] tileBoard;

    public Tile nextTile;
    public Tile currentTile;
    public Tile facingTile;

    public Sprite currentSprite;
    public HashMap<String, PAnimation> animationMap;
    public Observer[] observers;

    public Direction direction;
    public Aggression aggression;

    public Controller controller;

    public Entity(Controller controller, int x, int y) {
        this.controller = controller;
        this.tileBoard = controller.tileBoard;
        this.x = x;
        this.y = y;
        this.direction = Direction.down;

        animationMap = new HashMap<String, PAnimation>();

        behaviors = new BaseBehavior[10];
        for (int i = 0; i < behaviors.length; i++) {
            behaviors[i] = new NoBehavior(this);
        }
        observers = new Observer[10];
        for(int i = 0; i< observers.length; i++){
            observers[i] = new NoObserver(this);
        }
    }

    @Override
    public void update() {
        for (int i = 0; i < behaviors.length; i++) {
            behaviors[i].execute();
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        if (currentSprite != null) {
            batch.draw(currentSprite, x, y, currentSprite.getWidth(), currentSprite.getHeight());
        }
        //I've done the previous sprite thing here before and for whatever reason it didn't work out so don't try it
    }

    public void notifyObservers(){
        for(int i = 0; i< observers.length; i++){
            observers[i].update();
        }
    }

    public abstract boolean isLegalToMoveTo(Tile tile);

    public void setBehavior(BaseBehavior behavior, int index) {
        behaviors[index] = behavior;
    }

    public boolean equals(Tile tile) {
        return (tile.x == x && tile.y == y);
    }

    public void move(int dx, int dy) {
        x += dx;
        y += dy;
    }

    public void goToTile(Tile nextTile, int speed) {
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
        this.goToTile(this.currentTile, 1);
    }

    public void moveFast() {
        this.goToTileImmediately(this.currentTile);
    }

    public boolean isWithinArea(ArrayList<Tile> area) {
        for (Tile t : area) {
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
        if (nextTile == null) return;

        if (currentTile != null)
            this.currentTile.removeEntity(this);
        this.currentTile = nextTile;
        currentTile.addEntity(this);


        this.row = currentTile.row;
        this.col = currentTile.col;
    }

    public Tile getNextTile() {
        return nextTile;
    }

    public void setNextTile(Tile tile) {
        this.nextTile = tile;
    }

    public void randomizeLocation() {
        int rand = (int) (Math.random() * controller.currentFloor.getRoomTileList().size());
        Tile random = controller.currentFloor.getRoomTileList().get(rand);

        if (!(random instanceof StairTile) && random.getEntityList().size() == 0) {
            this.setNextTile(null);
            this.setCurrentTile(random);

            this.x = random.x;
            this.y = random.y;
        }
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
        if (this.hp <= 0) {
            this.hp = 0;
        }
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

    public void takeDamage(int x) {
        this.setHp(this.getHp() - x);
    }

    public void dealDamage(Entity entity, int damage) {
        entity.takeDamage(damage);
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
        this.notifyObservers();
    }

    public Action getActionState(){
        return actionState;
    }
}
