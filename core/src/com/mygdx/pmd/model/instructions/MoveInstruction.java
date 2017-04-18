package com.mygdx.pmd.model.instructions;

import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.model.Entity.*;
import com.mygdx.pmd.model.Tile.*;
import com.mygdx.pmd.model.components.*;

/**
 * Created by Cameron on 2/21/2017.
 */
public class MoveInstruction implements Instruction {
    private DynamicEntity dEntity;
    private Tile nextTile;
    private boolean isFinished;

    private MoveComponent mc;
    private ActionComponent ac;
    private DirectionComponent dc;
    private PositionComponent pc;

    public MoveInstruction(DynamicEntity dEntity, Tile nextTile) {
        this.dEntity = dEntity;
        this.nextTile = nextTile;
        this.mc = dEntity.mc;
        this.ac = dEntity.ac;
        this.dc = dEntity.dc;
        this.pc = dEntity.pc;
    }

    @Override
    public void execute() {
        if (!dEntity.equals(nextTile)) {
            mc.moveToTile(nextTile, mc.getSpeed());
        }

        if (dEntity.equals(nextTile)) {
            isFinished = true;
        }
    }

    @Override
    public void onInit() {
        ac.setActionState(Action.MOVING);

        pc.removeFromCurrentTile();
        mc.addToTile(nextTile);
        mc.setFacingTile(dc.getDirection());
    }

    @Override
    public void onFinish() {
        ac.setActionState(Action.IDLE);

        pc.setCurrentTile(nextTile);
        pc.getCurrentTile().playEvents(dEntity);
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }
}
