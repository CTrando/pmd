package com.mygdx.pmd.enumerations;

/**
 * Created by Cameron on 7/23/2016.
 */
public enum Move {
    NOMOVE(false,0,1),
    SCRATCH(true,20,1),
    GROWL(false,0,1),
    POUND(false,10,1), INSTANT_KILLER(true,100000000,5);

    private final boolean isRanged;
    public final int damage;
    public final int speed;

    Move(final boolean isRanged, int damage, int speed){
        this.isRanged = isRanged;
        this.damage = damage;
        this.speed = speed;
    }

    public boolean isRanged(){
        return isRanged;
    }

}
