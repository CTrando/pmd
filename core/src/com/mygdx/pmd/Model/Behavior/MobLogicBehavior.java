package com.mygdx.pmd.Model.Behavior;

import com.badlogic.gdx.utils.Array;
import com.mygdx.pmd.Enumerations.Action;
import com.mygdx.pmd.Enumerations.Turn;
import com.mygdx.pmd.Model.Pokemon.Pokemon;
import com.mygdx.pmd.Model.TileType.Tile;
import com.mygdx.pmd.utils.AI.ShortestPath;

/**
 * Created by Cameron on 11/8/2016.
 */
public class MobLogicBehavior extends Behavior {
    ShortestPath shortestPath;
    Array<Tile> solutionNodeList;

    public MobLogicBehavior(Pokemon pokemon) {
        super(pokemon);
        shortestPath = new ShortestPath(pokemon, tileBoard);
    }

    @Override
    public void execute() {
        if(pokemon.equals(pokemon.currentTile) && pokemon.turnState == Turn.COMPLETE)
            pokemon.actionState = Action.IDLE;

        if (pokemon.turnState == Turn.WAITING && pokemon.equals(pokemon.currentTile)) {

            solutionNodeList = shortestPath.findShortestPath(controller.getPokemonPlayer().currentTile);
            if (solutionNodeList.size <= 0){
                pokemon.turnState = Turn.COMPLETE;
                return;
            }

            pokemon.nextTile = solutionNodeList.first();

            if (pokemon.isLegalToMoveTo(pokemon.nextTile)) {

                pokemon.setCurrentTile(pokemon.nextTile);
                pokemon.setDirectionBasedOnTile(pokemon.nextTile);
                pokemon.nextTile = null;

                pokemon.actionState = Action.MOVING;
                pokemon.turnState = Turn.COMPLETE;
            }
            else pokemon.turnState = Turn.COMPLETE;
        }
    }


}
