package com.mygdx.pmd.model.components;

import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.model.Entity.*;

/**
 * Created by Cameron on 4/16/2017.
 */
public class TurnComponent implements Component {
    private Turn turnState;
    private Entity entity;

    public TurnComponent(Entity entity){
        this.entity = entity;
        this.turnState = Turn.COMPLETE;
    }

    public boolean isTurnComplete(){
        return turnState == Turn.COMPLETE;
    }

    public boolean isTurnWaiting() {
        return turnState == Turn.WAITING;
    }

    public Turn getTurnState() {
        return turnState;
    }

    public void setTurnState(Turn turnState) {
        this.turnState = turnState;
    }
}
