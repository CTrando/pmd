package com.mygdx.pmd.utils;

import com.badlogic.gdx.Gdx;

/**
 * Created by Cameron on 11/1/2016.
 */
public class Constants {
    public static int WIDTH = Gdx.graphics.getWidth();
    public static int HEIGHT = Gdx.graphics.getHeight();

    public static final int TILE_SIZE = 25;

    private static final int tileBoardWidth = 1000;
    private static final int tileBoardHeight = 1000;

    public static final int tileBoardRows = tileBoardHeight / Constants.TILE_SIZE;
    public static final int tileBoardCols = tileBoardWidth / Constants.TILE_SIZE;
    public static final int MAX_CONNCETORS = 20;

    public static final int V_WIDTH = 1080;
    public static final int V_HEIGHT = 720;

    public static final int NUM_MAX_ENTITY = 1000;
    public static final int VISIBILITY_RANGE = 3;
}


