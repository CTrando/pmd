package com.mygdx.pmd.Model.Pokemon;

import com.badlogic.gdx.Input;
import com.mygdx.pmd.Controller.Controller;
import com.mygdx.pmd.Enumerations.*;
import com.mygdx.pmd.utils.FastMotionBehavior;
import com.mygdx.pmd.utils.SlowMotionBehavior;

public class PokemonPlayer extends Pokemon {
    public PokemonPlayer(int x, int y, Controller controller, boolean move, PokemonName pokemonName) {
        super(controller, x, y, move, pokemonName);
        this.turnState = Turn.WAITING;
    }

    @Override
    public void updatePosition() {
        super.updatePosition();

        if (this.equals(this.getCurrentTile()) && actionState == Action.MOVING) {
            controller.unfreezeKeys();
            this.getCurrentTile().playEvents();

            //TODO figure out a better way for logic with IDLE actionstates
            this.handleInput();
            if (this.nextTile == null || this.turnState != Turn.WAITING)
                this.actionState = Action.IDLE;
            this.setNextTile(null);
        }
    }

    @Override
    public void updateLogic() {
        if (this.turnState != Turn.COMPLETE)
            this.handleInput();

        if (this.getNextTile() != null) {
            if (this.isLegalToMoveTo(this.getNextTile())) {
                this.actionState = Action.MOVING;
                if(controller.isKeyPressed(Key.s))
                    this.motionBehavior = new FastMotionBehavior(this);
                else
                    this.motionBehavior = (new SlowMotionBehavior(this));

                controller.freezeKeys();
                this.turnState = Turn.COMPLETE;
                this.setCurrentTile(this.getNextTile());
            } else {
                return;
            }
            this.setNextTile(null); //set it to null so turn does not remain complete -look up
        }
    }


    public void handleInput() {
        if (controller.keyFrozen) return;

        if (controller.isKeyPressed()) {
            try {
                if (controller.isKeyPressed(Key.down) && controller.isKeyPressed(Key.right)) {
                    this.setNextTile(tileBoard[this.getCurrentTile().row - 1][this.getCurrentTile().col + 1]);
                    this.direction = Direction.downright;
                } else if (controller.isKeyPressed(Key.up) && controller.isKeyPressed(Key.right)) {
                    this.setNextTile(tileBoard[this.getCurrentTile().row + 1][this.getCurrentTile().col + 1]);
                    this.direction = Direction.upright;
                } else if (controller.isKeyPressed(Key.up) && controller.isKeyPressed(Key.left)) {
                    this.setNextTile(tileBoard[this.getCurrentTile().row + 1][this.getCurrentTile().col - 1]);
                    this.direction = Direction.upleft;
                } else if (controller.isKeyPressed(Key.down) && controller.isKeyPressed(Key.left)) {
                    this.setNextTile(tileBoard[this.getCurrentTile().row - 1][this.getCurrentTile().col - 1]);
                    this.direction = Direction.downleft;
                } else if (controller.isKeyPressed(Key.down)) {
                    this.setNextTile(tileBoard[this.getCurrentTile().row - 1][this.getCurrentTile().col]);
                    this.direction = Direction.down;
                } else if (controller.isKeyPressed(Key.left)) {
                    this.setNextTile(tileBoard[this.getCurrentTile().row][this.getCurrentTile().col - 1]);
                    this.direction = Direction.left;
                } else if (controller.isKeyPressed(Key.right)) {
                    this.setNextTile(tileBoard[this.getCurrentTile().row][this.getCurrentTile().col + 1]);
                    this.direction = Direction.right;
                } else if (controller.isKeyPressed(Key.up)) {
                    this.setNextTile(tileBoard[this.getCurrentTile().row + 1][this.getCurrentTile().col]);
                    this.direction = Direction.up;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
            }
        }

        if (controller.isKeyPressed(Key.space)) {
            controller.getCurrentFloor().getFloorGenerator().generateFloor();
            controller.getCurrentFloor().getFloorGenerator().controller.randomizeAllPokemonLocation();
        }
        if (controller.isKeyPressed(Key.a) && this.turnState == Turn.WAITING) {
            this.turnState = Turn.COMPLETE;
        }
        if (controller.isKeyPressed(Key.a) && controller.isKeyPressed(Key.s)) {
            this.turnState = Turn.COMPLETE;
        }
        if (controller.isKeyPressed(Key.b)) {
            controller.controllerScreen.switchMenus("defaultMenu");
        }
    }
}
