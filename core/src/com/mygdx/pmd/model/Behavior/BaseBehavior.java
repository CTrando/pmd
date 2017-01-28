package com.mygdx.pmd.model.Behavior;

import com.mygdx.pmd.controller.Controller;
import com.mygdx.pmd.model.Tile.Tile;
import com.mygdx.pmd.model.Entity.Entity;

/**
 * Created by Cameron on 11/8/2016.
 */
public abstract class BaseBehavior {
    public static final int INPUT_BEHAVIOR = 0;
    public static final int ATTACK_BEHAVIOR = 1;
    public static final int PATHFIND_BEHAVIOR = 2;
    public static final int LOGIC_BEHAVIOR = 3;
    public static final int MOVE_BEHAVIOR = 4;
    public static final int ANIMATION_BEHAVIOR = 5;

    public Controller controller;
    public Entity entity;

    public BaseBehavior(Entity entity){
        this.controller = entity.controller;
        this.entity = entity;
    }

    public abstract void execute();
}
