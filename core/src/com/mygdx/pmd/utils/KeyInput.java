package com.mygdx.pmd.utils;

import com.badlogic.gdx.Gdx;

public class KeyInput {

    public static boolean pressed(int key) {
        return Gdx.input.isKeyPressed(key);
    }
}
