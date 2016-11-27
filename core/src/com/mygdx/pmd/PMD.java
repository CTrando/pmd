package com.mygdx.pmd;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.pmd.controller.Controller;
import com.mygdx.pmd.screens.DungeonScreen;
import com.mygdx.pmd.screens.PScreen;

/**
 * Created by Cameron on 9/11/2016.
 */
public class PMD extends Game {
    public static final int WIDTH = 1080;
    public static final int HEIGHT = 720;

    public static final String TITLE = "Pokemon Mystery Dungeon";

    public SpriteBatch batch;
    public ShapeRenderer shapeRenderer;

    public AssetManager manager;
    public Controller controller;

    public PScreen currentScreen;

    @Override
    public void create() {
        manager = new AssetManager();
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        currentScreen = new DungeonScreen(this);
        this.setScreen(currentScreen);
    }

    public void render() {
        super.render();
    }

    public void dispose() {
        batch.dispose();
    }
}
