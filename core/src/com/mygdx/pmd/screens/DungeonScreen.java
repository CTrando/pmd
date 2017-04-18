package com.mygdx.pmd.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.*;
import com.mygdx.pmd.PMD;
import com.mygdx.pmd.controller.Controller;
import com.mygdx.pmd.interfaces.Renderable;
import com.mygdx.pmd.model.Tile.*;
import com.mygdx.pmd.scenes.Hud;
import com.mygdx.pmd.utils.Constants;

public class DungeonScreen extends PScreen implements GestureDetector.GestureListener, InputProcessor {
    public final PMD game;
    public static ShapeRenderer sRenderer;
    private SpriteBatch batch;

    public static final float PPM = 25;

    public Array<Renderable> renderList;

    private Hud hud;
    private boolean showHub;

    public Controller controller;
    public Tile[][] tileBoard;

    private BitmapFont bFont;
    private InputMultiplexer inputMultiplexer;

    private OrthographicCamera gameCamera;
    private ScreenViewport gamePort;

    private Viewport stagePort;

    public DungeonScreen(final PMD game) {
        //init rendering stuff first
        this.game = game;
        this.batch = game.batch;
        this.sRenderer = new ShapeRenderer();
        this.renderList = new Array<Renderable>();


        gameCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        gamePort = new ScreenViewport(gameCamera);
        gamePort.setUnitsPerPixel(1 / PPM);

        //init stuff for updating
        controller = new Controller(this);
        tileBoard = controller.floor.tileBoard;

        bFont = new BitmapFont(Gdx.files.internal("ui/myCustomFont.fnt"));
        bFont.getData().setScale(.5f);

        //init stuff that needs the controller
        hud = new Hud(this, batch);
    }

    @Override
    public void render(float dt) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        // stage.act();
        controller.update();
        this.updateCamera();

        batch.setColor(Color.WHITE);
        batch.setProjectionMatrix(gameCamera.combined);
        batch.begin();
        sRenderer.begin(ShapeRenderer.ShapeType.Filled);
        sRenderer.setProjectionMatrix(gameCamera.combined);
        for (int i = 0; i < renderList.size; i++) {
            renderList.get(i).render(batch);
        }

        batch.end();
        sRenderer.end();
        batch.setProjectionMatrix(hud.stage.getCamera().combined);
        //for some reason it initializes batch,begin in stage.draw - how terrible

        if (showHub) {
            hud.update(dt);
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

        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(new GestureDetector(this));
        //inputMultiplexer.addProcessor(stage);
        inputMultiplexer.addProcessor(this);

        setShowHud(true);

        Gdx.input.setInputProcessor(inputMultiplexer);
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
        gameCamera.position.set((controller.pokemonPlayer.pc.x + Constants.TILE_SIZE / 2) / PPM,
                                (controller.pokemonPlayer
                                        .pc.y +
                                        Constants.TILE_SIZE / 2) / PPM, 0);
        gameCamera.update();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (PMD.keys.containsKey(keycode)) {
            PMD.keys.get(keycode).set(true);
        }

        hud.addText(Input.Keys.toString(keycode));
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (PMD.keys.containsKey(keycode)) {
            PMD.keys.get(keycode).set(false);
        }
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

    private void setShowHud(boolean show) {
        showHub = show;
        if (showHub) {
            inputMultiplexer.addProcessor(hud.stage);
        } else {
            inputMultiplexer.removeProcessor(hud.stage);
        }
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        if (velocityY < -100) {
            toggleHub();
            return true;
        }
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }

    public void toggleHub() {
        if (showHub) {
            setShowHud(false);
        } else {
            setShowHud(true);
        }
    }

    public OrthographicCamera getCamera() {
        return gameCamera;
    }

    public Hud getHud() {
        return hud;
    }
}
