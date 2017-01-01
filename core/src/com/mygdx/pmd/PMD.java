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
import com.mygdx.pmd.screens.EndScreen;
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

    public static PScreen dungeonScreen;
    public static PScreen endScreen;

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

        dungeonScreen = new DungeonScreen(this);
        endScreen = new EndScreen(this);

        this.setScreen(dungeonScreen);
    }

    public void render() {
        super.render();
    }

    public void dispose() {
        //TODO fix this
      //  batch.dispose();
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

    //TODO add buffer screen system
    public void switchScreen(PScreen screen){
        this.setScreen(screen);
    }
}
