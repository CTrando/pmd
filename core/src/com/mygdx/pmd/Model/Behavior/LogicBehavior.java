package com.mygdx.pmd.Model.Behavior;

import com.mygdx.pmd.Enumerations.Action;
import com.mygdx.pmd.Enumerations.Turn;
import com.mygdx.pmd.Model.Pokemon.Pokemon;

/**
 * Created by Cameron on 11/8/2016.
 */
public class LogicBehavior extends Behavior{

    public LogicBehavior(Pokemon pokemon){
        super(pokemon);
    }

    @Override
    public void execute() {
        if(pokemon.equals(pokemon.currentTile) && pokemon.nextTile == null)
            pokemon.actionState = Action.IDLE;

        if(pokemon.equals(pokemon.currentTile) && pokemon.turnState == Turn.WAITING){
            if(pokemon.isLegalToMoveTo(pokemon.nextTile)){
                pokemon.setCurrentTile(pokemon.nextTile);
                pokemon.nextTile = null;

                pokemon.turnState = Turn.COMPLETE;
                pokemon.actionState = Action.MOVING;
            }
        }
    }


}
