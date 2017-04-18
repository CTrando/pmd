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
    private DynamicEntity dEntity;
    private boolean isFinished;
    private ActionComponent ac;
    private PositionComponent pc;

    public CollideInstruction(DynamicEntity dEntity){
        this.dEntity = dEntity;
        this.ac = dEntity.ac;
        this.pc = dEntity.pc;
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
        if(dEntity.currentAnimation.isFinished()){
            isFinished = true;
        }
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }
}
