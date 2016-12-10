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
    ShortestPath shortestPath;
    Array<Tile> solutionNodeList;

    public MobMovementLogicBehavior(Pokemon pokemon) {
        super(pokemon);
        shortestPath = new ShortestPath(pokemon, tileBoard);
    }

    @Override
    public void execute() {
        if(pokemon.getActionState() != Action.IDLE && pokemon.getActionState() != Action.MOVING) return;

        if(pokemon.equals(pokemon.currentTile) && (pokemon.turnState == Turn.COMPLETE || controller.pokemonPlayer.turnState == Turn.WAITING))
            pokemon.setActionState(Action.IDLE);

        if(pokemon.turnState == Turn.WAITING) {
            if (pokemon.isLegalToMoveTo(pokemon.nextTile)) {
                pokemon.setDirectionBasedOnTile(pokemon.nextTile);

                pokemon.setCurrentTile(pokemon.nextTile);
                pokemon.nextTile = null;

                pokemon.setActionState(Action.MOVING);
                pokemon.turnState = Turn.COMPLETE;
            } else pokemon.turnState = Turn.COMPLETE;
        }
    }
}
