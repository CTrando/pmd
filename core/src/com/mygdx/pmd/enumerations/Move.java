package com.mygdx.pmd.enumerations;

/**
 * Created by Cameron on 7/23/2016.
 */
public enum Move {
    NOMOVE(false),
    SCRATCH(true),
    GROWL(false),
    POUND(false);

    private final boolean isRanged;

    Move(final boolean isRanged){
        this.isRanged = isRanged;
    }

    public boolean isRanged(){
        return isRanged;
    }

}
