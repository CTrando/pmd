package com.mygdx.pmd.model.Behavior.Pokemon.PokeMob;

import com.mygdx.pmd.enumerations.Action;
import com.mygdx.pmd.enumerations.Move;
import com.mygdx.pmd.enumerations.Turn;
import com.mygdx.pmd.model.Behavior.Pokemon.PokemonBehavior;
import com.mygdx.pmd.model.Entity.Pokemon.Pokemon;

/**
 * Created by Cameron on 11/14/2016.
 */
public class MobAttackLogicBehavior extends PokemonBehavior {


    public MobAttackLogicBehavior(Pokemon pokemon) {
        super(pokemon);
    }

    @Override
    public void execute() {
        if (pokemon.getActionState() != Action.IDLE && pokemon.getActionState() != Action.ATTACKING) return;

        if (pokemon.getActionState() == Action.ATTACKING) {
            if (pokemon.projectile == null && pokemon.currentAnimation.isFinished()) {
                pokemon.turnState = Turn.COMPLETE;
                pokemon.setActionState(Action.IDLE);
            }
        } else if (pokemon.getActionState() == Action.IDLE) {
            if (pokemon.turnState == Turn.WAITING) {
                if (pokemon.facingTile == null) return;

                if ((pokemon.isVisible() || pokemon.facingTile.hasEntity()) && pokemon.facingTile != pokemon.currentTile) {
                    pokemon.setActionState(Action.ATTACKING);
                    pokemon.turnState = Turn.PENDING;

                    pokemon.attack(Move.SCRATCH);
                }
            }
        }
    }
}
