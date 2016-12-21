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

        if(pokemon.equals(pokemon.currentTile) && pokemon.turnState == Turn.WAITING){
            if(pokemon.isLegalToMoveTo(pokemon.possibleNextTile)){

                if(controller.isKeyPressed(Key.s)){
                    pokemon.setBehavior(new MoveFastBehavior(pokemon), BaseBehavior.MOVE_BEHAVIOR);
                } else pokemon.setBehavior(new MoveSlowBehavior(pokemon), BaseBehavior.MOVE_BEHAVIOR);

                pokemon.setNextTile(pokemon.possibleNextTile);
                pokemon.possibleNextTile = null;

                pokemon.turnState = Turn.COMPLETE;
                pokemon.setActionState(Action.MOVING);
            }
        }

        pokemon.updateCurrentTile();

        if(pokemon.equals(pokemon.nextTile) && pokemon.possibleNextTile == null && pokemon.getActionState() == Action.MOVING) {
            pokemon.setActionState(Action.IDLE);
        }
    }

    @Override
    public boolean canExecute(){
        if(pokemon.getActionState() != Action.IDLE && pokemon.getActionState() != Action.MOVING) return false;
        return true;
    }


}
