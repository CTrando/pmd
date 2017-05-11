package com.mygdx.pmd.model.instructions;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.pmd.PMD;
import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.model.Entity.*;
import com.mygdx.pmd.model.components.*;
import com.mygdx.pmd.utils.PAnimation;

/**
 * Created by Cameron on 4/24/2017.
 */
public class AnimateInstruction implements Instruction {
    private Entity entity;
    private boolean isFinished;
    private AnimationComponent anc;
    private ActionComponent ac;

    private String animationKey;
    private String nextAnimation;

    public AnimateInstruction(Entity entity, String animationKey){
        this.entity = entity;
        this.anc = entity.getComponent(AnimationComponent.class);
        this.ac = entity.getComponent(ActionComponent.class);
        this.animationKey = animationKey;
        this.nextAnimation = "";
    }

    public AnimateInstruction(Entity entity, String animationKey, String nextAnimation){
        this(entity, animationKey);
        this.nextAnimation = nextAnimation;
    }

    @Override
    public void onInit() {
        this.anc.setCurrentAnimation(anc.getAnimation(animationKey));
        this.anc.getCurrentAnimation().clear();
    }

    @Override
    public void onFinish() {
        if(anc.getCurrentAnimation().isLooping()) {
            this.anc.getCurrentAnimation().clear();
        }
        if(nextAnimation.length() != 0) {
            this.anc.setCurrentAnimation(anc.getAnimation(nextAnimation));
        }
    }

    @Override
    public void execute() {
        if(anc.isAnimationFinished()){
            this.isFinished = true;
        }
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }
}
