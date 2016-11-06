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
import com.mygdx.pmd.Model.Pokemon.Pokemon;
import com.mygdx.pmd.Model.TileType.Tile;
import com.mygdx.pmd.PMD;
import com.mygdx.pmd.utils.Constants;
import com.mygdx.pmd.utils.Projectile;
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

    public AssetManager manager;
    public static HashMap<String, Sprite> sprites = new HashMap<String, Sprite>();
    public Array<Button> updateButtonList;

    private Music backgroundSound;

    InputMultiplexer inputMultiplexer;

    Controller controller;
    OrthographicCamera camera;

    public static Menu currentMenu;
    Stage stage;
    XmlReader xmlReader;

    public static HashMap<Integer, AtomicBoolean> keys;
    public static HashMap<String, Menu> menuList;

    public static final boolean isFullView = true;

    public static final int APP_WIDTH = 1080;//Gdx.graphics.getWidth();
    public static final int APP_HEIGHT = 720;//Gdx.graphics.getHeight();

    public PAnimation animation;

    public DungeonScreen(final PMD game) {
        this.game = game;
        batch = game.batch;
        shapeRenderer = new ShapeRenderer();

        this.loadManager();
        controller = new Controller(this);
        this.loadMenus();
        stage = new Stage();
        currentMenu = menuList.get("attackMenu");
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

    public void switchMenus(String menu)
    {
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

        for (Button b : currentMenu.updateButtonList) {
            b.update();
        }


        batch.begin();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(1,1,0,1);
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        for (Tile t : controller.renderableArea) {
          /*  if(controller.isTPressed) {
                shapeRenderer.rect(t.x, t.y, Constants.TILE_SIZE, Constants.TILE_SIZE);
            }
            t.render(batch);*/
        }
        shapeRenderer.end();

        if (isFullView) {
            for (int i = 0; i < controller.getTileBoard().length; i++)
                for (int j = 0; j < controller.getTileBoard()[0].length; j++)
                    controller.getTileBoard()[i][j].render(batch);
        }


        for (Renderable r : controller.getRenderList()) {
            if (r instanceof Pokemon) {
                if (((Pokemon) r).isWithinArea(controller.renderableArea)) {
                    /*Tile t = ((Pokemon) r).getFacingTile();
                    if(t != null)
                        t.renderDebug(batch);*/
                    r.render(batch);
                }
            } else
                r.render(batch);
        }

        for (Projectile p : controller.projectiles) {
            p.render(batch);
        }

   //    batch.draw(animation.getCurrentSprite(), 100, 100);

        /* Not sure about this, might be good need to do more reserach
        new SpriteDrawable(animation.getCurrentSprite()).draw(batch, 100,100,22,22);
        */

        batch.end();
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
        camera.position.set(controller.getPokemonPlayer().x, controller.getPokemonPlayer().y, 0);
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
