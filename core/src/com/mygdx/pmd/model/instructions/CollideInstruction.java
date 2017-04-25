package com.mygdx.pmd.model.instructions;

import com.badlogic.gdx.audio.Sound;
import com.mygdx.pmd.PMD;
import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.model.Entity.*;
import com.mygdx.pmd.model.components.*;

/**
 * Created by Cameron on 4/1/2017.
 */
public class CollideInstruction implements Instruction{
    private Entity entity;
    private boolean isFinished;
    private ActionComponent ac;
    private PositionComponent pc;
    private AnimationComponent anc;

    public CollideInstruction(Entity entity){
        this.entity = entity;
        this.ac = this.entity.getComponent(ActionComponent.class);
        this.pc = this.entity.getComponent(PositionComponent.class);
        this.anc = this.entity.getComponent(AnimationComponent.class);
    }

    @Override
    public void onInit() {
        PMD.manager.get("sfx/wallhit.wav", Sound.class).play();
        ac.setActionState(Action.COLLISION);
        pc.removeFromCurrentTile();
    }

    @Override
    public void onFinish() {

    }

    @Override
    public void execute() {
        if(anc.isAnimationFinished()){
            isFinished = true;
        }
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }
}
