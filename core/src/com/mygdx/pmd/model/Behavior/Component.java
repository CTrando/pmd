package com.mygdx.pmd.model.Behavior;

import com.mygdx.pmd.model.Entity.*;

/**
 * Created by Cameron on 2/16/2017.
 */
public abstract class Component {

    public static final Component NOCOMPONENT = new NoComponent(null);

    public static int SPAWN = 7;
    public static int LOGIC = 1;
    public static int ANIMATION = 2;
    protected Entity entity;

    public static int COLLISION = 6;
    public static int ATTACK = 5;
    public static int MOVE = 0;
    public static int DIRECTION = 3;
    public static int HP = 4;

    public Component(Entity entity){
        this.entity = entity;
    }

    public abstract void update();
}
