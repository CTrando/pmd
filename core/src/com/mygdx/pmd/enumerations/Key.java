package com.mygdx.pmd.enumerations;

import com.badlogic.gdx.Input;

/**
 * Created by Cameron on 8/29/2016.
 */
public enum Key {
    up(Input.Keys.UP), down(Input.Keys.DOWN), left(Input.Keys.LEFT), right(Input.Keys.RIGHT), space(Input.Keys.SPACE),
    s(Input.Keys.S), a(Input.Keys.A), b(Input.Keys.B), t(Input.Keys.T), r(Input.Keys.R), p(Input.Keys.P), k(Input.Keys.K),
    m(Input.Keys.M), escape(Input.Keys.ESCAPE), shift(Input.Keys.SHIFT_LEFT), F11(Input.Keys.F11), IK(-1);

    private final int value;
    private long lastTimeHit;

    Key(final int newValue) {
        value = newValue;
        lastTimeHit = 0;
    }

    public void setLastTimeHit(long lastTimeHit){
        this.lastTimeHit = lastTimeHit;
    }

    public long getLastTimeHit(){
        return lastTimeHit;
    }

    public int getValue()
    {
        return value;
    }

    public String toString(){
        return this.name();
    }

}
