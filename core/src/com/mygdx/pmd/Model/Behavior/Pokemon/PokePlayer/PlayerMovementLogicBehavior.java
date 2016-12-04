package com.mygdx.pmd.model.Behavior.Pokemon.PokePlayer;

import com.mygdx.pmd.enumerations.Action;
import com.mygdx.pmd.enumerations.Key;
import com.mygdx.pmd.enumerations.Turn;
import com.mygdx.pmd.model.Behavior.BaseBehavior;
import com.mygdx.pmd.model.Behavior.Entity.MoveFastBehavior;
import com.mygdx.pmd.model.Behavior.Entity.MoveSlowBehavior;
import com.mygdx.pmd.model.Behavior.Pokemon.PokemonBehavior;
import com.mygdx.pmd.model.Entity.Pokemon.Pokemon;
import com.mygdx.pmd.model.Entity.Entity;

/**
 * Created by Cameron on 11/8/2016.
 */
public class PlayerMovementLogicBehavior extends PokemonBehavior {

    public PlayerMovementLogicBehavior(Pokemon pokemon){
        super(pokemon);
    }

    @Override
    public void execute() {
        if(pokemon.getActionState() != Action.IDLE && pokemon.getActionState() != Action.MOVING) return;

        if(pokemon.equals(pokemon.currentTile) && pokemon.turnState == Turn.WAITING){
            if(pokemon.isLegalToMoveTo(pokemon.nextTile)){
                if(pokemon.nextTile.hasEntity()) {
                    for(Entity entity: pokemon.nextTile.getEntityList()) {
                        entity.nextTile = pokemon.currentTile;
                    }
                }

                if(controller.isKeyPressed(Key.s)){
                    pokemon.setBehavior(new MoveFastBehavior(pokemon), BaseBehavior.MOVE_BEHAVIOR);
                } else pokemon.setBehavior(new MoveSlowBehavior(pokemon), BaseBehavior.MOVE_BEHAVIOR);

                pokemon.setCurrentTile(pokemon.nextTile);
                pokemon.nextTile = null;

                pokemon.turnState = Turn.COMPLETE;
                pokemon.setActionState(Action.MOVING);
            }
        }

        if(pokemon.equals(pokemon.currentTile) && pokemon.nextTile == null && pokemon.getActionState() == Action.MOVING) {
           /* if(pokemon.getActionState() == Action.MOVING)
                pokemon.currentTile.playEvents();*/
            pokemon.setActionState(Action.IDLE);
        }
    }


}
