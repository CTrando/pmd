package com.mygdx.pmd.enumerations;

/**
 * Created by Cameron on 7/25/2016.
 */
public enum Direction {
    up,
    down,
    right,
    left,
    upleft,
    upright,
    downleft,
    downright;

    private Direction opposite;

    static{
        up.opposite = down;
        down.opposite = up;
        left.opposite = right;
        right.opposite = left;
    }

    public Direction getOppositeDirection(){
        return opposite;
    }
}
