package com.mygdx.pmd.model.Behavior.Pokemon.PokeMob;

import com.mygdx.pmd.enumerations.Action;
import com.mygdx.pmd.enumerations.Aggression;
import com.mygdx.pmd.enumerations.Move;
import com.mygdx.pmd.enumerations.Turn;
import com.mygdx.pmd.model.Behavior.Pokemon.PokemonBehavior;
import com.mygdx.pmd.model.Entity.Pokemon.Pokemon;

/**
 * Created by Cameron on 11/14/2016.
 */
public class MobAttackLogicBehavior extends PokemonBehavior {
    public boolean hasNotAttacked = true;

    public MobAttackLogicBehavior(Pokemon pokemon) {
        super(pokemon);
    }

    @Override
    public void execute() {
        if (!this.canExecute()) return;

        if (pMob.facingTile == null) return;
        //this is temporary

        if (pMob.projectile == null && hasNotAttacked) {
            pMob.attack(Move.SCRATCH);
            hasNotAttacked = false;
        }
        else {
            if (pMob.projectile == null && pMob.currentAnimation.isFinished()) {
                pMob.turnState = Turn.COMPLETE;
                pMob.setActionState(Action.IDLE);
            }
        }
    }

    //call this first
    public void resetAttack(){
        hasNotAttacked = true;
    }

    @Override
    public boolean canExecute() {
        if (pMob.getActionState() != Action.IDLE && pMob.getActionState() != Action.ATTACKING) return false;
        if (pMob.aggression != Aggression.aggressive) return false;
        return true;
    }
}
