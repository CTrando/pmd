package com.mygdx.pmd.Model.Behavior.Pokemon.PokeMob;

import com.mygdx.pmd.Enumerations.Action;
import com.mygdx.pmd.Enumerations.Turn;
import com.mygdx.pmd.Model.Behavior.Pokemon.PokemonBehavior;
import com.mygdx.pmd.Model.Entity.Pokemon.Pokemon;
import com.mygdx.pmd.Model.Entity.Projectile.Projectile;

/**
 * Created by Cameron on 11/14/2016.
 */
public class MobAttackLogicBehavior extends PokemonBehavior {


    public MobAttackLogicBehavior(Pokemon pokemon) {
        super(pokemon);
    }

    @Override
    public void execute() {
        if (pokemon.actionState != Action.IDLE && pokemon.actionState != Action.ATTACKING) return;

        if (pokemon.actionState == Action.ATTACKING) {
            if (pokemon.projectile == null) {
                pokemon.turnState = Turn.COMPLETE;
                pokemon.actionState = Action.IDLE;
            }
        } else if (pokemon.actionState == Action.IDLE) {
            if (pokemon.turnState == Turn.WAITING) {
                if (pokemon.facingTile == null) return;

                if (pokemon.facingTile.hasEntity() && pokemon.facingTile != pokemon.currentTile) {
                    pokemon.actionState = Action.ATTACKING;
                    pokemon.turnState = Turn.PENDING;
                    pokemon.projectile = new Projectile(pokemon.facingTile, pokemon);
                }
            }
        }
    }
}
