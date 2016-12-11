package com.mygdx.pmd.model.Behavior.Pokemon.PokeMob;

import com.badlogic.gdx.utils.Array;
import com.mygdx.pmd.enumerations.Action;
import com.mygdx.pmd.enumerations.Turn;
import com.mygdx.pmd.exceptions.PathFindFailureException;
import com.mygdx.pmd.model.Behavior.Pokemon.PokemonBehavior;
import com.mygdx.pmd.model.Entity.Pokemon.Pokemon;
import com.mygdx.pmd.model.Tile.Tile;
import com.mygdx.pmd.utils.AI.ShortestPath;

/**
 * Created by Cameron on 11/8/2016.
 */
public class MobMovementLogicBehavior extends PokemonBehavior {

    public MobMovementLogicBehavior(Pokemon pokemon) {
        super(pokemon);
    }

    @Override
    public void execute() {
        if(!this.canExecute()) return;


        if(pokemon.equals(pokemon.currentTile) && (pokemon.turnState == Turn.COMPLETE || controller.pokemonPlayer.turnState == Turn.WAITING))
            pokemon.setActionState(Action.IDLE);

        if(pokemon.turnState == Turn.WAITING) {
            if (pokemon.isLegalToMoveTo(pokemon.possibleNextTile)) {
                pokemon.setDirectionBasedOnTile(pokemon.possibleNextTile);

                pokemon.setNextTile(pokemon.possibleNextTile);
                pokemon.possibleNextTile = null;

                pokemon.setActionState(Action.MOVING);
                pokemon.turnState = Turn.COMPLETE;
            } else pokemon.turnState = Turn.COMPLETE;
        }

        pokemon.updateCurrentTile();
    }

    @Override
    public boolean canExecute() {
        if(pokemon.getActionState() != Action.IDLE && pokemon.getActionState() != Action.MOVING) return false;
        return true;
    }
}
