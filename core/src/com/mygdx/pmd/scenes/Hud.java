package com.mygdx.pmd.scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
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

    Controller controller;

    public Hud(Controller controller, SpriteBatch batch){
        this.controller = controller;
        worldTimer = 0;
        timeCount = 0;

        viewport = new FitViewport(DungeonScreen.V_WIDTH, DungeonScreen.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, batch);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        countDownLabel = new Label(String.format("%03d", controller.pokemonPlayer.hp), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(countDownLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);

        stage.addActor(table);
    }

    public void invalidate(){
        countDownLabel.setText(String.format("%03d", controller.pokemonPlayer.hp));
        countDownLabel.invalidate();
    }
}
