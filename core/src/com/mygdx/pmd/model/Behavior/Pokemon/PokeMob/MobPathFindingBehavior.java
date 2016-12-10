package com.mygdx.pmd.model.Behavior.Pokemon.PokeMob;

import com.badlogic.gdx.utils.Array;
import com.mygdx.pmd.enumerations.Turn;
import com.mygdx.pmd.exceptions.PathFindFailureException;
import com.mygdx.pmd.model.Behavior.Pokemon.PokemonBehavior;
import com.mygdx.pmd.model.Entity.Pokemon.Pokemon;
import com.mygdx.pmd.model.Tile.Tile;
import com.mygdx.pmd.utils.AI.ShortestPath;

/**
 * Created by Cameron on 12/9/2016.
 */
public class MobPathFindingBehavior extends PokemonBehavior {
    ShortestPath shortestPath;
    Array<Tile> solutionNodeList;

    public MobPathFindingBehavior(Pokemon pokemon) {
        super(pokemon);
        shortestPath = new ShortestPath(pokemon, tileBoard);
        solutionNodeList = new Array<Tile>();
    }

    @Override
    public void execute() {
        if(pokemon.isVisible()) return;

        if(pokemon.turnState == Turn.WAITING && pokemon.equals(pokemon.currentTile)) {
            try {
                solutionNodeList = shortestPath.pathFind(controller.pokemonPlayer.currentTile);
            } catch (PathFindFailureException e) {
                System.out.println("Failed to pathfind");
            }

            if (solutionNodeList.size <= 0) {
                pokemon.turnState = Turn.COMPLETE;
                return;
            }

            pokemon.nextTile = solutionNodeList.first();
            solutionNodeList.removeValue(pokemon.nextTile, true);
        }
    }
}
