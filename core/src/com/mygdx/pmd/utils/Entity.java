package com.mygdx.pmd.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;
import com.mygdx.pmd.Controller.Controller;
import com.mygdx.pmd.Enumerations.PokemonName;
import com.mygdx.pmd.Interfaces.Renderable;
import com.mygdx.pmd.Interfaces.Updatable;
import com.mygdx.pmd.Model.Behavior.Behavior;
import com.mygdx.pmd.Model.Behavior.NoBehavior;
import com.mygdx.pmd.Model.Pokemon.Pokemon;
import com.mygdx.pmd.Model.TileType.StairTile;
import com.mygdx.pmd.Model.TileType.Tile;
import com.mygdx.pmd.Screen.DungeonScreen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Cameron on 10/18/2016.
 */
public abstract class Entity implements Renderable, Updatable{
    public Behavior[] behaviors;

    public int x;
    public int y;

    public int row;
    public int col;

    public int hp =100;

    public Tile nextTile;
    public Tile currentTile;

    public Sprite currentSprite;
    public HashMap<String, PAnimation> animationMap;
    public MotionBehavior motionBehavior;

    public Controller controller;

    public Entity(Controller controller, int x, int y) {
        this.controller = controller;
        this.x = x;
        this.y = y;
        animationMap = new HashMap<String, PAnimation>();
    }

    @Override
    public void render(SpriteBatch batch) {
        if (currentSprite != null) {
            batch.draw(currentSprite, x, y, currentSprite.getWidth(), currentSprite.getHeight());
        }
        //I've done the previous sprite thing here before and for whatever reason it didn't work out so don't try it
    }

    public abstract boolean isLegalToMoveTo(Tile tile);

    public boolean equals(Tile tile)
    {
        return (tile.x == x && tile.y == y);
    }

    public void move(int dx, int dy) {
        x+=dx;
        y+=dy;
    }

    public void goToTile(Tile nextTile, int speed) {
        if (nextTile == null)
            return;

        if (this.equals(nextTile)) {
            return;
        }

        if (this.y > nextTile.y && this.x > nextTile.x) {
            this.move(-speed, - speed);
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
        if(nextTile == null) return;
        if (this.currentTile != nextTile) {
            if(currentTile != null)
                this.currentTile.removeEntity(this);
            nextTile.addEntity(this);
            this.currentTile = nextTile;
            this.row = currentTile.row;
            this.col = currentTile.col;
        }
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
                    Sprite sprite = DungeonScreen.sprites.get(pokemonName + child.get("name"));
                    if (sprite != null)
                        spriteArray.add(sprite);
                }
                Sprite finalSprite = DungeonScreen.sprites.get(pokemonName + element.getChildByName("finalsprite").get("name"));
                PAnimation animation = new PAnimation(name, spriteArray, finalSprite, 30);
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

    public boolean isToRight(Tile tile) {
        return x > tile.x;
    }

    public boolean isToLeft(Tile tile) {
        return x < tile.x;
    }

    public boolean isAbove(Tile tile) {
        return y > tile.y;
    }

    public boolean isBelow(Tile tile) {
        return y < tile.y;
    }
}
