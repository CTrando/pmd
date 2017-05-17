package com.mygdx.pmd.model.components;

import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.model.Entity.*;

/**
 * Created by Cameron on 5/16/2017.
 */
public class PlayerMoveComponent extends MoveComponent {
    private TurnComponent tc;
    public PlayerMoveComponent(Entity entity) {
        super(entity);
        tc = entity.getComponent(TurnComponent.class);
    }

    @Override
    public void randomizeLocation(){
        super.randomizeLocation();
        tc.setTurnState(Turn.WAITING);
    }
}
