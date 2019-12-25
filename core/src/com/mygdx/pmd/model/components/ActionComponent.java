package com.mygdx.pmd.model.components;

import com.badlogic.ashley.core.Component;
import com.mygdx.pmd.enums.*;

/**
 * Created by Cameron on 4/16/2017.
 */
public class ActionComponent implements Component {

    private Action actionState;

    public ActionComponent() {
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
