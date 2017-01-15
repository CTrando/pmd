package com.mygdx.pmd.model.Behavior.Pokemon.PokePlayer;

import com.mygdx.pmd.enumerations.Action;
import com.mygdx.pmd.enumerations.Aggression;
import com.mygdx.pmd.enumerations.Move;
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

        if (pokemon.getActionState() == Action.ATTACKING) {
            if (pokemon.projectile == null && pokemon.currentAnimation.isFinished()) {
                pokemon.turnState = Turn.COMPLETE;
                pokemon.setActionState(Action.IDLE);
            }
        }
    }

    @Override
    public boolean canExecute(){
        if (pokemon.getActionState() != Action.ATTACKING) return false;
        return true;
    }

}
