package com.mygdx.pmd.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.*;
import com.mygdx.pmd.PMD;
import com.mygdx.pmd.controller.Controller;
import com.mygdx.pmd.interfaces.Renderable;
import com.mygdx.pmd.model.Tile.*;
import com.mygdx.pmd.scenes.Hud;

public class DungeonScreen extends PScreen implements InputProcessor {
    public final PMD game;
    private SpriteBatch batch;

    public Array<Renderable> renderList;

    private Hud hud;
    public boolean showHub = true;

    public Controller controller;
    public Tile[][] tileBoard;

    public BitmapFont bFont;
    private InputMultiplexer inputMultiplexer;

    private OrthographicCamera gameCamera;
    private Viewport gamePort;

    public DungeonScreen(final PMD game) {
        //init rendering stuff first
        this.game = game;
        this.batch = game.batch;
        this.renderList = new Array<Renderable>();

        gameCamera = new OrthographicCamera(PMD.WIDTH, PMD.HEIGHT);
        gamePort = new ScreenViewport(gameCamera);

        //init stuff for updating
        controller = new Controller(this);
        tileBoard = controller.currentFloor.tileBoard;

        bFont = new BitmapFont(Gdx.files.internal("ui/myCustomFont.fnt"));
        bFont.getData().setScale(.5f);

        //init stuff that needs the controller
        hud = new Hud(this, this.batch);

        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(this);
        inputMultiplexer.addProcessor(hud.stage);

        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void render(float dt) {
        if(Controller.turns < 0){
            game.switchScreen(PMD.endScreen);
        }

        controller.update();
        this.updateCamera();

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(gameCamera.combined);
        batch.begin();
        for (int i = 0; i< renderList.size; i++){
            renderList.get(i).render(batch);
        }
        batch.end();
        batch.setProjectionMatrix(hud.stage.getCamera().combined);

        hud.update(dt);
        if(showHub){
            hud.stage.draw();
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    @Override
    public void show() {
        renderList.clear();
        controller.reset();
        hud.reset();
    }

    @Override
    public void resize(int width, int height) {
        hud.viewport.update(width, height, true);
        gamePort.update(width, height, true);
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

    private void updateCamera() {
        gameCamera.position.set(controller.pokemonPlayer.x, controller.pokemonPlayer.y, 0);
        gameCamera.update();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (PMD.keys.containsKey(keycode))
            PMD.keys.get(keycode).set(true);

        hud.addText(Input.Keys.toString(keycode));
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (PMD.keys.containsKey(keycode))
            PMD.keys.get(keycode).set(false);
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

    public void toggleHub(){
        showHub = !showHub;
    }
}
