package com.mygdx.pmd.utils;

/**
 * Created by Cameron on 11/3/2016.
 */
public class SlowMotionBehavior extends MotionBehavior {
    public SlowMotionBehavior(Entity entity){
        super(entity);
    }

    @Override
    public void move(){
        this.moveSlow();
    }

}
