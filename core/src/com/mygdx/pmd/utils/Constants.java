package com.mygdx.pmd.utils;

import com.badlogic.gdx.Gdx;

/**
 * Created by Cameron on 11/1/2016.
 */
public class Constants {
    public static int WIDTH = Gdx.graphics.getWidth();
    public static int HEIGHT = Gdx.graphics.getHeight();

    public static final int TILE_SIZE = 32;

    public static final int tileBoardWidth = 10000;
    public static final int tileBoardHeight = 10000;

    public static final int tileBoardRows = tileBoardHeight / Constants.TILE_SIZE;
    public static final int tileBoardCols = tileBoardWidth / Constants.TILE_SIZE;
    public static final int MAX_CONNECTORS = 100;

    public static final int V_WIDTH = 1080;
    public static final int V_HEIGHT = 720;

    public static final int NUM_MAX_ENTITY = 15;
    public static final int VISIBILITY_RANGE = 5;
}


