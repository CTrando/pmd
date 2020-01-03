package com.mygdx.pmd.model.components;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.pmd.utils.AssetManager;

/**
 * Created by Cameron on 4/24/2017.
 * This will store the animations of each entity
 */
public class AnimationComponent extends Component {
    private String fAnimationName;
    private boolean fLoop;
    private float fStateTime;
    private Animation<Sprite> fAnimation;

    public AnimationComponent(String assetName, String animation, boolean loop) {
        this(assetName, animation, AssetManager.getInstance(), loop);
    }

    public AnimationComponent(String assetName, String animation) {
        this(assetName, animation, AssetManager.getInstance(), true);
    }

    public AnimationComponent(String asset, String animation, AssetManager assets, boolean loop) {
        fAnimationName = animation;
        fAnimation = assets.getAnimation(asset, animation);
        fStateTime = 0;
        fLoop = loop;
    }

    public void addStateTime(float dt) {
        fStateTime += dt;
    }

    public float getStateTime() {
        return fStateTime;
    }

    public Sprite getKeyFrame() {
        return fAnimation.getKeyFrame(fStateTime, fLoop);
    }

    public boolean isFinished() {
        if(fLoop) {
            return false;
        }
        return fAnimation.isAnimationFinished(fStateTime);
    }

    public String getAnimationName() {
        return fAnimationName;
    }
}
