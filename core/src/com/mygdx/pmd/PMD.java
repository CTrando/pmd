package com.mygdx.pmd;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.pmd.Screen.DungeonScreen;

/**
 * Created by Cameron on 9/11/2016.
 */
public class PMD extends Game {

    public SpriteBatch batch;
    public AssetManager manager;

    @Override
    public void create() {
        manager = new AssetManager();
        batch = new SpriteBatch();
        this.setScreen(new DungeonScreen(this));
    }

    public void render()
    {
        try {
            super.render();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void dispose()
    {
        batch.dispose();
    }
}
