package com.mygdx.pmd.model.Behavior;

import com.badlogic.ashley.core.Component;
import com.mygdx.pmd.enumerations.*;

/**
 * Created by Cameron on 2/17/2017.
 */
public class TurnComponent implements Component {

    private Turn turnState;

    public TurnComponent(){
        setTurnState(Turn.COMPLETE);
    }


    //methods for turn state
    public boolean isTurnComplete() {
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
