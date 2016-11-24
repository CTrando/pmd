package com.mygdx.pmd.Model.Generator;


import com.mygdx.pmd.Controller.Controller;
import com.mygdx.pmd.Model.Entity.Entity;
import com.mygdx.pmd.Model.FloorComponent.Floor;
import com.mygdx.pmd.Model.Entity.Pokemon.Pokemon;
import com.mygdx.pmd.Model.Tile.Tile;

/**
 * Created by Cameron on 7/6/2016.
 */
public class FloorGenerator {
    public Controller controller;
    public Tile[][] tileBoard;

    Floor floor;

    public FloorGenerator(Controller controller, int numRooms) {
        floor = new Floor(controller, numRooms, this);
        this.controller = controller;
    }

    public Floor getFloor() {
        return floor;
    }

    public void generateFloor() {
        floor.generateFloor();
        for(Entity entity: controller.turnBasedEntities) {
            entity.setNextTile(null);
        }
    }
}
