package com.mygdx.pmd.model.Behavior.Pokemon.PokePlayer;

import com.mygdx.pmd.PMD;
import com.mygdx.pmd.enumerations.*;
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
        if(!this.canExecute()) return;
        //set the possible next tile based on key hit
        try {
            if (controller.isKeyPressed(Key.down) && controller.isKeyPressed(Key.right)) {
                pokemon.possibleNextTile = (tileBoard[pokemon.currentTile.row - 1][pokemon.currentTile.col + 1]);
            } else if (controller.isKeyPressed(Key.up) && controller.isKeyPressed(Key.right)) {
                pokemon.possibleNextTile = (tileBoard[pokemon.currentTile.row + 1][pokemon.currentTile.col + 1]);
            } else if (controller.isKeyPressed(Key.up) && controller.isKeyPressed(Key.left)) {
                pokemon.possibleNextTile = (tileBoard[pokemon.currentTile.row + 1][pokemon.currentTile.col - 1]);
            } else if (controller.isKeyPressed(Key.down) && controller.isKeyPressed(Key.left)) {
                pokemon.possibleNextTile = (tileBoard[pokemon.currentTile.row - 1][pokemon.currentTile.col - 1]);
            } else if (controller.isKeyPressed(Key.down)) {
                pokemon.possibleNextTile = (tileBoard[pokemon.currentTile.row - 1][pokemon.currentTile.col]);
            } else if (controller.isKeyPressed(Key.left)) {
                pokemon.possibleNextTile = (tileBoard[pokemon.currentTile.row][pokemon.currentTile.col - 1]);
            } else if (controller.isKeyPressed(Key.right)) {
                pokemon.possibleNextTile = (tileBoard[pokemon.currentTile.row][pokemon.currentTile.col + 1]);
            } else if (controller.isKeyPressed(Key.up)) {
                pokemon.possibleNextTile = (tileBoard[pokemon.currentTile.row + 1][pokemon.currentTile.col]);
            } else pokemon.possibleNextTile = (null);
        } catch (ArrayIndexOutOfBoundsException e) {
        }

        // set the direction based on key hit - note that one can only change directions and not move when he is not moving
        if (pokemon.equals(pokemon.currentTile)) {
            if (controller.isKeyPressed(Key.down) && controller.isKeyPressed(Key.right)) {
                pokemon.direction = Direction.downright;
            } else if (controller.isKeyPressed(Key.up) && controller.isKeyPressed(Key.right)) {
                pokemon.direction = Direction.upright;
            } else if (controller.isKeyPressed(Key.up) && controller.isKeyPressed(Key.left)) {
                pokemon.direction = Direction.upleft;
            } else if (controller.isKeyPressed(Key.down) && controller.isKeyPressed(Key.left)) {
                pokemon.direction = Direction.downleft;
            } else if (controller.isKeyPressed(Key.down)) {
                pokemon.direction = Direction.down;
            } else if (controller.isKeyPressed(Key.left)) {
                pokemon.direction = Direction.left;
            } else if (controller.isKeyPressed(Key.right)) {
                pokemon.direction = Direction.right;
            } else if (controller.isKeyPressed(Key.up)) {
                pokemon.direction = Direction.up;
            }
        }

        //only allows actions to occur if the player is standing still
        if(pokemon.getActionState() == Action.IDLE) {

            if (controller.isKeyPressed(Key.space)) {
                controller.nextFloor();
            }
            if (controller.isKeyPressed(Key.b) && controller.isKeyPressed(Key.t)) {
                pokemon.attack(Move.INSTANT_KILLER);
                pokemon.setActionState(Action.ATTACKING);
                pokemon.turnState = Turn.PENDING;
            }

            if (controller.isKeyPressed(Key.a)) {
                pokemon.turnState = Turn.COMPLETE;
                pokemon.possibleNextTile = null;
            }

            if (controller.isKeyPressed(Key.p)) {
                controller.paused = !controller.paused;
            }

            if (controller.isKeyPressed(Key.r)) {
                controller.controllerScreen.game.setScreen(PMD.endScreen);
            }

        }
    }

    @Override
    public boolean canExecute() {
        if (pokemon.turnState == Turn.WAITING) return true;
        return false;
    }
}
