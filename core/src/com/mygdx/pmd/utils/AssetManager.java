package com.mygdx.pmd.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.mygdx.pmd.utils.yaml.AnimationInfo;
import com.mygdx.pmd.utils.yaml.AssetInfo;
import org.yaml.snakeyaml.Yaml;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AssetManager extends com.badlogic.gdx.assets.AssetManager {
    private static final String ANIMATION_DIR = "animation";

    private Map<String, Sprite> fSprites = new HashMap<String, Sprite>();
    private Map<String, Map<String, Animation<Sprite>>> fAnimations = new HashMap<>();
    private static AssetManager fAsset = new AssetManager();

    public void loadSprites() {
        System.out.println("Loading sprites");
        Array<TextureAtlas> spriteAtlases = new Array<TextureAtlas>();
        AssetManager.getInstance().getAll(TextureAtlas.class, spriteAtlases);

        for (TextureAtlas atlas : spriteAtlases) {
            loadImages(atlas);
        }
    }

    public void loadAnimations() {
        System.out.println("Loading animations");
        Yaml yaml = new Yaml();
        final FileHandle animFolder = Gdx.files.internal(ANIMATION_DIR);
        for (FileHandle file : animFolder.list()) {
            AssetInfo asset = yaml.loadAs(file.read(), AssetInfo.class);

            String assetName = asset.getName();
            fAnimations.putIfAbsent(assetName, new HashMap<>());

            Map<String, AnimationInfo> animations = asset.getAnimations();

            for (Map.Entry<String, AnimationInfo> entry : animations.entrySet()) {
                loadAnimation(assetName, entry.getKey(), entry.getValue());
            }
        }
    }

    private void loadAnimation(String assetName, String animationName, AnimationInfo info) {
        Array<Sprite> sprites = new Array<>(info
                .getSprites()
                .stream().map(this::getSprite)
                .toArray(Sprite[]::new));
        if (Arrays.stream(sprites.toArray()).anyMatch(Objects::isNull)) {
            throw new IllegalStateException(
                    String.format("While loading asset %s, animation %s, encountered invalid sprite",
                            assetName, animationName));
        }

        fAnimations.get(assetName).put(animationName, new Animation<>(info.getDuration(), sprites));
    }

    private void loadImages(TextureAtlas textureAtlas) {
        for (TextureAtlas.AtlasRegion textureRegion : textureAtlas.getRegions()) {
            System.out.println(String.format("Loaded %s", textureRegion.name));
            Sprite sprite = textureAtlas.createSprite(textureRegion.name);
            fSprites.put(textureRegion.name, sprite);
        }
    }

    public Animation<Sprite> getAnimation(String name, String animationName) {
        return fAnimations.getOrDefault(name, new HashMap<>()).getOrDefault(animationName, null);
    }

    public Sprite getSprite(String name) {
        return fSprites.get(name);
    }

    public static AssetManager getInstance() {
        return fAsset;
    }
}
