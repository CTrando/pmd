package com.mygdx.pmd.Model.Behavior;

import com.mygdx.pmd.Enumerations.Action;
import com.mygdx.pmd.Enumerations.Turn;
import com.mygdx.pmd.Model.Pokemon.Pokemon;

/**
 * Created by Cameron on 11/14/2016.
 */
public class MobAttackLogicBehavior extends Behavior {


    public MobAttackLogicBehavior(Pokemon pokemon) {
        super(pokemon);
    }

    @Override
    public void execute() {
        if (pokemon.actionState != Action.IDLE && pokemon.actionState != Action.ATTACKING) return;

        if(pokemon.actionState == Action.ATTACKING){

        }


        if (pokemon.turnState == Turn.WAITING) {
            if (pokemon.facingTile == null) return;

            if (pokemon.facingTile.hasAPokemon() && pokemon.facingTile != pokemon.currentTile) {
                pokemon.actionState = Action.ATTACKING;
                pokemon.turnState = Turn.PENDING;
            }
        }
    }
}
