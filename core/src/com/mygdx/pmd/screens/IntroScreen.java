package com.mygdx.pmd.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.*;
import com.mygdx.pmd.PMD;
import com.mygdx.pmd.utils.Constants;

/**
 * Created by Cameron on 2/10/2017.
 */
public class IntroScreen extends PScreen implements InputProcessor{
    PMD game;
    OrthographicCamera gameCamera;
    Viewport view;
    Stage stage;
    Skin skin;
    private InputMultiplexer inputMultiplexer;

    public IntroScreen(final PMD game) {
        this.game = game;

        Table mainTable = new Table();

        gameCamera = new OrthographicCamera(Constants.WIDTH, Constants.HEIGHT);
        view = new FitViewport(Constants.WIDTH, Constants.HEIGHT, gameCamera);
        stage = new Stage(view);
        skin = new Skin(Gdx.files.internal("ui/test.json"));

        Label label = new Label("Welcome to this poorly made UI", skin);
        label.setWidth(Constants.WIDTH);
        label.setPosition(0, Constants.HEIGHT*.9f);
        label.setAlignment(Align.center);

        TextButton playButton = new TextButton("Click to play", skin);
        playButton.setPosition(Constants.WIDTH*.1f, Constants.HEIGHT*.3f);
        playButton.addAction(Actions.repeat(RepeatAction.FOREVER,Actions.sequence(Actions.moveTo(Constants.WIDTH*.1f,Constants.HEIGHT*.3f,1),
                Actions.moveTo(Constants.WIDTH*.1f,Gdx.graphics.getHeight()*.28f,1))));

        playButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.switchScreen(PMD.dungeonScreen);
            }
        });

        TextButton optionsButton = new TextButton("Click me to go to the options", skin);
        optionsButton.setPosition(Constants.WIDTH*.7f, Constants.HEIGHT*.3f);
        optionsButton.addAction(Actions.repeat(RepeatAction.FOREVER,Actions.sequence(Actions.moveTo(Constants.WIDTH*.7f,Constants.HEIGHT*.3f,1),
                Actions.moveTo(Constants.WIDTH*.7f,Gdx.graphics.getHeight()*.28f,1))));


        mainTable.debug();
        mainTable.addActor(label);
        mainTable.addActor(playButton);
        mainTable.addActor(optionsButton);

        mainTable.setFillParent(true);
        mainTable.setBackground(skin.getDrawable("background.9"));
        stage.addActor(mainTable);


        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(this);
        inputMultiplexer.addProcessor(stage);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        Constants.WIDTH = Gdx.graphics.getWidth();
        Constants.HEIGHT = Gdx.graphics.getHeight();

        stage.getViewport().update(width,height,true);
        view.update(width, height, true);
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
