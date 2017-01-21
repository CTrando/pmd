package com.mygdx.pmd.model.Behavior.Pokemon.PokePlayer;

import com.mygdx.pmd.enumerations.Action;
import com.mygdx.pmd.enumerations.Turn;
import com.mygdx.pmd.model.Behavior.Pokemon.PokemonBehavior;
import com.mygdx.pmd.model.Entity.Pokemon.Pokemon;

/**
 * Created by Cameron on 1/14/2017.
 */
public class PlayerAttackLogicBehavior extends PokemonBehavior{
    public PlayerAttackLogicBehavior(Pokemon pokemon) {
        super(pokemon);
    }

    @Override
    public void execute() {
        if(!this.canExecute()) return;

        if (pMob.getActionState() == Action.ATTACKING) {
            if (pMob.projectile == null && pMob.currentAnimation.isFinished()) {
                pMob.turnState = Turn.COMPLETE;
                pMob.setActionState(Action.IDLE);
            }
        }
    }

    @Override
    public boolean canExecute(){
        if (pMob.getActionState() != Action.ATTACKING) return false;
        return true;
    }

}
