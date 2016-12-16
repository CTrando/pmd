package com.mygdx.pmd;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.pmd.controller.Controller;
import com.mygdx.pmd.enumerations.Key;
import com.mygdx.pmd.screens.DungeonScreen;
import com.mygdx.pmd.screens.PScreen;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * Created by Cameron on 9/11/2016.
 */
public class PMD extends Game {
    public static final int WIDTH = 1080;
    public static final int HEIGHT = 720;

    public static final String TITLE = "Pokemon Mystery Dungeon";
    public static HashMap<String, Sprite> sprites = new HashMap<String, Sprite>();
    public static HashMap<Integer, AtomicBoolean> keys;

    public SpriteBatch batch;
    public ShapeRenderer shapeRenderer;

    public AssetManager manager;
    public Controller controller;

    public PScreen currentScreen;

    @Override
    public void create() {
        sprites = new HashMap<String, Sprite>();
        keys = new HashMap<Integer, AtomicBoolean>();
        manager = new AssetManager();

        this.loadKeys();
        this.loadManager();
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        currentScreen = new DungeonScreen(this);
        this.setScreen(currentScreen);
    }

    public void render() {
        super.render();
    }

    public void dispose() {
        batch.dispose();
    }

    public void loadImages(TextureAtlas textureAtlas) {
        for (TextureAtlas.AtlasRegion textureRegion : textureAtlas.getRegions()) {
            Sprite sprite = textureAtlas.createSprite(textureRegion.name);
            sprites.put(textureRegion.name, sprite);
        }
    }

    public void loadManager() {

        manager.load("pokemonassets/TREEKO_WALKSHEET.atlas", TextureAtlas.class);
        manager.load("pokemonassets/TILE_SPRITES.atlas", TextureAtlas.class);
        manager.load("pokemonassets/SQUIRTLE_WALKSHEET.atlas", TextureAtlas.class);
        manager.load("pokemonassets/PROJECTILE_TEXTURE.atlas", TextureAtlas.class);
        manager.load("sfx/background.ogg", Music.class);
        manager.load("sfx/wallhit.wav", Sound.class);
        manager.finishLoading();

        this.loadImages(manager.get("pokemonassets/TREEKO_WALKSHEET.atlas", TextureAtlas.class));
        this.loadImages(manager.get("pokemonassets/TILE_SPRITES.atlas", TextureAtlas.class));
        this.loadImages(manager.get("pokemonassets/SQUIRTLE_WALKSHEET.atlas", TextureAtlas.class));
        this.loadImages(manager.get("pokemonassets/PROJECTILE_TEXTURE.atlas", TextureAtlas.class));
    }


    public void loadKeys() {
        for (Key key : Key.values()) {
            keys.put(key.getValue(), new AtomicBoolean(false));
        }
    }
}
