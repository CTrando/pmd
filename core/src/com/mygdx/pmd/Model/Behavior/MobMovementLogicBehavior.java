package com.mygdx.pmd.Model.Behavior;

import com.badlogic.gdx.utils.Array;
import com.mygdx.pmd.Enumerations.Action;
import com.mygdx.pmd.Enumerations.Key;
import com.mygdx.pmd.Enumerations.Turn;
import com.mygdx.pmd.Model.Pokemon.Pokemon;
import com.mygdx.pmd.Model.TileType.Tile;
import com.mygdx.pmd.utils.AI.ShortestPath;

/**
 * Created by Cameron on 11/8/2016.
 */
public class MobMovementLogicBehavior extends Behavior {
    ShortestPath shortestPath;
    Array<Tile> solutionNodeList;

    public MobMovementLogicBehavior(Pokemon pokemon) {
        super(pokemon);
        shortestPath = new ShortestPath(pokemon, tileBoard);
    }

    @Override
    public void execute() {
        if(pokemon.actionState != Action.IDLE && pokemon.actionState != Action.MOVING) return;


        if(pokemon.equals(pokemon.currentTile)&& controller.getPokemonPlayer().turnState == Turn.WAITING)
            pokemon.actionState = Action.IDLE;

        if (pokemon.turnState == Turn.WAITING && pokemon.equals(pokemon.currentTile)) {
            solutionNodeList = shortestPath.pathFind(controller.getPokemonPlayer().currentTile);
            if (solutionNodeList.size <= 0){
                pokemon.turnState = Turn.COMPLETE;
                pokemon.actionState = Action.IDLE;
                return;
            }

            pokemon.nextTile = solutionNodeList.first();
            solutionNodeList.removeValue(pokemon.nextTile, true);
        }

        if (pokemon.isLegalToMoveTo(pokemon.nextTile)) {
            pokemon.setDirectionBasedOnTile(pokemon.nextTile);

            pokemon.setCurrentTile(pokemon.nextTile);
            pokemon.nextTile = null;

            pokemon.actionState = Action.MOVING;
            pokemon.turnState = Turn.COMPLETE;
        }
        else pokemon.turnState = Turn.COMPLETE;
    }
}
