package com.mygdx.pmd.Model.Pokemon;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;
import com.mygdx.pmd.Controller.Controller;
import com.mygdx.pmd.Enumerations.*;
import com.mygdx.pmd.Model.TileType.Tile;
import com.mygdx.pmd.Screen.DungeonScreen;
import com.mygdx.pmd.utils.*;

import java.io.IOException;
import java.util.HashMap;

public abstract class Pokemon extends Entity {
    public Controller controller;

    public Direction direction = Direction.down;
    public Tile facingTile;
    public Attack currentAttack;

    public HashMap<String, PAnimation> animationMap;
    public PAnimation currentAnimation;

    public boolean isMovable;

    public Tile[][] tileBoard;

    public Turn turnState = Turn.COMPLETE;
    public Action actionState = Action.IDLE;
    public AggressionState aggressionState;

    public String pokemonName;

    public Pokemon(Controller controller, int x, int y, boolean isMovable, PokemonName pokemonName) {
        this.controller = controller;
        this.x = x;
        this.y = y;

        this.isMovable = isMovable;
        this.pokemonName = pokemonName.toString();

        this.currentSprite = DungeonScreen.sprites.get(pokemonName + "down1");

        if (controller.dungeonMode) {
            tileBoard = controller.getCurrentFloor().getTileBoard();
            this.setCurrentTile(tileBoard[this.y/Tile.size][this.x/Tile.size]);
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

    @Override
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

    @Override
    public void updatePosition(){
        if (!this.equals(this.getCurrentTile()))
            this.motionLogic(); //explanatory
    }

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

    public void motionLogic() {
        if (this.isWithinArea(controller.loadedArea)) {
            if (controller.isSPressed()) {
                this.moveFast();
            } else
                this.moveSlow();
        }
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

    public String toString() {
        return pokemonName;
    }
}
