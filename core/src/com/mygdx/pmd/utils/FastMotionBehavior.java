package com.mygdx.pmd.utils;

/**
 * Created by Cameron on 11/3/2016.
 */
public class FastMotionBehavior extends MotionBehavior {

    public FastMotionBehavior(Entity entity){
        super(entity);
    }

    @Override
    public void move() {
        this.moveFast();
    }


}
