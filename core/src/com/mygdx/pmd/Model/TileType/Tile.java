package com.mygdx.pmd.Model.TileType;


import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.mygdx.pmd.Enumerations.Direction;
import com.mygdx.pmd.Interfaces.Renderable;
import com.mygdx.pmd.Model.FloorComponent.Floor;
import com.mygdx.pmd.Model.Pokemon.Pokemon;
import com.mygdx.pmd.Screen.DungeonScreen;
import com.mygdx.pmd.utils.Constants;
import com.mygdx.pmd.utils.Entity;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Cameron on 6/17/2016.
 */
public abstract class Tile implements Renderable {
    public int x;
    public int y;

    public int row;
    public int col;

    public Sprite sprite;
    private Sprite debug = DungeonScreen.sprites.get("debugtilesprite");;

    private boolean isWalkable;

    private ArrayList<Entity> entityList;

    public Tile[][] tileBoard;
    private Tile parent;
    private Pokemon currentPokemon;

    public Tile(Floor floor, int r, int c) {
        this.tileBoard = floor.getTileBoard();

        this.x = c * Constants.TILE_SIZE;
        this.y = r * Constants.TILE_SIZE;

        this.row = r;
        this.col = c;

        entityList = new ArrayList<Entity>();
    }

    public void render(SpriteBatch batch)
    {
        batch.draw(sprite, x, y);
    }

    public void renderDebug(SpriteBatch batch)
    {
        batch.draw(this.debug, x, y);
    }

    public void update() {

    }

    public void playEvents()
    {

    }

    public abstract boolean isLegal();

    public Tile calculateClosestNeighbor(ArrayList<Tile> tiles)
    {
        Tile retTile = null;
        double min = Integer.MAX_VALUE;
        for(Tile t: tiles)
        {
            if(t != this) {
                double dist = this.calculateDistance(t);
                if (dist < min) {
                    min = dist;
                    retTile = t;
                }
            }
        }
        return retTile;
    }

    public Tile calculateClosestNeighbor(ArrayList<Tile> tiles, Tile tile)
    {
        Tile retTile = null;
        double min = Integer.MAX_VALUE;
        for(Tile t: tiles)
        {
            if(t != this && t != tile) {
                double dist = this.calculateDistance(t);
                if (dist < min) {
                    min = dist;
                    retTile = t;
                }
            }
        }
        return retTile;
    }

    public double calculateDistance(Tile tile)
    {
        return Tile.calculateDistance(this, tile);
    }

    public static boolean tileExists(Tile[][] tileBoard, int row, int col)
    {
        if(row >= tileBoard.length || row<0)
        {
            return false;
        }

        if(col>=tileBoard[0].length || col < 0)
        {
            return false;
        }
        return true;
    }

    public static double calculateDistance(Tile t1, Tile t2)
    {
        int rowOffset = Math.abs(t1.row - t2.row);
        int colOffset = Math.abs(t1.col - t2.col);

        return Math.sqrt(Math.pow(rowOffset, 2) + Math.pow(colOffset,2));
    }

    public static void resetTileArrayParents(Tile[][] tileBoard)
    {
        for(int i = 0; i< tileBoard.length; i++)
        {
            for(int j = 0; j< tileBoard[0].length; j++)
            {
                tileBoard[i][j].setParent(null);
            }
        }
    }

    public static ArrayList<Tile> getTilesAroundTile(Tile[][] tileBoard, Tile t)
    {
        ArrayList<Tile> returnTileList = new ArrayList<Tile>();

        int row = t.row;
        int col = t.col;

        if(Tile.tileExists(tileBoard, row+1, col))
        {
            returnTileList.add(tileBoard[row+1][col]);
        }

        if(Tile.tileExists(tileBoard, row-1, col))
        {
            returnTileList.add(tileBoard[row-1][col]);
        }

        if(Tile.tileExists(tileBoard, row, col+1))
        {
            returnTileList.add(tileBoard[row][col+1]);
        }

        if(Tile.tileExists(tileBoard, row, col-1))
        {
            returnTileList.add(tileBoard[row][col-1]);
        }

        return returnTileList;
    }

    public static Tile getNextTileInDirection(Tile[][] tileBoard, Tile t, Direction d)
    {
        int row = t.row;
        int col = t.col;

        switch(d)
        {
            case up:
                if(Tile.tileExists(tileBoard, row-1, col)) {
                    return tileBoard[row - 1][col];
                }
                break;
            case down:
                if(Tile.tileExists(tileBoard, row+1, col))
                {
                    return tileBoard[row+1][col];
                }
                break;
            case right:
                if(Tile.tileExists(tileBoard, row, col+1))
                {
                    return tileBoard[row][col+1];
                }
                break;
            case left:
                if(Tile.tileExists(tileBoard, row, col-1))
                {
                    return tileBoard[row][col-1];
                }
        }
        return null;
    }

    public boolean hasAPokemon()
    {
        if(currentPokemon != null)
            return true;
        else return false;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean isWalkable() {
        return isWalkable;
    }

    public void setWalkable(boolean walkable) {
        isWalkable = walkable;
    }

    public void setParent(Tile parent)
    {
        if(this.parent == null)
            this.parent = parent;

        if(parent == null)
            this.parent = null;
    }

    public Tile getParent()
    {
        return parent;
    }

    public Pokemon getCurrentPokemon() {
        return currentPokemon;
    }

    public void addEntity(Entity entity)
    {
        if(!entityList.contains(entity))
        entityList.add(entity);
        if(entity instanceof Pokemon)
        {
            currentPokemon = (Pokemon)entity;
        }
    }

    public void removeEntity(Entity entity)
    {
        entityList.remove(entity);
        if(entity instanceof Pokemon)
        {
            currentPokemon = null;
        }
    }

    public boolean isAbove(Tile other)
    {
        return other.row < this.row;
    }

    public boolean isBelows(Tile other)
    {
        return other.row > this.row;
    }

    public boolean isToLeft(Tile other)
    {
        return other.col > this.col;
    }

    public boolean isToRight(Tile other){
        return (other.col < this.col);
    }

    public static Tile getTileAt(int x, int y, Tile[][] tileBoard) {
        Tile retTile = null;
        try{
            retTile = tileBoard[y/Constants.TILE_SIZE][x/Constants.TILE_SIZE];
        } catch(ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return retTile;
    }

    public boolean equals(Tile o)
    {
        return (this.row == o.row && this.col == o.col);
    }

    public ArrayList<Entity> getEntityList() {
        return entityList;
    }

    public void print()
    {
        System.out.println(this.row + "," + this.col);
    }
}
