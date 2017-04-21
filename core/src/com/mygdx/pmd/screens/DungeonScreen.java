package com.mygdx.pmd.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.glutils.*;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.*;
import com.mygdx.pmd.PMD;
import com.mygdx.pmd.controller.Controller;
import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.interfaces.Renderable;
import com.mygdx.pmd.model.Entity.Pokemon.*;
import com.mygdx.pmd.model.Tile.*;
import com.mygdx.pmd.model.components.*;
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

    private ScreenViewport gamePort;
    private OrthographicCamera gameCamera;
    private CameraMode cameraMode = CameraMode.fixed;

    private Viewport stagePort;
    private ShaderProgram shader;

    private enum CameraMode {
        freeroam, fixed;
    }

    public DungeonScreen(final PMD game) {
        //init rendering stuff first
        this.game = game;
        this.batch = game.batch;
        this.sRenderer = new ShapeRenderer();
        this.renderList = new Array<Renderable>();

        gameCamera = new OrthographicCamera(Gdx.graphics.getWidth() / PPM, Gdx.graphics.getHeight() / PPM);
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
        batch.setProjectionMatrix(gamePort.getCamera().combined);
        batch.begin();
       /* sRenderer.begin(ShapeRenderer.ShapeType.Filled);
        sRenderer.setProjectionMatrix(gamePort.getCamera().combined);*/
        for (int i = 0; i < renderList.size; i++) {
            renderList.get(i).render(batch);
        }

        batch.end();
        //sRenderer.end();
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
        switch (cameraMode) {
            case fixed:
                float x = MathUtils.round(((float) (controller.pokemonPlayer.pc.x + Constants.TILE_SIZE / 2)));
                float y = MathUtils.round(((float) (controller.pokemonPlayer.pc.y + Constants.TILE_SIZE / 2)));

                Vector3 pos = new Vector3(x / PPM,
                                          y / PPM,
                                          0);
                gameCamera.position.set(pos);
                gameCamera.update();
                break;
            case freeroam:
                int mouseX = Gdx.input.getX();
                int mouseY = Gdx.input.getY();

                Vector3 mousePos = new Vector3(mouseX, mouseY, 0);
                mousePos = gameCamera.unproject(mousePos);
                System.out.println(mousePos.toString());
                if(mousePos.x > gamePort.getWorldWidth()){
                    mousePos.x = gamePort.getWorldWidth();
                }

                if(mousePos.y > gamePort.getWorldHeight()){
                    mousePos.y = gamePort.getWorldHeight();
                }

                if(mousePos.x < 0){
                    mousePos.x = 0;
                }

                if(mousePos.y < 0){
                    mousePos.y = 0;
                }
                gameCamera.position.lerp(mousePos, .01f);
                gameCamera.update();
                break;
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        if (PMD.keys.containsKey(keycode)) {
            PMD.keys.get(keycode).set(true);
        }

        if (PMD.isKeyPressed(Key.c)) {
            switch (cameraMode) {
                case fixed:
                    cameraMode = CameraMode.freeroam;
                    break;
                case freeroam:
                    cameraMode = CameraMode.fixed;
                    break;
            }
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


    public Hud getHud() {
        return hud;
    }
}
