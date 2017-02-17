package com.mygdx.pmd.model.Behavior;

import com.badlogic.ashley.core.Component;
import com.mygdx.pmd.enumerations.*;

/**
 * Created by Cameron on 2/16/2017.
 */
public class ActionComponent implements Component {

    private Action actionState;
    private Action previousState;

    public ActionComponent(){
        setActionState(Action.IDLE);
    }

    public void setActionState(Action actionState) {
        this.previousState = this.actionState;
        this.actionState = actionState;
    }

    public Action getActionState() {
        return actionState;
    }
}
