package com.mygdx.pmd.model.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.pmd.model.Entity.*;
import com.mygdx.pmd.utils.PAnimation;

import java.util.HashMap;

/**
 * Created by Cameron on 4/24/2017.
 * This will store the animations of each entity
 */
public class AnimationComponent implements Component {

    private HashMap<String, PAnimation> animationMap;
    private PAnimation prevAnimation;
    private PAnimation currentAnimation;

    public AnimationComponent(){
        this.animationMap = new HashMap<String, PAnimation>();
    }

    public boolean isAnimationFinished(){
        return currentAnimation.isFinished();
    }

    public PAnimation getCurrentAnimation() {
        return currentAnimation;
    }

    public PAnimation getPrevAnimation() {
        return prevAnimation;
    }

    public void setCurrentAnimation(String animationKey){
        this.prevAnimation = this.currentAnimation;
        this.currentAnimation = getAnimation(animationKey);
    }

    public void setCurrentAnimation(PAnimation currentAnimation) {
        this.prevAnimation = this.currentAnimation;
        this.currentAnimation = currentAnimation;
    }

    public void putAnimation(String string, PAnimation animation){
        animationMap.put(string, animation);
    }

    public PAnimation getAnimation(String string){
        return animationMap.get(string);
    }

    public Sprite getCurrentSprite(){
        if(currentAnimation == null) return null;
        return currentAnimation.getCurrentSprite();
    }

    public void clearCurrentAnimation() {
        currentAnimation.clear();
    }
}
