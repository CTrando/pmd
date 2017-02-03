package com.mygdx.pmd.model.Tile;


import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.mygdx.pmd.PMD;
import com.mygdx.pmd.controller.Controller;
import com.mygdx.pmd.enumerations.Aggression;
import com.mygdx.pmd.interfaces.Renderable;
import com.mygdx.pmd.model.Entity.DynamicEntity;
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
    public Controller controller;
    public Floor floor;

    public int x;
    public int y;

    public int row;
    public int col;

    public int spriteValue = 0;

    public Sprite sprite;
    private Sprite debug = PMD.sprites.get("debugtilesprite");

    public boolean isWalkable;
    public String classifier; //for toString purposes

    private ArrayList<Entity> entityList;
    private Array<StaticEntity> staticEntities;
    public Array<DynamicEntity> dynamicEntities;

    public Tile[][] tileBoard;
    public Tile parent;

    public Tile(int r, int c, Floor floor, String classifier) {
        this.controller = floor.controller;
        this.tileBoard = floor.tileBoard;
        this.floor = floor;

        this.x = c * Constants.TILE_SIZE;
        this.y = r * Constants.TILE_SIZE;

        this.row = r;
        this.col = c;
        this.classifier = classifier;

        entityList = new ArrayList<Entity>();
        staticEntities = new Array<StaticEntity>();
        dynamicEntities = new Array<DynamicEntity>();
    }

    public void render(SpriteBatch batch) {
        batch.draw(sprite, x, y);
        for(StaticEntity sEntity: staticEntities){
            sEntity.render(batch);
        }
    }

    public void renderDebug(SpriteBatch batch) {
        batch.draw(this.debug, x, y);
    }

    public void update() {

    }

    public void playEvents(DynamicEntity receiver) {
        for(StaticEntity sEntity: staticEntities){
            if(sEntity instanceof Item){
                ((Item) sEntity).playEvents(receiver);
            }
        }
        staticEntities.clear();
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

    public boolean hasDynamicEntity() {
        return (dynamicEntities.size > 0);
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

        if(entity instanceof StaticEntity){
            staticEntities.add((StaticEntity)entity);
        }

        if(entity instanceof DynamicEntity){
            dynamicEntities.add((DynamicEntity)entity);
        }
    }

    public void removeEntity(Entity entity) {
        entityList.remove(entity);

        if(entity instanceof DynamicEntity){
            dynamicEntities.removeValue((DynamicEntity) entity, true);
        }

        if(entity instanceof StaticEntity){
            staticEntities.removeValue((StaticEntity) entity, true);
        }
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
            return null;
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

    public boolean containsAggressionType(Aggression type){
        //TODO seeing me in the place of the other so still attacking, need to change it so that if it sees obstacle don't attack
        for(DynamicEntity dEntity: dynamicEntities){
            if(dEntity.aggression == type){
                return true;
            }
        }
        return false;
    }

    public boolean equals(Tile o) {
        if (o == null) return false;
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
