package com.mygdx.pmd.comparators;

import com.badlogic.ashley.core.Entity;

import java.util.Comparator;

import static com.mygdx.pmd.model.Behavior.Mappers.*;

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
    public int compare(Entity o1, Entity o2) {
        if (this.distanceFormula(pm.get(entity).x, pm.get(entity).y, pm.get(o1).x, pm.get(o1).y) < this.distanceFormula(pm.get(entity).x, pm.get(entity).y, pm.get(o2).x, pm.get(o2).y))
            return -1;
        else if (this.distanceFormula(pm.get(entity).x, pm.get(entity).y, pm.get(o1).x, pm.get(o1).y) > this.distanceFormula(pm.get(entity).x, pm.get(entity).y, pm.get(o2).x, pm.get(o2).y))
            return 1;
        else return 0;
    }

    public double distanceFormula(int x1, int y1, int x2, int y2) {
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }
}
