package com.mygdx.pmd.model.components;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.pmd.model.Entity.*;
import com.mygdx.pmd.utils.PAnimation;

import java.util.HashMap;

/**
 * Created by Cameron on 4/24/2017.
 * This will store the animations of each entity
 */
public class AnimationComponent implements Component {

    private Entity entity;
    private HashMap<String, PAnimation> animationMap;
    private PAnimation currentAnimation;

    public AnimationComponent(Entity entity){
        this.entity = entity;
        this.animationMap = new HashMap<String, PAnimation>();
    }

    public boolean isAnimationFinished(){
        return currentAnimation.isFinished();
    }

    public PAnimation getCurrentAnimation() {
        return currentAnimation;
    }

    public void setCurrentAnimation(PAnimation currentAnimation) {
        this.currentAnimation = currentAnimation;
    }

    public void putAnimation(String string, PAnimation animation){
        animationMap.put(string, animation);
    }

    public PAnimation getAnimation(String string){
        return animationMap.get(string);
    }

    public Sprite getCurrentSprite(){
        return currentAnimation.getCurrentSprite();
    }

}
