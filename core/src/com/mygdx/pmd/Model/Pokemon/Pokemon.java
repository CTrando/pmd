package com.mygdx.pmd.Model.Pokemon;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;
import com.mygdx.pmd.Controller.Controller;
import com.mygdx.pmd.Enumerations.*;
import com.mygdx.pmd.Interfaces.Renderable;
import com.mygdx.pmd.Interfaces.Updatable;
import com.mygdx.pmd.Model.TileType.Tile;
import com.mygdx.pmd.Screen.DungeonScreen;
import com.mygdx.pmd.utils.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class Pokemon extends Entity {
    private Tile nextTile;
    private Tile currentTile;
    public Tile facingTile;

    public Attack currentAttack;

    Array<Projectile> projectiles = new Array<Projectile>();


    public HashMap<String, PAnimation> animationMap;

    public int x;
    public int y;

    private int startingRow;
    private int startingCol;

    public Controller controller;
    public Direction direction = Direction.down;

    boolean movable;

    public PAnimation previousAnimation;
    public PAnimation currentAnimation;

    private final boolean dungeonMode = true;

    public Tile[][] tileBoard;

    private Turn turnState = Turn.COMPLETE;
    public Turn previousTurnState = Turn.COMPLETE;

    public Action actionState = Action.IDLE;

    private Sprite currentSprite;

    public String pokemonName;

    public State state;

    private int HP;

    public Pokemon(Controller controller, int x, int y, boolean movable, PokemonName pokemonName) {
        this.controller = controller;
        this.x = x;
        this.y = y;
        this.movable = movable;
        this.pokemonName = pokemonName.toString();

        this.startingCol = this.x / 25;
        this.startingRow = this.y / 25;

        this.currentSprite = DungeonScreen.sprites.get(pokemonName + "down1");

        HP = 100;

        if (dungeonMode) {
            tileBoard = controller.getCurrentFloor().getTileBoard();

            currentTile = tileBoard[startingRow][startingCol];
            currentTile.addEntity(this);

            nextTile = null;
        }

        animationMap = new HashMap<String, PAnimation>();
        this.loadAnimations();
        this.currentAnimation = animationMap.get("noanimation");
        this.previousAnimation = animationMap.get("defaultdown");
    }

    public void render(SpriteBatch batch) {
        if (currentSprite != null) {
            batch.draw(currentSprite, x, y, currentSprite.getWidth(), currentSprite.getHeight());
        }
        //I've done the previous sprite thing here before and for whatever reason it didn't work out so don't try it


    }

    public void loadAnimations() {
        XmlReader xmlReader = new XmlReader();
        try {
            XmlReader.Element root = xmlReader.parse(Gdx.files.internal("utils/AnimationStorage.xml"));
            for (XmlReader.Element element : root.getChildrenByName("Animation")) {
                String name = element.get("name");
                Array<Sprite> spriteArray = new Array<Sprite>();
                for (XmlReader.Element child : element.getChildrenByName("sprite")) {
                    Sprite sprite = DungeonScreen.sprites.get(this.pokemonName + child.get("name"));
                    if (sprite != null)
                        spriteArray.add(sprite);
                }
                Sprite finalSprite = DungeonScreen.sprites.get(this.pokemonName + element.getChildByName("finalsprite").get("name"));
                PAnimation animation = new PAnimation(name, spriteArray, finalSprite, 30);
                animationMap.put(name, animation);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateAnimation() {
        if (currentAnimation != animationMap.get("noanimation")) {
            currentSprite = currentAnimation.getCurrentSprite();

            if (currentAnimation.isFinished()) {
                if (controller.isKeyPressed(Key.a)) //something needs to be done here to compensate for keys being hit
                    currentSprite = currentAnimation.finalSprite;
                currentAnimation.clear();
                this.setCurrentAnimation(animationMap.get("noanimation"));
            }
        }
    }

    public void turnToTile(Tile tile) {

        if (tile.isAbove(currentTile))
            this.up();
        if (tile.isBelows(currentTile))
            this.down();
        if (tile.isToLeft(currentTile))
            this.left();
        if (tile.isToRight(currentTile))
            this.right();

        this.setFacingTile(this.direction);
    }

    public abstract void updatePosition();

    public void moveSlow() {
        if (this.isWithinArea(controller.loadedArea)) {
            this.goToTile(nextTile, 1);
        } else this.goToTileImmediately(nextTile);
    }

    public void moveFast() {
        this.goToTileImmediately(nextTile);
    }

    public String toString() {
        return pokemonName;
    }

    public void takeDamage(int x) {
        this.setHP(this.getHP() - 1);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(double x) {
        this.x = (int) x;
    }

    public void setY(double y) {
        this.y = (int) y;
    }

    public void updateAttack() {
        if (currentAttack == null) return;

        currentAttack.update();
        if(currentAttack.isFinished() && controller.projectiles.size == 0) {
            this.currentAnimation = animationMap.get("noanimation");
            this.actionState = Action.IDLE;
            this.setTurnState(Turn.COMPLETE);
            this.currentAttack = null;
        }
    }

    public void dealDamage(Pokemon pokemon) {
        pokemon.takeDamage(1);
    }

    public void down() {
        this.direction = Direction.down;
        this.setCurrentAnimation(animationMap.get("down"));
    }

    public void up() {
        this.direction = Direction.up;
        this.setCurrentAnimation(animationMap.get("up"));
    }

    public void right() {
        this.direction = Direction.right;
        this.setCurrentAnimation(animationMap.get("right"));
    }

    public void left() {
        this.direction = Direction.left;
        this.setCurrentAnimation(animationMap.get("left"));
    }

    public void upRight() {
        this.setCurrentAnimation(animationMap.get("upright"));
    }

    public void upLeft() {
        this.setCurrentAnimation(animationMap.get("upleft"));
    }

    public void downRight() {
        this.setCurrentAnimation(animationMap.get("downright"));
    }

    public void downLeft() {
        this.setCurrentAnimation(animationMap.get("downleft"));
    }

    public Tile getCurrentTile() {
        return currentTile;
    }

    public void setCurrentTile(Tile currentTile) {
        if (this.currentTile != currentTile) {
            this.currentTile.removeEntity(this);
            this.currentTile = currentTile;
            this.currentTile.addEntity(this);
        }
    }

    public void update() {
        this.updateAnimation();
        this.updateLogic();
        this.updatePosition();
        this.updateAttack();
    }

    public abstract void updateLogic();


    public void setCurrentAnimation(PAnimation animation) {
        if (this.currentAnimation != null && animation != this.currentAnimation)
            this.previousAnimation = this.currentAnimation;
        this.currentAnimation = animation;
    }

    public void turnToDirection(Direction d) {
        Tile tile = null;
        try{
            switch(d) {
                case up:
                    tile = tileBoard[currentTile.row+1][currentTile.col];
                    break;
                case down:
                    tile = tileBoard[currentTile.row-1][currentTile.col];
                    break;
                case left:
                    tile = tileBoard[currentTile.row][currentTile.col-1];
                    break;
                case right:
                    tile = tileBoard[currentTile.row][currentTile.col+1];
                    break;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        }
        this.turnToTile(tile);
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
        if (this.HP <= 0) {
            this.HP = 100;
        }
    }

    public Tile getNextTile() {
        return nextTile;
    }

    public boolean equals(Tile tile) {
        if (x == tile.col * Tile.size && y == tile.row * Tile.size) {
            return true;
        }
        return false;
    }

    public void setFacingTile(Direction direction) {
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
            e.printStackTrace();
        }
    }

    public void goToTile(Tile nextTile, int speed) {
        if (nextTile == null)
            return;

        if (this.equals(nextTile)) {
            return;
        }

        if (y > nextTile.y && x > nextTile.x) {
            this.setY(this.getY() - speed);
            this.setX(this.getX() - speed);
        } else if (y < nextTile.y && x > nextTile.x) {
            this.setY(this.getY() + speed);
            this.setX(this.getX() - speed);
        } else if (y < nextTile.y && x < nextTile.x) {
            this.setY(this.getY() + speed);
            this.setX(this.getX() + speed);
        } else if (y > nextTile.y && x < nextTile.x) {
            this.setY(this.getY() - speed);
            this.setX(this.getX() + speed);
        } else if (y > nextTile.y) {
            this.setY(this.getY() - speed);
        } else if (y < nextTile.y) {
            this.setY(this.getY() + speed);
        } else if (x < nextTile.x) {
            this.setX(this.getX() + speed);
        } else if (x > nextTile.x) {
            this.setX(this.getX() - speed);
        }
    }

    public boolean isToRight(Tile tile) {
        return x > tile.x;
    }

    public boolean isToLeft(Tile tile) {
        return x < tile.x;
    }

    public boolean isAbove(Tile tile) {
        return y > tile.getY();
    }

    public boolean isBelow(Tile tile) {
        return y < tile.getY();
    }

    public boolean isWithinArea(ArrayList<Tile> area) {
        for (Tile t : area) {
            if (t == this.currentTile) {
                return true;
            }
        }
        return false;
    }

    public void goToTileImmediately(Tile nextTile) {
        this.setX(nextTile.x);
        this.setY(nextTile.y);
    }

    public boolean isLegalToMoveTo(Tile tile) {

        if (tile.getCurrentPokemon() != null && tile.getCurrentPokemon() != this)
            return false;

        if (!tile.isWalkable())
            return false;

        return true;
    }

    public void setNextTile(Tile tile) {
        this.nextTile = tile;
    }

    public Turn getTurnState() {
        return turnState;
    }

    public void setTurnState(Turn turnState) {
        this.previousTurnState = this.turnState;
        this.turnState = turnState;
    }
}