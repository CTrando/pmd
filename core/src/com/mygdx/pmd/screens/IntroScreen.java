package com.mygdx.pmd.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.pmd.PMD;

import javax.xml.soap.Text;

/**
 * Created by Cameron on 2/10/2017.
 */
public class IntroScreen extends PScreen implements InputProcessor{
    PMD game;
    OrthographicCamera gameCamera;
    Stage stage;
    Skin skin;
    InputMultiplexer inputMultiplexer;

    public IntroScreen(final PMD game) {
        this.game = game;
        gameCamera = new OrthographicCamera(PMD.WIDTH, PMD.HEIGHT);
        stage = new Stage(new ScreenViewport(gameCamera));
        skin = new Skin(Gdx.files.internal("ui/test.json"));

        TextButton button = new TextButton("Hello", skin);
        button.setSize(Gdx.graphics.getWidth(), 100);
        button.setPosition(0, Gdx.graphics.getHeight() - 100);

        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.switchScreen(PMD.dungeonScreen);
            }
        });
        stage.addActor(button);

    }

    @Override
    public void show() {
        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(this);
        inputMultiplexer.addProcessor(stage);

        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
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

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
