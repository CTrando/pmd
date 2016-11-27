package com.mygdx.pmd.model.Behavior.Pokemon.PokePlayer;

import com.mygdx.pmd.enumerations.Direction;
import com.mygdx.pmd.enumerations.Key;
import com.mygdx.pmd.enumerations.Turn;
import com.mygdx.pmd.model.Behavior.Pokemon.PokemonBehavior;
import com.mygdx.pmd.model.Entity.Pokemon.Pokemon;

/**
 * Created by Cameron on 11/8/2016.
 */
public class PlayerInputBehavior extends PokemonBehavior {

    public PlayerInputBehavior(Pokemon pokemon) {
        super(pokemon);
    }

    @Override
    public void execute() {
        if (pokemon.equals(pokemon.currentTile) && pokemon.turnState == Turn.WAITING) {
            try {
                if (controller.isKeyPressed(Key.down) && controller.isKeyPressed(Key.right)) {
                    pokemon.setNextTile(tileBoard[pokemon.currentTile.row - 1][pokemon.currentTile.col + 1]);
                    pokemon.direction = Direction.downright;
                } else if (controller.isKeyPressed(Key.up) && controller.isKeyPressed(Key.right)) {
                    pokemon.setNextTile(tileBoard[pokemon.currentTile.row + 1][pokemon.currentTile.col + 1]);
                    pokemon.direction = Direction.upright;
                } else if (controller.isKeyPressed(Key.up) && controller.isKeyPressed(Key.left)) {
                    pokemon.setNextTile(tileBoard[pokemon.currentTile.row + 1][pokemon.currentTile.col - 1]);
                    pokemon.direction = Direction.upleft;
                } else if (controller.isKeyPressed(Key.down) && controller.isKeyPressed(Key.left)) {
                    pokemon.setNextTile(tileBoard[pokemon.currentTile.row - 1][pokemon.currentTile.col - 1]);
                    pokemon.direction = Direction.downleft;
                } else if (controller.isKeyPressed(Key.down)) {
                    pokemon.setNextTile(tileBoard[pokemon.currentTile.row - 1][pokemon.currentTile.col]);
                    pokemon.direction = Direction.down;
                } else if (controller.isKeyPressed(Key.left)) {
                    pokemon.setNextTile(tileBoard[pokemon.currentTile.row][pokemon.currentTile.col - 1]);
                    pokemon.direction = Direction.left;
                } else if (controller.isKeyPressed(Key.right)) {
                    pokemon.setNextTile(tileBoard[pokemon.currentTile.row][pokemon.currentTile.col + 1]);
                    pokemon.direction = Direction.right;
                } else if (controller.isKeyPressed(Key.up)) {
                    pokemon.setNextTile(tileBoard[pokemon.currentTile.row + 1][pokemon.currentTile.col]);
                    pokemon.direction = Direction.up;
                }
                else pokemon.setNextTile(null);
            } catch (ArrayIndexOutOfBoundsException e) {
            }

            if(!pokemon.isLegalToMoveTo(pokemon.nextTile)){
                pokemon.nextTile = null;
            }

            if (controller.isKeyPressed(Key.space)) {
                controller.currentFloor.getFloorGenerator().generateFloor();
                controller.currentFloor.getFloorGenerator().controller.randomizeAllPokemonLocation();
            }
            if (controller.isKeyPressed(Key.a)) {
                pokemon.turnState = Turn.COMPLETE;
                pokemon.nextTile = null;
            }

            if (controller.isKeyPressed(Key.b)) {
                controller.controllerScreen.switchMenus("defaultMenu");
            }
        }
    }
}
