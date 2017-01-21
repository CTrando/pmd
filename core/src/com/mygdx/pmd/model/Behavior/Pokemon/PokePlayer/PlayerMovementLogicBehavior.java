package com.mygdx.pmd.model.Behavior.Pokemon.PokePlayer;

import com.mygdx.pmd.enumerations.Action;
import com.mygdx.pmd.enumerations.Key;
import com.mygdx.pmd.enumerations.Turn;
import com.mygdx.pmd.model.Behavior.BaseBehavior;
import com.mygdx.pmd.model.Behavior.Entity.MoveFastBehavior;
import com.mygdx.pmd.model.Behavior.Entity.MoveSlowBehavior;
import com.mygdx.pmd.model.Behavior.Pokemon.PokemonBehavior;
import com.mygdx.pmd.model.Entity.Pokemon.Pokemon;

/**
 * Created by Cameron on 11/8/2016.
 */
public class PlayerMovementLogicBehavior extends PokemonBehavior {

    public PlayerMovementLogicBehavior(Pokemon pokemon){
        super(pokemon);
    }

    @Override
    public void execute() {
        if(!this.canExecute()) return;

        if(pMob.equals(pMob.currentTile) && pMob.turnState == Turn.WAITING){
            if(pMob.isLegalToMoveTo(pMob.possibleNextTile)){
                if(controller.isKeyPressed(Key.s)){
                    pMob.setBehavior(new MoveFastBehavior(pMob), BaseBehavior.MOVE_BEHAVIOR);
                } else pMob.setBehavior(new MoveSlowBehavior(pMob), BaseBehavior.MOVE_BEHAVIOR);

                pMob.setNextTile(pMob.possibleNextTile);
                pMob.possibleNextTile = null;

                pMob.turnState = Turn.COMPLETE;
                pMob.setActionState(Action.MOVING);
            } else pMob.possibleNextTile = null;
        }

        pMob.updateCurrentTile();

        if(pMob.equals(pMob.nextTile) && pMob.possibleNextTile == null && pMob.getActionState() == Action.MOVING) {
            pMob.setActionState(Action.IDLE);
        }
    }

    @Override
    public boolean canExecute(){
        if(pMob.getActionState() != Action.IDLE && pMob.getActionState() != Action.MOVING) return false;
        return true;
    }


}
