package com.mygdx.pmd.render;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Renderer extends ShapeRenderer {
    private static final Renderer sRenderer = new Renderer();

    public static Renderer getInstance() {
        return sRenderer;
    }
}
