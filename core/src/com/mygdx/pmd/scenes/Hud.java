package com.mygdx.pmd.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.pmd.PMD;
import com.mygdx.pmd.screens.DungeonScreen;

/**
 * Created by Cameron on 11/26/2016.
 */
public class Hud {
    public Stage stage;
    public Viewport viewport;
    private float accumTime = 0;

    Label timeLabel;
    Label floorLabel;
    Label testLabel;
    Label turnLabel;

    BitmapFont customFont;
    DungeonScreen screen;

    public Hud(DungeonScreen screen, SpriteBatch batch) {
        this.screen = screen;
        customFont = new BitmapFont(Gdx.files.internal("ui/myCustomFont.fnt"));

        OrthographicCamera hudCam = new OrthographicCamera();

        viewport = new ScreenViewport(hudCam);
        stage = new Stage(viewport, batch);

        Table onScreenController = new Table();
        onScreenController.center();
        onScreenController.top();
        onScreenController.setFillParent(true);
        Skin skin = new Skin(Gdx.files.internal("ui/test.json"));

        testLabel = new Label("HP: " + screen.controller.pokemonPlayer.hp, skin);
        floorLabel = new Label("Floor: " + screen.controller.floorCount, skin);
        timeLabel = new Label("Time left:" + screen.time, skin);
        turnLabel = new Label("Turns left: " + screen.controller.turns, skin);


        Image upImg = new Image(PMD.sprites.get("uparrow"));
        upImg.setScale(.5f);
        upImg.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                PMD.keys.get(Input.Keys.UP).set(true);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                PMD.keys.get(Input.Keys.UP).set(false);
            }
        });

        Image downImg = new Image(PMD.sprites.get("downarrow"));
        downImg.setScale(.5f);
        downImg.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                PMD.keys.get(Input.Keys.DOWN).set(true);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                PMD.keys.get(Input.Keys.DOWN).set(false);
            }
        });

        Image leftImg = new Image(PMD.sprites.get("leftarrow"));
        leftImg.setScale(.5f);
        leftImg.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                PMD.keys.get(Input.Keys.LEFT).set(true);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                PMD.keys.get(Input.Keys.LEFT).set(false);
            }
        });

        Image rightImg = new Image(PMD.sprites.get("rightarrow"));
        rightImg.setScale(.5f);
        rightImg.setWidth(rightImg.getWidth()/2);
        rightImg.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                PMD.keys.get(Input.Keys.RIGHT).set(true);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                PMD.keys.get(Input.Keys.RIGHT).set(false);
            }
        });

        onScreenController.debug();
        onScreenController.add();
        onScreenController.add(upImg).pad(15, 15, 15, 15);

        onScreenController.row();
        onScreenController.add(leftImg).pad(15, 15, 15, 15);
        onScreenController.add();
        onScreenController.add();
        onScreenController.add(rightImg).pad(15, 15, 15, 15);
        onScreenController.row();
        onScreenController.add();
        onScreenController.add(downImg).pad(15, 15, 15, 15);

        Table temp = new Table();
        temp.setFillParent(true);
        temp.left().bottom();
        temp.debug();
        temp.add(testLabel);
        temp.row();
        temp.add(floorLabel);
        temp.row();
        temp.add(turnLabel);

        onScreenController.right().padRight(10).bottom();

        //if (Gdx.app.getType() == Application.ApplicationType.Android)
            stage.addActor(onScreenController);
        stage.addActor(temp);
    }

    public void update(float dt) {
        //reason why not appearing is because did not include : in bit map font
        testLabel.setText("HP: " + screen.controller.pokemonPlayer.hp);
        floorLabel.setText("Floor: " + screen.controller.floorCount);
        if(screen.controller.turnsPaused){
            customFont.setColor(Color.BLUE);
        }

        turnLabel.setText("Turns left: " + screen.controller.turns);
    }

}
