package com.mygdx.pmd.model.Tile;


import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.mygdx.pmd.PMD;
import com.mygdx.pmd.interfaces.*;
import com.mygdx.pmd.model.Entity.Item.Item;
import com.mygdx.pmd.model.Entity.StaticEntity;
import com.mygdx.pmd.model.Floor.Floor;
import com.mygdx.pmd.model.components.*;
import com.mygdx.pmd.utils.*;
import com.mygdx.pmd.model.Entity.Entity;


import static com.mygdx.pmd.screens.DungeonScreen.PPM;

/**
 * Created by Cameron on 6/17/2016.
 */
public abstract class Tile extends Entity {
    public Floor floor;

    public int x;
    public int y;

    public int row;
    public int col;

    public int spriteValue = 0;

    public Sprite sprite;
    private Sprite debug = PMD.sprites.get("debugtilesprite");

    public boolean isWalkable;
    private String classifier; //for toString purposes

    private Array<Entity> entityList;

    public Tile[][] tileBoard;
    private Tile parent;

    public RenderComponent rc;

    public Tile(int r, int c, Floor floor, String classifier) {
        super(floor, c*Constants.TILE_SIZE, r*Constants.TILE_SIZE);
        this.tileBoard = floor.tileBoard;
        this.floor = floor;

        this.x = c * Constants.TILE_SIZE;
        this.y = r * Constants.TILE_SIZE;

        this.row = r;
        this.col = c;
        this.classifier = classifier;

        entityList = new Array<Entity>();
        this.rc = new RenderComponent(this);
        components.put(RenderComponent.class, rc);
    }
/*

    public void render(SpriteBatch batch) {
        if(sprite != null) {
            batch.draw(sprite, x / PPM, y / PPM, sprite.getWidth() / PPM, sprite.getHeight() / PPM);
        }
        for (StaticEntity sEntity : items) {
            sEntity.render(batch);
        }
    }
*/

    public void renderDebug(SpriteBatch batch) {
        batch.draw(this.debug, x, y);
    }

    public void update() {

    }

    public void playEvents(Entity receiver) {
        for (Entity child : children) {
            if (child instanceof Item) {
                Item item = (Item) child;
                System.out.println("Some item was hit by " + receiver.toString());
                item.playEvents(receiver);
                if (item.shouldBeDestroyed) {
                    entityList.removeValue(item, true);
                    children.removeValue(item, true);
                    floor.removeItem(item);
                }
            }
        }
    }

    public int dist(Tile tile) {
        return (int) (Tile.dist(this, tile) / 25);
    }

    public static double dist(Tile t1, Tile t2) {
        return MathLogic.calculateDistance(t1.x, t1.y, t2.x, t2.y);
    }

    public static boolean tileExists(Tile[][] tileBoard, int row, int col) {
        if (row >= tileBoard.length || row < 0) {
            return false;
        }

        return !(col >= tileBoard[0].length || col < 0);
    }

    public static void resetTileArrayParents(Tile[][] tileBoard) {
        for (int i = 0; i < tileBoard.length; i++) {
            for (int j = 0; j < tileBoard[0].length; j++) {
                tileBoard[i][j].setParent(null);
            }
        }
    }

    public static Array<Tile> getTilesAroundTile(Tile[][] tileBoard, Tile t) {
        Array<Tile> returnTileList = new Array<Tile>();

        int row = t.row;
        int col = t.col;

        if (Tile.tileExists(tileBoard, row + 1, col)) {
            returnTileList.add(tileBoard[row + 1][col]);
        }

        if (Tile.tileExists(tileBoard, row - 1, col)) {
            returnTileList.add(tileBoard[row - 1][col]);
        }

        if (Tile.tileExists(tileBoard, row, col + 1)) {
            returnTileList.add(tileBoard[row][col + 1]);
        }

        if (Tile.tileExists(tileBoard, row, col - 1)) {
            returnTileList.add(tileBoard[row][col - 1]);
        }

        return returnTileList;
    }

    public void setParent(Tile parent) {
        if (this.parent == null) {
            this.parent = parent;
        }

        if (parent == null) {
            this.parent = null;
        }
    }

    public Tile getParent() {
        return parent;
    }

    public void addEntity(Entity entity) {
        if (!entityList.contains(entity, true)) {
            entityList.add(entity);
        }

        if (entity instanceof Item) {
            children.add(entity);
        }
    }

    public void removeEntity(Entity entity) {
        entityList.removeValue(entity, true);

        if (entity instanceof Item) {
            children.removeValue(entity, true);
        }
    }

    public static Tile getTileAt(int x, int y, Tile[][] tileBoard) {
        Tile retTile;
        try {
            retTile = tileBoard[y / Constants.TILE_SIZE][x / Constants.TILE_SIZE];
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            return null;
        }
        return retTile;
    }

    public boolean equals(Tile o) {
        if (o == null) {
            return false;
        }
        return (this.row == o.row && this.col == o.col);
    }

    public Array<Entity> getEntityList() {
        return entityList;
    }

    public String toString() {
        return this.classifier + " row: " + row + ", col: " + col;
    }

    public boolean hasEntityOfType(Class c) {
        return PUtils.getObjectsOfType(c, entityList).size > 0;
    }

    public boolean hasEntityWithComponent(Class type) {
        for (Entity entity : entityList) {
            if (entity.hasComponent(type)) {
                return true;
            }
        }

        //TODO fix this method and fix components so it uses a hashmap and strings to find components
        return false;
    }
}
