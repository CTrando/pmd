package com.mygdx.pmd.Screen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;
import com.mygdx.pmd.Controller.Controller;
import com.mygdx.pmd.Enumerations.Key;
import com.mygdx.pmd.Interfaces.Renderable;
import com.mygdx.pmd.Model.Entity.Entity;
import com.mygdx.pmd.Model.Entity.Pokemon.Pokemon;
import com.mygdx.pmd.Model.Tile.Tile;
import com.mygdx.pmd.PMD;
import com.mygdx.pmd.Model.Entity.Projectile.Projectile;
import com.mygdx.pmd.utils.Constants;
import com.mygdx.pmd.utils.Timer;
import com.mygdx.pmd.ui.Button;
import com.mygdx.pmd.ui.Menu;
import com.mygdx.pmd.utils.PAnimation;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class DungeonScreen implements InputProcessor, Screen {
    final PMD game;
    SpriteBatch batch;
    ShapeRenderer shapeRenderer;

    public Controller controller;
    public Tile[][] tileBoard;

    public static final int windowWidth = 1000;
    public static final int windowLength = 1000; //TODO the stutter might be because of having to reload everything on player movement
    public static final int windowRows = windowLength / Constants.TILE_SIZE;
    public static final int windowCols = windowWidth / Constants.TILE_SIZE;

    public AssetManager manager;
    public static HashMap<String, Sprite> sprites = new HashMap<String, Sprite>();
    public Array<Button> updateButtonList;

    InputMultiplexer inputMultiplexer;


    OrthographicCamera camera;

    public static Menu currentMenu;
    Stage stage;
    XmlReader xmlReader;

    public static HashMap<Integer, AtomicBoolean> keys;
    public static HashMap<String, Menu> menuList;

    public static final boolean isFullView = true;

    public static final int APP_WIDTH = 1080;//Gdx.graphics.getWidth();
    public static final int APP_HEIGHT = 720;//Gdx.graphics.getHeight();

    public DungeonScreen(final PMD game) {
        this.game = game;
        batch = game.batch;
        shapeRenderer = new ShapeRenderer();

        this.loadManager();
        controller = new Controller(this);
        tileBoard = controller.tileBoard;
        this.loadMenus();

        stage = new Stage();
        currentMenu = menuList.get("defaultMenu");
        stage.addActor(currentMenu);


        keys = new HashMap<Integer, AtomicBoolean>();
        updateButtonList = new Array<Button>();

        this.loadKeys();

        Timer timer = new Timer(controller);
        timer.ticking();

        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        camera.update();

        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(this);
        inputMultiplexer.addProcessor(stage);

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


    public void loadManager() {
        manager = game.manager;

        manager.load("pokemonassets/TREEKO_WALKSHEET.atlas", TextureAtlas.class);
        manager.load("pokemonassets/TILE_SPRITES.atlas", TextureAtlas.class);
        manager.load("pokemonassets/SQUIRTLE_WALKSHEET.atlas", TextureAtlas.class);
        manager.load("sfx/background.ogg", Music.class);
        manager.load("sfx/wallhit.wav", Sound.class);
        manager.finishLoading();

        this.loadImages(manager.get("pokemonassets/TREEKO_WALKSHEET.atlas", TextureAtlas.class));
        this.loadImages(manager.get("pokemonassets/TILE_SPRITES.atlas", TextureAtlas.class));
        this.loadImages(manager.get("pokemonassets/SQUIRTLE_WALKSHEET.atlas", TextureAtlas.class));

    }

    public void loadKeys() {
        for (Key key : Key.values()) {
            keys.put(key.getValue(), new AtomicBoolean(false));
        }
    }

    @Override
    public void render(float delta) {
        this.updateCamera();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        for (Button b : currentMenu.updateButtonList) {
            b.update();
        }

        for (int i = 0; i< controller.renderList.size(); i++){
            controller.renderList.get(i).render(batch);
        }

        //    batch.draw(animation.getCurrentSprite(), 100, 100);

        /* Not sure about this, might be good need to do more reserach
        new SpriteDrawable(animation.getCurrentSprite()).draw(batch, 100,100,22,22);
        */

        batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(1, 1, 0, 1);
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        if (controller.isKeyPressed(Key.t)) {
            for (int i = 0; i < tileBoard.length; i++) {
                for (int j = 0; j < tileBoard[0].length; j++) {
                    Tile tile = tileBoard[i][j];
                    shapeRenderer.rect(tile.x, tile.y, Constants.TILE_SIZE, Constants.TILE_SIZE);
                }
            }
        }
        shapeRenderer.end();
        stage.draw();
    }

    @Override
    public void dispose() {
        batch.dispose();
        manager.dispose();
        stage.dispose();
    }

    public void loadImages(TextureAtlas textureAtlas) {
        for (TextureAtlas.AtlasRegion textureRegion : textureAtlas.getRegions()) {
            Sprite sprite = textureAtlas.createSprite(textureRegion.name);
            sprites.put(textureRegion.name, sprite);
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.position.set(width / 2f, height / 2f, 0);
        camera.update();

        batch.getProjectionMatrix().setToOrtho2D(0, 0, width, height);
        stage.getViewport().update(width, height);

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
        camera.position.set(controller.pokemonPlayer.x, controller.pokemonPlayer.y, 0);
        camera.update();
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
