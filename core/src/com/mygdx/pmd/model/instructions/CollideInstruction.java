package com.mygdx.pmd.model.instructions;

import com.badlogic.gdx.audio.Sound;
import com.mygdx.pmd.PMD;
import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.model.Entity.*;

/**
 * Created by Cameron on 4/1/2017.
 */
public class CollideInstruction implements Instruction{
    private DynamicEntity dEntity;
    private boolean isFinished;

    public CollideInstruction(DynamicEntity dEntity){
        this.dEntity = dEntity;
    }

    @Override
    public void onInit() {
        PMD.manager.get("sfx/wallhit.wav", Sound.class).play();
        dEntity.setActionState(Action.COLLISION);
        dEntity.removeFromTile();
    }

    @Override
    public void onFinish() {

    }

    @Override
    public void execute() {
        if(dEntity.currentAnimation.isFinished()){
            isFinished = true;
        }
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }
}
