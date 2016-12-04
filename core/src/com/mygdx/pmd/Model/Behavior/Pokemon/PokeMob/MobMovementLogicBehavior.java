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

        if(pokemon.currentTile == null) System.out.println("NULL CURRENTTILE");

        if(pokemon.equals(pokemon.currentTile)&& controller.pokemonPlayer.turnState == Turn.WAITING)
            pokemon.setActionState(Action.IDLE);

        if (pokemon.turnState == Turn.WAITING && pokemon.equals(pokemon.currentTile)) {
            try {
                solutionNodeList = shortestPath.pathFind(controller.pokemonPlayer.currentTile);
            } catch (PathFindFailureException e){
                e.printStackTrace();
                System.out.println("Failed to pathfind");
            }

            if (solutionNodeList.size <= 0){
                pokemon.turnState = Turn.COMPLETE;
                pokemon.setActionState(Action.IDLE);
                return;
            }

            pokemon.nextTile = solutionNodeList.first();
            solutionNodeList.removeValue(pokemon.nextTile, true);
        }

        if (pokemon.isLegalToMoveTo(pokemon.nextTile)) {
            pokemon.setDirectionBasedOnTile(pokemon.nextTile);

            pokemon.setCurrentTile(pokemon.nextTile);
            pokemon.nextTile = null;

            pokemon.setActionState(Action.MOVING);
            pokemon.turnState = Turn.COMPLETE;
        }
        else pokemon.turnState = Turn.COMPLETE;
    }
}
