package com.mygdx.pmd.Model.FloorComponent;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.pmd.Controller.Controller;
import com.mygdx.pmd.Interfaces.Renderable;
import com.mygdx.pmd.Interfaces.Updatable;
import com.mygdx.pmd.Model.Generator.FloorGenerator;
import com.mygdx.pmd.Model.Generator.PathGenerator;
import com.mygdx.pmd.Model.Generator.RoomGenerator;
import com.mygdx.pmd.Model.Pokemon.PokemonPlayer;
import com.mygdx.pmd.Model.TileType.GenericTile;
import com.mygdx.pmd.Model.TileType.StairTile;
import com.mygdx.pmd.Model.TileType.Tile;

import java.util.ArrayList;

/**
 * Created by Cameron on 7/27/2016.
 */
public class Floor implements Renderable, Updatable {

    Controller controller;

    Tile[][] tileBoard;
    int windowRows;
    int windowCols;

    private RoomGenerator roomGenerator;
    private PathGenerator pathGenerator;

    private ArrayList<Room> roomList;
    private ArrayList<Tile> roomTileList;
    private ArrayList<Tile> doorList;

    public final int NUMBER_OF_ROOMS = 15;

    FloorGenerator floorGenerator;

    PokemonPlayer pokemonPlayer;

    public Floor(Controller controller, int windowRows, int windowCols, FloorGenerator floorGenerator) {
        this.floorGenerator = floorGenerator;

        tileBoard = new Tile[windowRows][windowCols];
        this.generateTileBoard();
        this.windowRows = windowRows;
        this.windowCols = windowCols;
        this.controller = controller;
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
        StairTile stairTile = new StairTile(this, rt.getRow(), rt.getCol());
        tileBoard[stairTile.getRow()][stairTile.getCol()] = stairTile;
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
