package com.mygdx.pmd.utils;

import com.badlogic.gdx.Gdx;

public class KeyInput {

    public boolean pressed(int key) {
        return Gdx.input.isKeyPressed(key);
    }

    public boolean justPressed(int key) {
        return Gdx.input.isKeyJustPressed(key);
    }
}
