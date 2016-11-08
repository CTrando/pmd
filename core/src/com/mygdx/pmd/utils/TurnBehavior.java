package com.mygdx.pmd.utils;

import com.mygdx.pmd.Enumerations.Turn;

/**
 * Created by Cameron on 11/7/2016.
 */
public class TurnBehavior {

    private Turn turnState = Turn.COMPLETE;
    public Entity entity;

    public TurnBehavior(Entity entity){
        this.entity = entity;
    }

    public boolean isTurnWaiting(){
        return turnState == Turn.WAITING;
    }

    public boolean isTurnComplete(){
        return turnState == Turn.COMPLETE;
    }

    public Turn getTurnState(){
        return turnState;
    }

    public void setTurnState(Turn turn){
        turnState = turn;
    }



}
