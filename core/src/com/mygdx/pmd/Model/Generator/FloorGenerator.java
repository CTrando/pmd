package com.mygdx.pmd.Model.Generator;


import com.mygdx.pmd.Controller.Controller;
import com.mygdx.pmd.Enumerations.Turn;
import com.mygdx.pmd.Model.FloorComponent.Floor;
import com.mygdx.pmd.Model.Pokemon.Pokemon;
import com.mygdx.pmd.Model.TileType.Tile;

/**
 * Created by Cameron on 7/6/2016.
 */
public class FloorGenerator {
    private Tile[][] tileBoard;
    private RoomGenerator roomGenerator;
    private PathGenerator pathGenerator;
    public Controller controller;

    private int windowCols;
    private int windowRows;

    Floor floor;


    public FloorGenerator(int windowRows, int windowCols, int roomNum, Controller controller)
    {
        floor = new Floor(controller, windowRows, windowCols, this);
        this.controller = controller;
        this.windowCols = windowCols;
        this.windowRows = windowRows;
    }

    public Floor getFloor() {
        return floor;
    }

    public RoomGenerator getRoomGenerator() {
        return roomGenerator;
    }

    public PathGenerator getPathGenerator() {
        return pathGenerator;
    }


    public void generateFloor()
    {
        floor.generateFloor();
        for(Pokemon pokemon: controller.pokemonList)
        {
            pokemon.setTurnState(Turn.COMPLETE);
        }
    }
}
