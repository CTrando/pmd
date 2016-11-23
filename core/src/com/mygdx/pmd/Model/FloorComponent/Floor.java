package com.mygdx.pmd.Model.FloorComponent;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.pmd.Controller.Controller;
import com.mygdx.pmd.Interfaces.Renderable;
import com.mygdx.pmd.Interfaces.Updatable;
import com.mygdx.pmd.Model.Generator.FloorGenerator;
import com.mygdx.pmd.Model.Generator.PathGenerator;
import com.mygdx.pmd.Model.Generator.RoomGenerator;
import com.mygdx.pmd.Model.Entity.Pokemon.Pokemon;
import com.mygdx.pmd.Model.Tile.GenericTile;
import com.mygdx.pmd.Model.Tile.StairTile;
import com.mygdx.pmd.Model.Tile.Tile;

import java.util.ArrayList;

import static com.mygdx.pmd.Controller.Controller.windowCols;
import static com.mygdx.pmd.Controller.Controller.windowRows;

/**
 * Created by Cameron on 7/27/2016.
 */
public class Floor implements Renderable, Updatable {
    Controller controller;
    Tile[][] tileBoard;

    private RoomGenerator roomGenerator;
    private PathGenerator pathGenerator;

    private ArrayList<Room> roomList;
    private ArrayList<Tile> roomTileList;
    private ArrayList<Tile> doorList;

    public final int NUMBER_OF_ROOMS;

    FloorGenerator floorGenerator;

    Pokemon pokemonPlayer;

    public Floor(Controller controller, int numRooms, FloorGenerator floorGenerator) {
        this.floorGenerator = floorGenerator;
        this.controller = controller;
        this.NUMBER_OF_ROOMS = numRooms;

        tileBoard = new Tile[windowRows][windowCols];
        this.generateTileBoard();
        this.pokemonPlayer = controller.getPokemonPlayer();

        roomGenerator = new RoomGenerator(this);
        pathGenerator = new PathGenerator(this);

    }

    public void generateFloor() {
        this.generateTileBoard();

        this.generateRooms();
        roomList = roomGenerator.getRoomList();
        roomTileList = roomGenerator.getRoomTileList();
        doorList = roomGenerator.getDoorList();

        this.generatePaths();
        this.generateStairs();
    }

    public void generateRooms() {
        roomGenerator.generateRooms();
    }

    public void generatePaths() {
        pathGenerator.generatePaths();
    }

    public void generateStairs()
    {
        int rand = (int)Math.random()*roomTileList.size();
        Tile rt = roomTileList.get(rand);
        StairTile stairTile = new StairTile(this, rt.row, rt.col);
        tileBoard[stairTile.row][stairTile.col] = stairTile;
    }

    public Tile[][] getTileBoard() {
        return tileBoard;
    }

    public int getWindowRows() {
        return windowRows;
    }

    public int getWindowCols() {
        return windowCols;
    }

    public void update() {
    }

    public void render(SpriteBatch batch) {
    }

    public void generateTileBoard() {
        for (int r = 0; r < tileBoard.length; r++) {
            for (int c = 0; c < tileBoard[0].length; c++) {
                tileBoard[r][c] = new GenericTile(this, r, c);
            }
        }
    }

    public ArrayList<Room> getRoomList() {
        return roomList;
    }

    public ArrayList<Tile> getRoomTileList() {
        return roomTileList;
    }

    public ArrayList<Tile> getDoorList() {
        return doorList;
    }

    public FloorGenerator getFloorGenerator() {
        return floorGenerator;
    }
}
