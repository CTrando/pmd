package com.mygdx.pmd.model.Tile;


import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.mygdx.pmd.PMD;
import com.mygdx.pmd.interfaces.Renderable;
import com.mygdx.pmd.model.Entity.Item.Item;
import com.mygdx.pmd.model.Entity.StaticEntity;
import com.mygdx.pmd.model.Floor.Floor;
import com.mygdx.pmd.utils.Constants;
import com.mygdx.pmd.model.Entity.Entity;
import com.mygdx.pmd.utils.MathLogic;


import java.util.ArrayList;

/**
 * Created by Cameron on 6/17/2016.
 */
public abstract class Tile implements Renderable {
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

    public Array<Entity> entities;
    private Array<Item> items;

    public Tile[][] tileBoard;
    private Tile parent;

    public Tile(int r, int c, Floor floor, String classifier) {
        this.tileBoard = floor.tileBoard;
        this.floor = floor;

        this.x = c * Constants.TILE_SIZE;
        this.y = r * Constants.TILE_SIZE;

        this.row = r;
        this.col = c;
        this.classifier = classifier;

        entities = new Array<Entity>();
        items = new Array<Item>();
    }

    public void render(SpriteBatch batch) {
        batch.draw(sprite, x, y);
        for (StaticEntity sEntity : items) {
            sEntity.render(batch);
        }
    }

    public void renderDebug(SpriteBatch batch) {
        batch.draw(this.debug, x, y);
    }

    public void update() {

    }

    public void playEvents(Entity receiver) {
        for (Item item : items) {
            item.playEvents(receiver);
            items.removeValue(item, true);
            floor.removeItem(item);
        }
    }

    public double calculateDistanceTo(Tile tile) {
        return Tile.calculateDistanceTo(this, tile);
    }

    public static double calculateDistanceTo(Tile t1, Tile t2) {
        return MathLogic.calculateDistance(t1.x, t1.y, t2.x, t2.y);
    }

    public static boolean tileExists(Tile[][] tileBoard, int row, int col) {
        if (row >= tileBoard.length || row < 0) {
            return false;
        }

        if (col >= tileBoard[0].length || col < 0) {
            return false;
        }
        return true;
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

    public boolean hasEntity() {
        return (entities.size > 0);
    }

    public void setParent(Tile parent) {
        if (this.parent == null)
            this.parent = parent;

        if (parent == null)
            this.parent = null;
    }

    public Tile getParent() {
        return parent;
    }

    public void addEntity(Entity entity) {
        if (entity instanceof Item) {
            items.add((Item) entity);
        }
        entities.add(entity);
    }

    public void removeEntity(Entity entity) {
        entities.removeValue(entity, true);

        if (entity instanceof Item) {
            items.removeValue((Item) entity, true);
        }
    }

    public static Tile getTileAt(int x, int y, Tile[][] tileBoard) {
        Tile retTile = null;
        try {
            retTile = tileBoard[y / Constants.TILE_SIZE][x / Constants.TILE_SIZE];
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            return null;
        }
        return retTile;
    }

    public boolean equals(Tile o) {
        if (o == null) return false;
        return (this.row == o.row && this.col == o.col);
    }

    public void print() {
        System.out.println(this.row + "," + this.col);
    }

    public String toString() {
        return this.classifier + " row: " + row + ", col: " + col;
    }
}
