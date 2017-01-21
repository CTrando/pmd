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
            if (controller.isKeyPressed(Key.down)) {
                pMob.possibleNextTile = (tileBoard[pMob.currentTile.row - 1][pMob.currentTile.col]);
            } else if (controller.isKeyPressed(Key.left)) {
                pMob.possibleNextTile = (tileBoard[pMob.currentTile.row][pMob.currentTile.col - 1]);
            } else if (controller.isKeyPressed(Key.right)) {
                pMob.possibleNextTile = (tileBoard[pMob.currentTile.row][pMob.currentTile.col + 1]);
            } else if (controller.isKeyPressed(Key.up)) {
                pMob.possibleNextTile = (tileBoard[pMob.currentTile.row + 1][pMob.currentTile.col]);
            } else pMob.possibleNextTile = (null);
        } catch (ArrayIndexOutOfBoundsException e) {
        }

        // set the direction based on key hit - note that one can only change directions and not move when he is not moving
        if (pMob.equals(pMob.currentTile)) {
            if (controller.isKeyPressed(Key.down)) {
                pMob.direction = Direction.down;
            } else if (controller.isKeyPressed(Key.left)) {
                pMob.direction = Direction.left;
            } else if (controller.isKeyPressed(Key.right)) {
                pMob.direction = Direction.right;
            } else if (controller.isKeyPressed(Key.up)) {
                pMob.direction = Direction.up;
            }
        }

        //only allows actions to occur if the player is standing still
        if(pMob.getActionState() == Action.IDLE) {

            if (controller.isKeyPressed(Key.space)) {
                controller.nextFloor();
            }
            if (controller.isKeyPressed(Key.b) && controller.isKeyPressed(Key.t)) {
                pMob.attack(Move.INSTANT_KILLER);
                pMob.setActionState(Action.ATTACKING);
                pMob.turnState = Turn.PENDING;
            }
            else
            if (controller.isKeyPressed(Key.a)) {
                pMob.turnState = Turn.COMPLETE;
                pMob.possibleNextTile = null;
            }
            else
            if (controller.isKeyPressed(Key.p)) {
                controller.paused = !controller.paused;
            }
            else
            if (controller.isKeyPressed(Key.r)) {
                controller.controllerScreen.game.setScreen(PMD.endScreen);
            }

        }
    }

    @Override
    public boolean canExecute() {
        if (pMob.turnState == Turn.WAITING) return true;
        return false;
    }
}
