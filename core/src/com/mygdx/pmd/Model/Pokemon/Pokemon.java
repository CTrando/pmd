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

    public Tile facingTile;
    public Attack currentAttack;

    public HashMap<String, PAnimation> animationMap;

    private int startingRow;
    private int startingCol;

    public Controller controller;
    public Direction direction = Direction.down;

    public PAnimation currentAnimation;

    boolean movable;

    private final boolean dungeonMode = true;

    public Tile[][] tileBoard;

    public Turn turnState = Turn.COMPLETE;

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
    }

    @Override
    public void update() {
        this.updateAnimation();
        this.updateLogic();
        this.updatePosition();
        this.updateFacingTile();
        this.updateAttack();
    }

    public void updateAnimation() {
        switch(actionState) {
            case MOVING:
                currentAnimation = animationMap.get(direction.toString());
                currentSprite = animationMap.get(direction.toString()).getCurrentSprite();
                break;
            case ATTACKING:
                currentAnimation = animationMap.get(direction.toString() + "attack");
                currentSprite = animationMap.get(direction.toString() + "attack").getCurrentSprite();
                break;
            case IDLE:
                currentAnimation = animationMap.get("noanimation");
                currentSprite = animationMap.get(direction.toString()).finalSprite;
                animationMap.get(direction.toString()).clear();
        }
    }

    public abstract void updateLogic();

    public abstract void updatePosition();

    public void updateFacingTile() {
        try {
            switch (this.direction) {
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

    public void updateAttack() {
        if (currentAttack == null) return;

        currentAttack.update();
        if(currentAttack.isFinished())
            this.actionState = Action.IDLE;

        if (currentAttack.isFinished() && controller.projectiles.size == 0) {
            this.actionState = Action.IDLE;
            this.turnState =Turn.COMPLETE;
            this.currentAttack = null;
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        if (currentSprite != null) {
            batch.draw(currentSprite, x, y, currentSprite.getWidth(), currentSprite.getHeight());
        }
        //I've done the previous sprite thing here before and for whatever reason it didn't work out so don't try it
    }

    public void turnToTile(Tile tile) {
        if (tile.isAbove(currentTile))
            this.direction = Direction.up;
        if (tile.isBelows(currentTile))
            this.direction = Direction.down;
        if (tile.isToLeft(currentTile))
            this.direction = Direction.left;
        if (tile.isToRight(currentTile))
            this.direction = Direction.right;
    }

    @Override
    public boolean isLegalToMoveTo(Tile tile) {
        if (tile.getCurrentPokemon() != null && tile.getCurrentPokemon() != this)
            return false;
        if (!tile.isWalkable())
            return false;
        return true;
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

    public Tile getCurrentTile() {
        return currentTile;
    }

    public void setCurrentTile(Tile nextTile) {
        if(nextTile == null) return;
        if (this.currentTile != nextTile) {
            this.currentTile.removeEntity(this);
            nextTile.addEntity(this);
            this.currentTile = nextTile;
        }
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

    public void takeDamage(int x) {
        this.setHP(this.getHP() - x);
    }

    public void dealDamage(Pokemon pokemon) {
        pokemon.takeDamage(1);
    }


    public String toString() {
        return pokemonName;
    }
}
