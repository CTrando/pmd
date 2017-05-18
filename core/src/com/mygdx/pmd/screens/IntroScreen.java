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

import javax.sound.midi.Sequence;

/**
 * Created by Cameron on 2/10/2017.
 */
public class IntroScreen extends PScreen implements InputProcessor {
    private PMD game;
    private Stage stage;
    private Skin skin;
    private InputMultiplexer inputMultiplexer;

    public IntroScreen(final PMD game) {
        this.game = game;
        this.stage = new Stage();
        this.skin = new Skin(Gdx.files.internal("ui/skin/flat-earth-ui.json"));

        Table rootTable = new Table(skin);
        //rootTable.debug();
        rootTable.setFillParent(true);

        Label titleLabel = new Label("Pokemon Mystery Dungeon Clone", skin);
        titleLabel.setAlignment(Align.center);

        TextButton playButton = new TextButton("Play", skin);
       /* playButton.addAction(Actions.repeat(RepeatAction.FOREVER, Actions.sequence(
                Actions.moveTo(Constants.WIDTH * .1f, Constants.HEIGHT * .3f, 1),
                Actions.moveTo(Constants.WIDTH * .1f, Gdx.graphics.getHeight() * .28f, 1))));
*/
        playButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                SequenceAction sq = new SequenceAction(
                        Actions.fadeOut(.5f),
                        Actions.addAction(Actions.run(new Runnable() {
                            @Override
                            public void run() {
                                game.setScreen(PMD.dungeonScreen);
                            }
                        }))
                );
                stage.addAction(sq);
            }
        });

        TextButton optionsButton = new TextButton("Options", skin);/*
        *//*optionsButton.setPosition(Constants.WIDTH * .5f, Constants.HEIGHT * .3f);
        optionsButton.addAction(Actions.repeat(RepeatAction.FOREVER, Actions.sequence(Actions.moveTo(Constants
                                                                                                             .WIDTH * .5f,
                    *//*                                                                                 Constants.HEIGHT * .3f,
                                                                                                     1),
                                                                                      Actions.moveTo(
                                                                                              Constants.WIDTH * .5f,
                                                                                              Gdx.graphics.getHeight() * .28f,
                                                                                              1))));*/
        //TODO cells can only be added to one table surprisingly
        Table optionsTable = new Table(skin);
        optionsTable.defaults().width(.3f * Constants.WIDTH).fill();
        optionsTable.add(playButton)
                    .align(Align.bottomLeft)
                    .row();
        optionsTable.add(optionsButton)
                    .align(Align.bottomLeft)
                    .row();
        optionsTable.add(new TextField("Enter your name please.", skin));


        rootTable.add(titleLabel)
                 .expand()
                 .center();
        rootTable.row();


        rootTable.add(optionsTable)
                 .expandX()
                 .bottom()
                 .left();

        stage.addActor(rootTable);
        //stage.setDebugAll(true);


        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(this);
        inputMultiplexer.addProcessor(stage);
    }

    @Override
    public void show() {
        SequenceAction sq = new SequenceAction(
                Actions.fadeOut(.01f),
                Actions.fadeIn(.5f)
        );
        stage.addAction(sq);
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

        stage.getViewport().update(width, height, true);
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
