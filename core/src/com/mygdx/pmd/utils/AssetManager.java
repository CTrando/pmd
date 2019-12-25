package com.mygdx.pmd.utils;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;
import java.util.Map;

public class AssetManager extends com.badlogic.gdx.assets.AssetManager {
    private Map<String, Sprite> fSprites = new HashMap<String, Sprite>();
    private static AssetManager fAsset = new AssetManager();

    public void loadSprites() {
        System.out.println("Loading sprites");
        Array<TextureAtlas> spriteAtlases = new Array<TextureAtlas>();
        AssetManager.getInstance().getAll(TextureAtlas.class, spriteAtlases);

        for (TextureAtlas atlas : spriteAtlases) {
            loadImages(atlas);
        }
    }

    private void loadImages(TextureAtlas textureAtlas) {
        for (TextureAtlas.AtlasRegion textureRegion : textureAtlas.getRegions()) {
            System.out.println(String.format("Loaded %s", textureRegion.name));
            Sprite sprite = textureAtlas.createSprite(textureRegion.name);
            fSprites.put(textureRegion.name, sprite);
        }
    }

    public Sprite getSprite(String name) {
        return fSprites.get(name);
    }

    public static AssetManager getInstance() {
        return fAsset;
    }
}
