package com.mygdx.pmd.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.viewport.*;
import com.mygdx.pmd.PMD;
import com.mygdx.pmd.controller.Controller;
import com.mygdx.pmd.enumerations.Key;
import com.mygdx.pmd.model.Tile.Tile;
import com.mygdx.pmd.scenes.Hud;
import com.mygdx.pmd.utils.Constants;


import static com.mygdx.pmd.PMD.keys;

public class DungeonScreen extends PScreen implements InputProcessor {
    public final com.mygdx.pmd.PMD game;
    SpriteBatch batch;
    ShapeRenderer shapeRenderer;
    Hud hud;

    public Tile[][] tileBoard;

    public Controller controller;

    public static final int windowWidth = 1000;
    public static final int windowLength = 1000; //TODO the stutter might be because of having to reload everything on player movement
    public static final int windowRows = windowLength / Constants.TILE_SIZE;
    public static final int windowCols = windowWidth / Constants.TILE_SIZE;

    public static final int V_WIDTH = 1080;
    public static final int V_HEIGHT = 720;

    public BitmapFont bFont;

    public int time =20;

    public AssetManager manager;

    InputMultiplexer inputMultiplexer;

    private OrthographicCamera gameCamera;
    private Viewport gamePort;

    Stage stage;
    XmlReader xmlReader;
    public boolean timePaused;

    public DungeonScreen(final PMD game) {
        controller = new Controller(this);

        this.game = game;
        batch = game.batch;
        bFont = new BitmapFont();
        bFont.getData().setScale(.8f);

        shapeRenderer = game.shapeRenderer;
        gameCamera = new OrthographicCamera(PMD.WIDTH, PMD.HEIGHT);

        //gamePort = new FitViewport(PMD.WIDTH, PMD.HEIGHT, gameCamera);\
        gamePort = new ScreenViewport(gameCamera);
        hud = new Hud(this, this.batch);

        tileBoard = controller.currentFloor.tileBoard;
        stage = new Stage();

        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(this);
        inputMultiplexer.addProcessor(stage);
        inputMultiplexer.addProcessor(hud.stage);
        Gdx.input.setInputProcessor(inputMultiplexer);

        //manager.get("sfx/background.ogg", Music.class).play();
    }

    @Override
    public void render(float dt) {
        if(controller.turns < 0){
            game.setScreen(PMD.endScreen);
        }

        controller.update();
        this.updateCamera();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(gameCamera.combined);
        batch.begin();

        for (int i = 0; i < controller.currentFloor.tileBoard.length; i++) {
            for (int j = 0; j < controller.currentFloor.tileBoard[0].length; j++) {
                Tile tile = controller.currentFloor.tileBoard[i][j];
                tile.render(batch);
                //drawing strings like this is very costly performance wise and causes stuttering
                //bFont.draw(batch, tile.spriteValue+"", tile.x + 5, tile.y+25/2);
            }
        }

        for (int i = 0; i< controller.renderList.size(); i++){
            controller.renderList.get(i).render(batch);
        }

        batch.end();
        if (controller.isKeyPressed(Key.t)) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(1, 1, 0, 1);
            shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
            for (int i = 0; i < tileBoard.length; i++) {
                for (int j = 0; j < tileBoard[0].length; j++) {
                    Tile tile = tileBoard[i][j];
                    shapeRenderer.rect(tile.x, tile.y, Constants.TILE_SIZE, Constants.TILE_SIZE);
                }
            }

            shapeRenderer.end();
        }
        stage.draw();

        batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.update(dt);
        hud.stage.draw();
    }

    @Override
    public void dispose() {
        batch.dispose();
        manager.dispose();
        stage.dispose();
    }

    @Override
    public void show() {
        //set the global amount of time
        time = 10;
        controller = new Controller(this);
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

    public void updateCamera() {
        gameCamera.position.set(controller.pokemonPlayer.x, controller.pokemonPlayer.y, 0);
        gameCamera.update();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keys.containsKey(keycode))
            keys.get(keycode).set(true);
        hud.addText(Input.Keys.toString(keycode));
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keys.containsKey(keycode))
            keys.get(keycode).set(false);
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
