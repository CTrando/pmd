package com.mygdx.pmd.model.Spawner;

import com.mygdx.pmd.controller.Controller;
import com.mygdx.pmd.interfaces.Updatable;
import com.mygdx.pmd.model.Entity.Entity;
import com.mygdx.pmd.model.Tile.Tile;

import java.util.Enumeration;

/**
 * Created by Cameron on 12/21/2016.
 */
public class Spawner extends Entity implements Updatable{

    public Spawner(Controller controller, int x, int y) {
        super(controller, x, y);
    }

    @Override
    public void registerObservers() {

    }

    @Override
    public boolean isLegalToMoveTo(Tile tile) {
        return false;
    }
}
