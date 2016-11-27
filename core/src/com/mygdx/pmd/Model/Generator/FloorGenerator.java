package com.mygdx.pmd.model.Generator;


import com.mygdx.pmd.controller.Controller;
import com.mygdx.pmd.model.Entity.Entity;
import com.mygdx.pmd.model.FloorComponent.Floor;
import com.mygdx.pmd.model.Tile.Tile;

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
