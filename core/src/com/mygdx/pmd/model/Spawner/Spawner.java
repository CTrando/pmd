package com.mygdx.pmd.model.Spawner;

import com.mygdx.pmd.interfaces.TurnBaseable;
import com.mygdx.pmd.enumerations.Action;
import com.mygdx.pmd.enumerations.Turn;
import com.mygdx.pmd.model.Entity.*;
import com.mygdx.pmd.model.Floor.*;
import com.mygdx.pmd.model.logic.*;

/**
 * Created by Cameron on 12/21/2016.
 */
public abstract class Spawner extends Entity implements TurnBaseable {
    Logic logic;

    protected Spawner(Floor floor) {
        super(floor, 0, 0);
        this.setTurnState(Turn.COMPLETE);
        setActionState(Action.IDLE);
    }

    @Override
    public void update() {
        super.update();
        runLogic();
    }

    @Override
    public void runLogic() {
        logic.execute();
    }

    @Override
    public void dispose() {

    }

    @Override
    public void setTurnState(Turn turnState) {
        this.turnState = turnState;
    }

    @Override
    public Turn getTurnState() {
        return turnState;
    }

}
