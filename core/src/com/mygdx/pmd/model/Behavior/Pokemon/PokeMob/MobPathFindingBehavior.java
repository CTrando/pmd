package com.mygdx.pmd.model.Behavior.Pokemon.PokeMob;

import com.badlogic.gdx.utils.Array;
import com.mygdx.pmd.enumerations.Turn;
import com.mygdx.pmd.exceptions.PathFindFailureException;
import com.mygdx.pmd.model.Behavior.Pokemon.PokemonBehavior;
import com.mygdx.pmd.model.Entity.Pokemon.Pokemon;
import com.mygdx.pmd.model.Tile.Tile;
import com.mygdx.pmd.utils.AI.ShortestPath;
import com.mygdx.pmd.utils.AI.Wander;

/**
 * Created by Cameron on 12/9/2016.
 */
public class MobPathFindingBehavior extends PokemonBehavior {
    ShortestPath shortestPath;
    Wander wander;
    Array<Tile> solutionNodeList;

    public MobPathFindingBehavior(Pokemon pokemon) {
        super(pokemon);
        shortestPath = new ShortestPath(pokemon);
        wander = new Wander(pokemon);
        solutionNodeList = new Array<Tile>();
    }

    @Override
    public void execute() {
        if(!this.canExecute()) return;

        if(pokemon.turnState == Turn.WAITING && pokemon.equals(pokemon.currentTile)) {
            try {
                solutionNodeList = wander.pathFind(controller.pokemonPlayer.nextTile);
                //solutionNodeList = shortestPath.pathFind(controller.pokemonPlayer.nextTile);
            } catch (PathFindFailureException e) {
                System.out.println("Failed to pathfind");
            }

            if (solutionNodeList.size <= 0) {
                pokemon.turnState = Turn.COMPLETE;
                return;
            }

            pokemon.possibleNextTile = solutionNodeList.first();
            solutionNodeList.removeValue(pokemon.possibleNextTile, true);
        }
    }

    @Override
    public boolean canExecute() {
        if(pokemon.isEnemyInSight()) return false;
        return true;
    }
}
