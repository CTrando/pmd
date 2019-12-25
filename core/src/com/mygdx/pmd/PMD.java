package com.mygdx.pmd;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.*;
import com.mygdx.pmd.enumerations.Key;
import com.mygdx.pmd.screens.*;
import com.mygdx.pmd.utils.AssetManager;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * Created by Cameron on 9/11/2016.
 */
public class PMD extends Game {
    public static final String TITLE = "Pokemon Mystery Dungeon";
    private final String ATLAS_DIRECTORY = "pokemonassets";
    private final String ATLAS_LIST = "atlas.txt";
    private final String SFX_DIRECTORY = "sfx";
    private final String SFX_LIST = "sfx.txt";
    public static HashMap<String, Sprite> sprites = new HashMap<String, Sprite>();
    public static HashMap<Integer, AtomicBoolean> keys;

    public static Screen dungeonScreen;

    public SpriteBatch batch;
    public ShapeRenderer shapeRenderer;

    @Override
    public void create() {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        keys = new HashMap<Integer, AtomicBoolean>();
        sprites = new HashMap<String, Sprite>();

        this.loadKeys();
        this.loadAssets();

        //introScreen = new IntroScreen(this);
        dungeonScreen = new DungeonScreen(this);

        this.switchScreen(dungeonScreen);
    }

    public void render() {
        super.render();
    }

    public void dispose() {
        batch.dispose();
    }

    private void loadKeys() {
        for (Key key : Key.values()) {
            keys.put(key.getValue(), new AtomicBoolean(false));
        }
    }

    private void loadAssets() {
        loadAtlases();
        loadSFX();

        AssetManager.getInstance().finishLoading();
        AssetManager.getInstance().loadSprites();
    }

    private void loadAtlases() {
        // must use Gdx.files.internal if want to use android as well should work on desktop though
        final FileHandle atlasFolder = Gdx.files.internal(ATLAS_LIST);

        System.out.println("load atlas");
        System.out.println(atlasFolder.name());
        for (final String atlasName : atlasFolder.readString().split("\\r?\\n")) {
            System.out.println(atlasName);
            AssetManager.getInstance().load(ATLAS_DIRECTORY + "/" + atlasName, TextureAtlas.class);
        }
    }

    private void loadSFX() {
        final FileHandle sfxFolder = Gdx.files.internal(SFX_LIST);

        for (final String sfxName : sfxFolder.readString().split("\\r?\\n")) {
            if (sfxName.endsWith(".wav")) {
                AssetManager.getInstance().load(SFX_DIRECTORY + "/" + sfxName, Sound.class);
            } else if (sfxName.endsWith(".ogg")) {
                AssetManager.getInstance().load(SFX_DIRECTORY + "/" + sfxName, Music.class);
            }
        }
    }

    //TODO add buffer screen system using stacks
    public void switchScreen(Screen screen) {
        this.setScreen(screen);
    }
}
