package com.mygdx.pmd.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.pmd.PMD;

/**
 * Created by Cameron on 12/28/2016.
 */
public class EndScreen extends PScreen {
    Stage stage;
    Table table;
    public BitmapFont bfont = new BitmapFont();
    public PMD game;
    public SpriteBatch batch;
    public Label gameOverLabel;

    public EndScreen(final PMD game) {
        this.game = game;
        this.batch = game.batch;

        stage = new Stage();
        table = new Table();
        Skin skin = new Skin(Gdx.files.internal("ui/test.json"));

        gameOverLabel = new Label("You died! \n Click to play again", skin);
        table.add(gameOverLabel).center();
        table.setFillParent(true);
        table.pack();

        stage.addActor(table);
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
        stage.draw();
        batch.end();
    }

    public void update(){
        if(Gdx.input.justTouched()){
            game.setScreen(PMD.dungeonScreen);
        }
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
