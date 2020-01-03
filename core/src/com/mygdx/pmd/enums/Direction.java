package com.mygdx.pmd.enums;

import com.badlogic.gdx.Input;

/**
 * Created by Cameron on 7/25/2016.
 */
public enum Direction {
    up,
    down,
    right,
    left,
    none,
    upleft,
    upright,
    downleft,
    downright;

    private Direction opposite;

    static {
        up.opposite = down;
        down.opposite = up;
        left.opposite = right;
        right.opposite = left;
    }

    public Direction getOpposite() {
        return opposite;
    }

    public String toString() {
        switch (this) {
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

    public static Direction get(int key) {
        switch (key) {
            case Input.Keys.DOWN:
                return down;
            case Input.Keys.UP:
                return up;
            case Input.Keys.RIGHT:
                return right;
            case Input.Keys.LEFT:
                return left;
            default:
                throw new IllegalArgumentException("Invalid keyboard press");
        }
    }

    public String format(String input) {
        return String.format("%s%s", input, toString());
    }
}
