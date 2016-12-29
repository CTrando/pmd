package com.mygdx.pmd.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.viewport.*;
import com.mygdx.pmd.PMD;
import com.mygdx.pmd.controller.Controller;
import com.mygdx.pmd.enumerations.Key;
import com.mygdx.pmd.model.Tile.Tile;
import com.mygdx.pmd.scenes.Hud;
import com.mygdx.pmd.utils.Constants;
import com.mygdx.pmd.utils.Timer;
import com.mygdx.pmd.ui.Button;
import com.mygdx.pmd.ui.Menu;


import java.io.IOException;
import java.util.HashMap;

import static com.mygdx.pmd.PMD.keys;

public class DungeonScreen extends PScreen implements InputProcessor {
    public final com.mygdx.pmd.PMD game;
    SpriteBatch batch;
    ShapeRenderer shapeRenderer;
    Hud hud;

    public Controller controller;
    public Tile[][] tileBoard;

    public static final int windowWidth = 1000;
    public static final int windowLength = 1000; //TODO the stutter might be because of having to reload everything on player movement
    public static final int windowRows = windowLength / Constants.TILE_SIZE;
    public static final int windowCols = windowWidth / Constants.TILE_SIZE;

    public static final int V_WIDTH = 1080;
    public static final int V_HEIGHT = 720;

    public BitmapFont bfont = new BitmapFont();

    public int time =300;

    public AssetManager manager;
    public Array<Button> updateButtonList;

    InputMultiplexer inputMultiplexer;

    private OrthographicCamera gameCamera;
    private Viewport gamePort;

    public static Menu currentMenu;
    Stage stage;
    XmlReader xmlReader;

    public static HashMap<String, Menu> menuList;

    public DungeonScreen(final PMD game) {
        controller = new Controller(this);

        this.game = game;
        batch = game.batch;
        shapeRenderer = game.shapeRenderer;
        gameCamera = new OrthographicCamera(PMD.WIDTH, PMD.HEIGHT);

        if(Gdx.app.getType() == Application.ApplicationType.Android) {
            gameCamera.zoom -= .5;
        }
        //gamePort = new FitViewport(PMD.WIDTH, PMD.HEIGHT, gameCamera);\
        gamePort = new ScreenViewport(gameCamera);
        hud = new Hud(this, this.batch);


        tileBoard = controller.tileBoard;
        this.loadMenus();

        stage = new Stage();
        currentMenu = menuList.get("defaultMenu");
        stage.addActor(currentMenu);

        updateButtonList = new Array<Button>();

       /* Timer timer = new Timer(controller);
        timer.ticking();*/


        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(this);
        inputMultiplexer.addProcessor(stage);
        inputMultiplexer.addProcessor(hud.stage);
        Gdx.input.setInputProcessor(inputMultiplexer);

        //manager.get("sfx/background.ogg", Music.class).play();
    }

    public void switchMenus(String menu) {
        stage.clear();
        currentMenu = menuList.get(menu);
        stage.addActor(currentMenu);
    }

    public void loadMenus() {
        xmlReader = new XmlReader();
        menuList = new HashMap<String, Menu>();

        XmlReader.Element root = null;

        try {
            root = xmlReader.parse(Gdx.files.internal("ui/MenuStorage.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Array<XmlReader.Element> MenuList = root.getChildrenByName("Menu");

        for (XmlReader.Element e : MenuList) {
            Menu menu = new Menu();
            menu.setFillParent(true);
            menu.top().right();

            TextureAtlas textureAtlas = new TextureAtlas(e.get("textureatlas"));
            Skin skin = new Skin(Gdx.files.internal(e.get("json")), textureAtlas);

            for (XmlReader.Element child : e.getChildrenByName("Button")) {
                Button button = new Button(child.get("text"), child.get("classifier"), skin, menu, child.get("nextMenu"), controller);
                menu.addButton(button);
                menu.row().width(200);
            }
            menuList.put(e.get("MenuName"), menu);
        }
    }

    @Override
    public void render(float dt) {
        controller.update();
        this.updateCamera();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(gameCamera.combined);
        batch.begin();

       /* for (Button b : currentMenu.updateButtonList) {
            b.update();
        } //TODO Fix the buttons so that they can adapt to changes in the entity list*/

        for (int i = 0; i < controller.tileBoard.length; i++) {
            for (int j = 0; j < controller.tileBoard[0].length; j++) {
                Tile tile = controller.tileBoard[i][j];
                tile.render(batch);
                //drawing strings like this is very costly performance wise and causes stuttering
                //bfont.draw(batch, tile.spriteValue+"", tile.x + 7, tile.y+ 17);
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

    public void reset(){
        controller = new Controller(this);
    }


    @Override
    public void dispose() {
        batch.dispose();
        manager.dispose();
        stage.dispose();
    }

    @Override
    public void show() {

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
