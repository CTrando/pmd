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
    public Controller controller;
    public Tile[][] tileBoard;

    Floor floor;

    public FloorGenerator(Controller controller, int numRooms)
    {
        floor = new Floor(controller, numRooms, this);
        this.controller = controller;
    }

    public Floor getFloor() {
        return floor;
    }

    public void generateFloor()
    {
        floor.generateFloor();
        for(Pokemon pokemon: controller.pokemonList)
        {
            pokemon.setNextTile(null);
            pokemon.turnBehavior.setTurnState(Turn.COMPLETE);
        }

        if(controller.getPokemonPlayer() != null){
            controller.getPokemonPlayer().turnBehavior.setTurnState(Turn.WAITING);
        }
    }
}
