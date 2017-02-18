package com.mygdx.pmd.interfaces;

import com.mygdx.pmd.enumerations.Turn;

/**
 * Created by Cameron on 12/22/2016.
 */
public interface TurnBaseable {
    public void setTurnState(Turn turnState);
    public Turn getTurnState();
}
