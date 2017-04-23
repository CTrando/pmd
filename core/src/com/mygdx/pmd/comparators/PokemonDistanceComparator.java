package com.mygdx.pmd.comparators;

import com.mygdx.pmd.model.Entity.Entity;
import com.mygdx.pmd.model.Tile.*;
import com.mygdx.pmd.model.components.*;

import java.util.Comparator;

/**
 * Created by Cameron on 9/7/2016.
 */
public class PokemonDistanceComparator implements Comparator<Entity> {

    //TODO should probably take in tile distance instead of straight line distance
    private Entity entity;

    public PokemonDistanceComparator(Entity entity) {
        this.entity = entity;
    }


    @Override
    public int compare(Entity first, Entity second) {
        PositionComponent entityPC = entity.getComponent(PositionComponent.class);
        PositionComponent firstPC = first.getComponent(PositionComponent.class);
        PositionComponent secondPC = second.getComponent(PositionComponent.class);

        if (secondPC == null || firstPC == null) {
            return 1;
        }

        if (this.distanceFormula(entityPC.x, entityPC.y, firstPC.x, firstPC.y) < this.distanceFormula(entityPC.x,
                                                                                                    entityPC.y,
                                                                                                    secondPC.x,
                                                                                                    secondPC.y)) {
            return -1;
        } else if (this.distanceFormula(entityPC.x, entityPC.y, firstPC.x, firstPC.y) > this.distanceFormula(entityPC.x,
                                                                                                           entityPC.y,
                                                                                                           secondPC.x,
                                                                                                           secondPC.y)) {
/*        if (this.tileDistance(entityPC.getCurrentTile(), firstPC.getCurrentTile()) > this.tileDistance(entityPC
                                                                                                               .getCurrentTile(),
                                                                                                       secondPC
                                                                                                               .getCurrentTile())) {
            return 1;
        } else if (this.tileDistance(entityPC.getCurrentTile(), firstPC.getCurrentTile()) < this.tileDistance(entityPC
                                                                                                                      .getCurrentTile(),
                                                                                                              secondPC.getCurrentTile())) {*/
            return 1;
        } else {
            return 0;
        }
    }

    private int tileDistance(Tile tile1, Tile tile2) {
        return Math.abs(tile1.row - tile2.row) + Math.abs(tile1.col - tile2.col);
    }

    public int tileDistance(int x1, int y1, int x2, int y2) {
        return Math.abs(x2 - x1) + Math.abs(y2 - y1);
    }

    public double distanceFormula(int x1, int y1, int x2, int y2) {
        return ((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }
}
