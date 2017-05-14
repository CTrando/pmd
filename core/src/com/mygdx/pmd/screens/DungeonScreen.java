package com.mygdx.pmd.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.*;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.pmd.PMD;
import com.mygdx.pmd.controller.Controller;
import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.model.Tile.*;
import com.mygdx.pmd.model.components.*;
import com.mygdx.pmd.ui.*;
import com.mygdx.pmd.utils.Constants;

import java.util.concurrent.atomic.AtomicBoolean;

public class DungeonScreen extends PScreen implements GestureDetector.GestureListener, InputProcessor {
    public final PMD game;
    public static ShapeRenderer sRenderer;
    private SpriteBatch batch;
    public static final float PPM = 32;

    public Array<RenderComponent> renderList;
    public static Rectangle cameraBounds;
    private Hud hud;
    private PauseMenu pauseMenu;

    private Skin skin;

    public Controller controller;
    public Tile[][] tileBoard;

    private BitmapFont bFont;
    private float timeStep = 0;

    private InputMultiplexer inputMultiplexer;
    private ScreenViewport gamePort;
    private OrthographicCamera gameCamera;

    private CameraMode cameraMode = CameraMode.fixed;

    private enum CameraMode {
        freeroam, fixed
    }

    public DungeonScreen(final PMD game) {
        //init rendering stuff first
        this.game = game;
        this.batch = game.batch;
        this.skin = new Skin(Gdx.files.internal("ui/skin/flat-earth-ui.json"));

        sRenderer = new ShapeRenderer();
        this.renderList = new Array<RenderComponent>();
        cameraBounds = new Rectangle();

        gameCamera = new OrthographicCamera(Gdx.graphics.getWidth() / PPM, Gdx.graphics.getHeight() / PPM);
        gamePort = new ScreenViewport(gameCamera);
        gamePort.setUnitsPerPixel(1 / PPM);

        //init stuff for updating
        controller = new Controller(this);
        tileBoard = controller.floor.tileBoard;

        bFont = new BitmapFont(Gdx.files.internal("ui/myCustomFont.fnt"));
        bFont.getData().setScale(.5f);

        hud = new Hud(controller, skin, batch);
        pauseMenu = new PauseMenu(controller, skin, batch);
    }

    @Override
    public void render(float dt) {
        timeStep += dt;
        if (timeStep >= 1 / 60f) {
            timeStep = 0;
            controller.update();
            updateCamera();
            updateBounds();
        }

        Gdx.gl.glClearColor(0, 0, 0, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glDisable(GL20.GL_BLEND);

        batch.setProjectionMatrix(gamePort.getCamera().combined);
        sRenderer.setProjectionMatrix(gamePort.getCamera().combined);
        sRenderer.begin(ShapeRenderer.ShapeType.Line);
        batch.begin();

        for (int i = 0; i < renderList.size; i++) {
            RenderComponent rc = renderList.get(i);
            rc.render(batch);
        }

        batch.end();
        sRenderer.end();
        batch.setProjectionMatrix(hud.stage.getCamera().combined);
        batch.flush();


        //for some reason it initializes batch,begin in stage.draw - how terrible
        if (hud.isVisible()) {
            hud.update(dt);
            hud.stage.draw();
        }
        if (pauseMenu.isVisible()) {
            pauseMenu.update(dt);
            pauseMenu.getStage().draw();
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
        cameraMode = CameraMode.fixed;
        hud.reset();

        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(new GestureDetector(this));
        inputMultiplexer.addProcessor(hud.stage);
        inputMultiplexer.addProcessor(this);

        hud.setVisible(true);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void resize(int width, int height) {
        hud.viewport.update(width, height, true);
        gamePort.update(width, height, true);
    }

    @Override
    public void pause() {
        pauseMenu.setVisible(true);
    }

    @Override
    public void resume() {
        pauseMenu.setVisible(false);
    }

    @Override
    public void hide() {

    }

    private void updateBounds() {
        if (cameraMode == CameraMode.fixed) {
            float row = controller.pokemonPlayer.pc.y / PPM;
            float col = controller.pokemonPlayer.pc.x / PPM;
            cameraBounds = new Rectangle(col - (gamePort.getWorldWidth() / 2),
                                         row - (gamePort.getWorldHeight() / 2),
                                         gamePort.getWorldWidth() + 1,
                                         gamePort.getWorldHeight() + 1);
        } else {
            float col = gameCamera.position.x;
            float row = gameCamera.position.y;
            cameraBounds = new Rectangle(col - (gamePort.getWorldWidth() / 2),
                                         row - (gamePort.getWorldHeight() / 2),
                                         gamePort.getWorldWidth() + 1,
                                         gamePort.getWorldHeight() + 1);
        }
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
                if (mousePos.x > Constants.tileBoardWidth) {
                    mousePos.x = Constants.tileBoardWidth;
                }

                if (mousePos.y > Constants.tileBoardHeight) {
                    mousePos.y = Constants.tileBoardHeight;
                }

                if (mousePos.x < 0) {
                    mousePos.x = 0;
                }

                if (mousePos.y < 0) {
                    mousePos.y = 0;
                }
                gameCamera.position.lerp(mousePos, .05f);
                gameCamera.update();
                break;
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        if (PMD.keys.containsKey(keycode)) {
            PMD.keys.get(keycode).set(true);
        } else {
            PMD.keys.put(keycode, new AtomicBoolean(true));
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
        if (PMD.isKeyPressed(Input.Keys.SLASH)) {
            hud.getConsole().requestFocus();
        }

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
        hud.getConsole().cancelFocus();
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
        if (hud.isVisible()) {
            hud.setVisible(false);
        } else {
            hud.setVisible(true);
        }
    }

    public Hud getHud() {
        return hud;
    }
}
