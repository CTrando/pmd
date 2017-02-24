package com.mygdx.pmd;

import com.badlogic.gdx.*;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.pmd.controller.Controller;
import com.mygdx.pmd.enumerations.Key;
import com.mygdx.pmd.screens.*;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * Created by Cameron on 9/11/2016.
 */
public class PMD extends Game {
    public static final String TITLE = "Pokemon Mystery Dungeon";
    public static HashMap<String, Sprite> sprites = new HashMap<String, Sprite>();
    public static HashMap<Integer, AtomicBoolean> keys;

    public static PScreen dungeonScreen;
    public static PScreen endScreen;
    public static PScreen introScreen;

    public SpriteBatch batch;
    public ShapeRenderer shapeRenderer;

    public static AssetManager manager;
    public Controller controller;

    @Override
    public void create() {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        keys = new HashMap<Integer, AtomicBoolean>();
        manager = new AssetManager();
        sprites = new HashMap<String, Sprite>();

        this.loadKeys();
        this.loadManager();
        this.loadSprites();


        introScreen = new IntroScreen(this);
        dungeonScreen = new DungeonScreen(this);
        endScreen = new EndScreen(this);

        this.switchScreen(introScreen);
    }

    public void render() {
        super.render();
    }

    public void dispose() {
        manager.dispose();
        batch.dispose();
    }

    private void loadKeys() {
        for (Key key : Key.values()) {
            keys.put(key.getValue(), new AtomicBoolean(false));
        }
    }

    private void loadManager() {

        manager.load("pokemonassets/TREEKO_WALKSHEET.atlas", TextureAtlas.class);
        manager.load("pokemonassets/TILE_SPRITES.atlas", TextureAtlas.class);
        manager.load("pokemonassets/SQUIRTLE_WALKSHEET.atlas", TextureAtlas.class);
        manager.load("pokemonassets/PROJECTILE_TEXTURE.atlas", TextureAtlas.class);
        manager.load("pokemonassets/ARROW_SPRITES.atlas", TextureAtlas.class);
        manager.load("pokemonassets/ATTACK_SPRITES.atlas", TextureAtlas.class);

        manager.load("sfx/background.ogg", Music.class);
        manager.load("sfx/wallhit.wav", Sound.class);
        manager.finishLoading();
    }

    private void loadSprites() {
        this.loadImages(manager.get("pokemonassets/TREEKO_WALKSHEET.atlas", TextureAtlas.class));
        this.loadImages(manager.get("pokemonassets/TILE_SPRITES.atlas", TextureAtlas.class));
        this.loadImages(manager.get("pokemonassets/SQUIRTLE_WALKSHEET.atlas", TextureAtlas.class));
        this.loadImages(manager.get("pokemonassets/PROJECTILE_TEXTURE.atlas", TextureAtlas.class));
        this.loadImages(manager.get("pokemonassets/ARROW_SPRITES.atlas", TextureAtlas.class));
        this.loadImages(manager.get("pokemonassets/ATTACK_SPRITES.atlas", TextureAtlas.class));
    }

    private void loadImages(TextureAtlas textureAtlas) {
        for (TextureAtlas.AtlasRegion textureRegion : textureAtlas.getRegions()) {
            Sprite sprite = textureAtlas.createSprite(textureRegion.name);
            sprites.put(textureRegion.name, sprite);
        }
    }

    //TODO add buffer screen system using stacks
    public void switchScreen(PScreen screen) {
        this.setScreen(screen);
    }

    public static boolean isKeyPressed(Key key) { //TODO perhaps add a buffer system for more control later
        return keys.get(key.getValue()).get();
    }

    /**
     * Time sensitive key hits - hits are not consecutive
     *
     * @param key the key entered
     * @return true if the key has been pressed after a certain period of time - returns false if the key is not pressed or if the key has been pressed too soon
     */
    public static boolean isKeyPressedTimeSensitive(Key key) {
        if (keys.get(key.getValue()).get()) {
            if (TimeUtils.timeSinceMillis(key.getLastTimeHit()) > key.getTimeLimit()) {
                key.setLastTimeHit(TimeUtils.millis());
                return true;
            }
        }
        return false;
    }
}
