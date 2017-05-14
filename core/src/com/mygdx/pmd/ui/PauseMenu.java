package com.mygdx.pmd.ui;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.pmd.controller.Controller;

/**
 * Created by Cameron on 5/14/2017.
 */
public class PauseMenu {
    private Stage menu;
    private OrthographicCamera menuCam;
    private ScreenViewport viewport;
    private Controller controller;
    private SpriteBatch batch;
    private Skin skin;
    private Table rootTable;

    public PauseMenu(Controller controller, Skin skin, SpriteBatch batch){
        this.controller = controller;
        this.batch = batch;
        this.skin = skin;

        menuCam = new OrthographicCamera();
        viewport = new ScreenViewport(menuCam);
        menu = new Stage(viewport, batch);

        init();
    }

    private void init(){
        rootTable = new Table();
        rootTable.setDebug(true);
        rootTable.setFillParent(true);
        rootTable.add(new Label("PAUSED", skin, "title")).expand().center();
        rootTable.setVisible(false);
        menu.addActor(rootTable);
    }

    public Stage getStage() {
        return menu;
    }

    public boolean isVisible() {
        return rootTable.isVisible();
    }

    public void update(float dt) {
        menu.act(dt);
    }

    public void setVisible(boolean visible) {
        this.rootTable.setVisible(visible);
    }
}
