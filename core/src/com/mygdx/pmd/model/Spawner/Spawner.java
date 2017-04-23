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
    public TurnComponent tc;
    public ActionComponent ac;
    public PositionComponent pc;

    protected Spawner(Floor floor) {
        super(floor, 0, 0);
        this.tc = new TurnComponent(this);
        this.ac = new ActionComponent(this);
        this.pc = getComponent(PositionComponent.class);

        components.put(TurnComponent.class, tc);
        components.put(ActionComponent.class, ac);

        this.tc.setTurnState(Turn.COMPLETE);
        this.ac.setActionState(Action.IDLE);
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
