package com.mygdx.pmd.Model.Behavior;

import com.mygdx.pmd.Enumerations.Action;
import com.mygdx.pmd.Enumerations.Key;
import com.mygdx.pmd.Enumerations.Turn;
import com.mygdx.pmd.Model.Pokemon.Pokemon;
import com.mygdx.pmd.utils.Entity;

/**
 * Created by Cameron on 11/8/2016.
 */
public class PlayerMovementLogicBehavior extends PokemonBehavior{

    public PlayerMovementLogicBehavior(Pokemon pokemon){
        super(pokemon);
    }

    @Override
    public void execute() {
        if(pokemon.actionState != Action.IDLE && pokemon.actionState != Action.MOVING) return;

        if(pokemon.equals(pokemon.currentTile) && pokemon.turnState == Turn.WAITING){
            if(pokemon.isLegalToMoveTo(pokemon.nextTile)){
                if(pokemon.nextTile.hasEntity()) {
                    for(Entity entity: pokemon.nextTile.getEntityList()) {
                        entity.nextTile = pokemon.currentTile;
                    }
                }

                if(controller.isKeyPressed(Key.s)){
                    pokemon.setBehavior(new MoveFastBehavior(pokemon), Behavior.MOVE_BEHAVIOR);
                } else pokemon.setBehavior(new MoveSlowBehavior(pokemon), Behavior.MOVE_BEHAVIOR);

                pokemon.setCurrentTile(pokemon.nextTile);
                pokemon.nextTile = null;

                pokemon.turnState = Turn.COMPLETE;
                pokemon.actionState = Action.MOVING;
            }
        }

        if(pokemon.equals(pokemon.currentTile) && pokemon.nextTile == null) {
            if(pokemon.actionState == Action.MOVING)
                pokemon.currentTile.playEvents();
            pokemon.actionState = Action.IDLE;
        }
    }


}
