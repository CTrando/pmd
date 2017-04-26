package com.mygdx.pmd.model.instructions;

import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.model.Entity.*;
import com.mygdx.pmd.model.Tile.*;
import com.mygdx.pmd.model.components.*;

/**
 * Created by Cameron on 2/21/2017.
 */
public class MoveInstruction implements Instruction {
    private Entity entity;
    private Tile nextTile;
    private boolean isFinished;

    private MoveComponent mc;
    private ActionComponent ac;
    private DirectionComponent dc;
    private PositionComponent pc;
    private AnimationComponent anc;

    public MoveInstruction(Entity entity, Tile nextTile) {
        this.entity = entity;
        this.nextTile = nextTile;
        this.mc = this.entity.getComponent(MoveComponent.class);
        this.ac = this.entity.getComponent(ActionComponent.class);
        this.dc = this.entity.getComponent(DirectionComponent.class);
        this.pc = this.entity.getComponent(PositionComponent.class);
        this.anc = this.entity.getComponent(AnimationComponent.class);

        mc.addToTile(nextTile);
    }

    @Override
    public void execute() {
        if (!entity.equals(nextTile)) {
            mc.moveToTile(nextTile, mc.getSpeed());
        }

        if (entity.equals(nextTile)) {
            isFinished = true;
        }
    }

    @Override
    public void onInit() {
        ac.setActionState(Action.MOVING);
        mc.setFacingTile(dc.getDirection());
        pc.removeFromCurrentTile();

        if(mc.isForcedMove()){
            mc.setForcedMove(false);
        }
    }

    @Override
    public void onFinish() {
        ac.setActionState(Action.IDLE);
        anc.setCurrentAnimation(dc.getDirection().toString()+"idle");

        pc.setCurrentTile(nextTile);
        pc.getCurrentTile().playEvents(entity);
        mc.setFacingTile(dc.getDirection());
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }
}
