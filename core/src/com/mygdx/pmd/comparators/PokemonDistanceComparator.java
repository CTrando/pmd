package com.mygdx.pmd.comparators;

import com.mygdx.pmd.model.Entity.Entity;
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
        PositionComponent entityPC = (PositionComponent) entity.getComponent(Component.POSITION);
        PositionComponent firstPC = (PositionComponent) first.getComponent(Component.POSITION);
        PositionComponent secondPC = (PositionComponent) second.getComponent(Component.POSITION);

        if(secondPC ==null || firstPC == null){
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
            return 1;
        } else {
            return 0;
        }
    }

    public double distanceFormula(int x1, int y1, int x2, int y2) {
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }
}
