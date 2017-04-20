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

    public MoveInstruction(Entity entity, Tile nextTile) {
        this.entity = entity;
        this.nextTile = nextTile;
        this.mc = (MoveComponent) this.entity.getComponent(MoveComponent.class);
        this.ac = (ActionComponent) this.entity.getComponent(ActionComponent.class);
        this.dc = (DirectionComponent) this.entity.getComponent(DirectionComponent.class);
        this.pc = (PositionComponent) this.entity.getComponent(PositionComponent.class);

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

        pc.removeFromCurrentTile();
        mc.setFacingTile(dc.getDirection());

        if(mc.isForcedMove()){
            mc.setForcedMove(false);
        }
    }

    @Override
    public void onFinish() {
        ac.setActionState(Action.IDLE);

        pc.setCurrentTile(nextTile);
        pc.getCurrentTile().playEvents(entity);
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }
}
