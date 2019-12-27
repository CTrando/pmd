package com.mygdx.pmd.enums;

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

    public Direction getOpposite(){
        return opposite;
    }

    public String toString() {
        switch(this) {
            case up:
                return "Up";
            case down:
                return "Down";
            case left:
                return "Left";
            case right:
                return "Right";
            default:
                return "None";
        }
    }

    public String format(String input) {
        return String.format("%s%s", input, toString());
    }
}
