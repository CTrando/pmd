package com.mygdx.pmd.model.Spawner;

import com.mygdx.pmd.enumerations.Action;
import com.mygdx.pmd.enumerations.Turn;
import com.mygdx.pmd.model.Entity.*;
import com.mygdx.pmd.model.Floor.*;
import com.mygdx.pmd.model.components.*;
import com.mygdx.pmd.model.logic.*;

/**
 * Created by Cameron on 12/21/2016.
 */
public abstract class Spawner extends Entity  {
    Logic logic;

    protected Spawner(Floor floor) {
        super(floor, 0, 0);
        tc = new TurnComponent(this);
        ac = new ActionComponent(this);

        tc.setTurnState(Turn.COMPLETE);
        ac.setActionState(Action.IDLE);
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
}
