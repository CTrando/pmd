package com.mygdx.pmd.Model.Behavior;

import com.mygdx.pmd.Controller.Controller;
import com.mygdx.pmd.Model.Pokemon.Pokemon;
import com.mygdx.pmd.Model.TileType.Tile;
import com.mygdx.pmd.utils.Entity;

/**
 * Created by Cameron on 11/8/2016.
 */
public abstract class Behavior {
    public static final int INPUT_BEHAVIOR = 0;
    public static final int ATTACK_BEHAVIOR = 1;
    public static final int LOGIC_BEHAVIOR = 2;
    public static final int MOVE_BEHAVIOR = 3;
    public static final int ANIMATION_BEHAVIOR = 4;

    Controller controller;
    Tile[][] tileBoard;
    Entity entity;

    public Behavior(Entity entity){
        this.controller = entity.controller;
        this.tileBoard = controller.tileBoard;
        this.entity = entity;
    }

    public abstract void execute();
}
