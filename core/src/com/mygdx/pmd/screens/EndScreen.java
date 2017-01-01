package com.mygdx.pmd.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.pmd.PMD;

/**
 * Created by Cameron on 12/28/2016.
 */
public class EndScreen extends PScreen {
    public BitmapFont bfont = new BitmapFont();
    public PMD game;
    public SpriteBatch batch;

    public EndScreen(final PMD game) {
        this.game = game;
        this.batch = game.batch;
    }

    @Override
    public void show() {
        System.out.println("working");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if(Gdx.input.justTouched()){
            game.setScreen(PMD.dungeonScreen);
        }
        batch.begin();
        bfont.draw(batch, "Click to play again", Gdx.graphics.getHeight() / 2, Gdx.graphics.getWidth() / 2);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
