package com.mygdx.pmd.Enumerations;

import com.badlogic.gdx.Input;

/**
 * Created by Cameron on 8/29/2016.
 */
public enum Keys {
    up(Input.Keys.UP), down(Input.Keys.DOWN), left(Input.Keys.LEFT), right(Input.Keys.RIGHT), space(Input.Keys.SPACE), s(Input.Keys.S), a(Input.Keys.A), b(Input.Keys.B), t(Input.Keys.T);

    private final int value;

    Keys(final int newValue) {
        value = newValue;
    }

    public int getValue()
    {
        return value;
    }

}
