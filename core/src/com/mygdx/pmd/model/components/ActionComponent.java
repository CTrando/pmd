package com.mygdx.pmd.model.components;

import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.model.Entity.*;

/**
 * Created by Cameron on 4/16/2017.
 */
public class ActionComponent implements Component {

    private Action actionState;
    private Entity entity;

    public ActionComponent(Entity entity) {
        this.entity = entity;
        actionState = Action.IDLE;
    }

    public boolean isMoving() {
        return actionState == Action.MOVING;
    }

    public boolean isIdle() {
        return actionState == Action.IDLE;
    }

    public boolean isAttacking() {
        return actionState == Action.ATTACKING;
    }

    public Action getActionState() {
        return actionState;
    }

    public void setActionState(Action actionState){
        this.actionState = actionState;
    }

}
