package com.mygdx.pmd.Model.TileType;


import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.mygdx.pmd.Enumerations.Direction;
import com.mygdx.pmd.Interfaces.Renderable;
import com.mygdx.pmd.Model.FloorComponent.Floor;
import com.mygdx.pmd.Model.Pokemon.Pokemon;
import com.mygdx.pmd.Screen.DungeonScreen;
import com.mygdx.pmd.utils.Entity;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Cameron on 6/17/2016.
 */
public abstract class Tile implements Renderable {
    public int x;
    public int y;

    private String classifier;

    public int row;
    public int col;

    private int windowRows;
    private int windowCols;

    public static final int size = 25;

    private Color color;

    private Sprite sprite;
    private Sprite debug = DungeonScreen.sprites.get("debugtilesprite");;

    private boolean isWalkable;

    private ArrayList<Renderable> renderList;

    private ArrayList<Entity> entityList;

    private Tile[][] tileBoard;

    private Tile parent;

    private Pokemon currentPokemon;

    public void setColor(Color color) {
        this.color = color;
    }

    public Tile(Floor floor, int r, int c) {
        this.tileBoard = floor.getTileBoard();
        this.parent = null;

        this.x = c * Tile.size;

        this.y = r * Tile.size;

        this.row = r;
        this.col = c;

        this.windowRows = tileBoard.length;
        this.windowCols = tileBoard[0].length;

        classifier = "SMALL";

        this.color = Color.white;
        renderList = new ArrayList<Renderable>();
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

    public Tile[][] getTileBoard() {
        return tileBoard;
    }

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

    public double calculateDistance(Tile t)
    {
        int rowOffset = Math.abs(this.row - t.row);
        int colOffset = Math.abs(this.col - t.col);

        return Math.sqrt(Math.pow(rowOffset, 2) + Math.pow(colOffset,2));
    }

    public static boolean tileExists(Tile[][] tileBoard, int row, int col)
    {
        if(row >= tileBoard.length || row<0)
        {
            return false;
        }

        if(col>=tileBoard[0].length ||   col < 0)
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

    public static Tile getNextTileOver(Tile[][] tileBoard, Tile t, Direction d)
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

    public int getX() {
        return this.col*Tile.size;
    }

    public int getY() {
        return this.row*Tile.size;
    }


    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
    public void setClassifier(String classifier) {
        this.classifier = classifier;
    }


    public boolean isWalkable() {
        return isWalkable;
    }

    public void setWalkable(boolean walkable) {
        isWalkable = walkable;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public int getWindowCols() {
        return windowCols;
    }

    public int getWindowRows() {
        return windowRows;
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

    public void setCurrentPokemon(Pokemon currentPokemon) {
        this.currentPokemon = currentPokemon;
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
        if(other.row < this.row)
            return true;
        else return false;
    }

    public boolean isBelows(Tile other)
    {
        if(other.row > this.row)
            return true;
        else return false;
    }

    public boolean isToLeft(Tile other)
    {
        if(other.col > this.col)
            return true;
        else return false;
    }

    public static Tile getTileAt(int x, int y, Tile[][] tileBoard) {
        Tile retTile = null;
        try{
            retTile = tileBoard[y/Tile.size][x/Tile.size];
        } catch(ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return retTile;
    }

    public boolean isToRight(Tile other)
    {
        if(other.col < this.col)
            return true;
        else return false;
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
