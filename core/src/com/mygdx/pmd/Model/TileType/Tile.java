package com.mygdx.pmd.Model.TileType;


import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.mygdx.pmd.Interfaces.Renderable;
import com.mygdx.pmd.Model.FloorComponent.Floor;
import com.mygdx.pmd.Screen.DungeonScreen;
import com.mygdx.pmd.utils.Constants;
import com.mygdx.pmd.utils.Entity;
import com.mygdx.pmd.utils.MathLogic;

import java.util.ArrayList;

/**
 * Created by Cameron on 6/17/2016.
 */
public abstract class Tile implements Renderable {
    public int x;
    public int y;

    public int row;
    public int col;
    public Floor floor;

    public Sprite sprite;
    private Sprite debug = DungeonScreen.sprites.get("debugtilesprite");
    ;

    public boolean isWalkable;
    public String classifier; //for toString purposes

    private ArrayList<Entity> entityList;

    public Tile[][] tileBoard;
    public Tile parent;

    public Tile(int r, int c, Floor floor, String classifier) {
        this.tileBoard = floor.getTileBoard();

        this.x = c * Constants.TILE_SIZE;
        this.y = r * Constants.TILE_SIZE;

        this.row = r;
        this.col = c;
        this.floor = floor;
        this.classifier = classifier;

        entityList = new ArrayList<Entity>();
    }

    public void render(SpriteBatch batch) {
        batch.draw(sprite, x, y);
    }

    public void renderDebug(SpriteBatch batch) {
        batch.draw(this.debug, x, y);
    }

    public void update() {

    }

    public void playEvents() {
    }

    public abstract boolean isLegal();

    public Tile calculateClosestNeighbor(ArrayList<Tile> tiles) {
        Tile retTile = null;
        double min = Integer.MAX_VALUE;
        for (Tile t : tiles) {
            if (t != this) {
                double dist = this.calculateDistance(t);
                if (dist < min) {
                    min = dist;
                    retTile = t;
                }
            }
        }
        return retTile;
    }

    public Tile calculateClosestNeighbor(ArrayList<Tile> tiles, Tile tile) {
        Tile retTile = null;
        double min = Integer.MAX_VALUE;
        for (Tile t : tiles) {
            if (t != this && t != tile) {
                double dist = this.calculateDistance(t);
                if (dist < min) {
                    min = dist;
                    retTile = t;
                }
            }
        }
        return retTile;
    }

    public double calculateDistance(Tile tile) {
        return Tile.calculateDistance(this, tile);
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

    public static double calculateDistance(Tile t1, Tile t2) {
        return MathLogic.calculateDistance(t1.x, t1.y, t2.x, t2.y);
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
        return (entityList.size() > 0);
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
        if (!entityList.contains(entity))
            entityList.add(entity);
    }

    public void removeEntity(Entity entity) {
        entityList.remove(entity);
    }

    public boolean isAbove(Tile other) {
        return other.row < this.row;
    }

    public boolean isBelows(Tile other) {
        return other.row > this.row;
    }

    public boolean isToLeft(Tile other) {
        return other.col > this.col;
    }

    public boolean isToRight(Tile other) {
        return (other.col < this.col);
    }

    public static Tile getTileAt(int x, int y, Tile[][] tileBoard) {
        Tile retTile = null;
        try {
            retTile = tileBoard[y / Constants.TILE_SIZE][x / Constants.TILE_SIZE];
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return retTile;
    }

    public boolean containsEntityType(Class type) {
        for (Entity entity : entityList) {
            if (type.isInstance(entity))
                return true;
        }
        return false;
    }

    public boolean equals(Tile o) {
        return (this.row == o.row && this.col == o.col);
    }

    public ArrayList<Entity> getEntityList() {
        return entityList;
    }

    public void print() {
        System.out.println(this.row + "," + this.col);
    }

    public String toString() {
        return this.classifier + " row: " + row + ", col: " + col;
    }
}
