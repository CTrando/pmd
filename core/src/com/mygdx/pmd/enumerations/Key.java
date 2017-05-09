package com.mygdx.pmd.enumerations;

import com.badlogic.gdx.Input;

/**
 * Created by Cameron on 8/29/2016.
 */
public enum Key {
    up(Input.Keys.UP), down(Input.Keys.DOWN), left(Input.Keys.LEFT), right(Input.Keys.RIGHT), space(Input.Keys.SPACE),
    s(Input.Keys.S), a(Input.Keys.A, 110), b(Input.Keys.B), t(Input.Keys.T), r(Input.Keys.R), p(Input.Keys.P), k(Input
                                                                                                                         .Keys.K),
    m(Input.Keys.M), escape(Input.Keys.ESCAPE), shift(Input.Keys.SHIFT_LEFT), F11(Input.Keys.F11), IK(-1), c(Input
                                                                                                                     .Keys.C),
    TAB(Input.Keys.TAB);

    private final int value;
    private long lastTimeHit;
    private long timeLimit;

    Key(final int val, final long timeLimit) {
        this.value = val;
        this.timeLimit = timeLimit;
        lastTimeHit = 0;
    }

    Key(final int newValue) {
        this(newValue, 500);
    }

    public void setLastTimeHit(long lastTimeHit) {
        this.lastTimeHit = lastTimeHit;
    }

    public long getLastTimeHit() {
        return lastTimeHit;
    }

    public int getValue() {
        return value;
    }

    public String toString() {
        return this.name();
    }

    public long getTimeLimit() {
        return timeLimit;
    }
}
