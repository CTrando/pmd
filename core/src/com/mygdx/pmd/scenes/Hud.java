package com.mygdx.pmd.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.pmd.controller.Controller;
import com.mygdx.pmd.screens.DungeonScreen;

/**
 * Created by Cameron on 11/26/2016.
 */
public class Hud {
    public Stage stage;
    private Viewport viewport;

    private Integer worldTimer;
    private float timeCount;

    Label countDownLabel;
    Label timeLabel;
    Label testLabel;

    BitmapFont customFont;

    Controller controller;

    public Hud(Controller controller, SpriteBatch batch){
        this.controller = controller;
        worldTimer = 0;
        timeCount = 0;
        customFont = new BitmapFont(Gdx.files.internal("ui/myCustomFont.fnt"));

        viewport = new FitViewport(DungeonScreen.V_WIDTH, DungeonScreen.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, batch);

        Table table = new Table();
        table.top();
        table.setFillParent(true);
        Skin skin = new Skin(Gdx.files.internal("ui/test.json"));

        countDownLabel = new Label(controller.pokemonPlayer.hp + "", skin);

        timeLabel = new Label("TIME", skin);

        testLabel = new Label("Hope it works", skin);

        table.add(countDownLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);
        table.add(testLabel).expandX().padTop(10);

        stage.addActor(table);
    }
}
