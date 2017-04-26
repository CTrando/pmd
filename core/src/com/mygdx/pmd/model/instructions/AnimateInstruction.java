package com.mygdx.pmd.model.instructions;

import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.model.Entity.*;
import com.mygdx.pmd.model.components.*;

/**
 * Created by Cameron on 4/24/2017.
 */
public class AnimateInstruction implements Instruction {

    //TODO come back to this when I have an animationKey component
    private Entity entity;
    private boolean isFinished;
    private AnimationComponent anc;
    private ActionComponent ac;

    private String animationKey;

    public AnimateInstruction(Entity entity, String animationKey){
        this.entity = entity;
        this.anc = entity.getComponent(AnimationComponent.class);
        this.ac = entity.getComponent(ActionComponent.class);
        this.animationKey = animationKey;
    }

    @Override
    public void onInit() {
        this.anc.setCurrentAnimation(anc.getAnimation(animationKey));
    }

    @Override
    public void onFinish() {
        this.ac.setActionState(Action.IDLE);
        this.anc.getCurrentAnimation().clear();
        this.anc.setCurrentAnimation(anc.getAnimation("upidle"));
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
