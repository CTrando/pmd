package com.mygdx.pmd.Model.Pokemon;

import com.mygdx.pmd.Controller.Controller;
import com.mygdx.pmd.Enumerations.*;
import com.mygdx.pmd.Model.Behavior.*;
import com.mygdx.pmd.utils.FastMotionBehavior;
import com.mygdx.pmd.utils.SlowMotionBehavior;

public class PokemonPlayer extends Pokemon {

    public PokemonPlayer(Controller controller, int x, int y, PokemonName pokemonName) {
        super(controller, x, y, pokemonName);
        this.turnState = Turn.WAITING;

        behaviors = new Behavior[4];
        behaviors[Behavior.INPUT_BEHAVIOR] = new InputBehavior(this);
        behaviors[Behavior.LOGIC_BEHAVIOR] = new LogicBehavior(this);
        behaviors[Behavior.MOVE_BEHAVIOR] = new PlayerMoveBehavior(this);
        behaviors[Behavior.ANIMATION_BEHAVIOR] = new AnimationBehavior(this);
    }

   /* @Override
    public void updatePosition() {
        super.updatePosition();

        if (this.equals(this.getCurrentTile()) && actionState == Action.MOVING) {
            controller.unfreezeKeys();
            this.getCurrentTile().playEvents();

            //TODO figure out a better way for logic with IDLE actionstates
            this.handleInput();
            if (this.nextTile == null || !this.turnBehavior.isTurnWaiting())
                this.actionState = Action.IDLE;
            this.setNextTile(null);
        }
    }

    @Override
    public void updateLogic() {
        if (!this.turnBehavior.isTurnComplete())
            this.handleInput();

        if (this.getNextTile() != null) {
            if (this.isLegalToMoveTo(this.getNextTile())) {
                this.actionState = Action.MOVING;
                if(controller.isKeyPressed(Key.s))
                    this.motionBehavior = new FastMotionBehavior(this);
                else
                    this.motionBehavior = (new SlowMotionBehavior(this));

                controller.freezeKeys();
                this.turnBehavior.setTurnState(Turn.COMPLETE);
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
        if (controller.isKeyPressed(Key.a)) {
            this.turnBehavior.setTurnState(Turn.COMPLETE);
        }

        if (controller.isKeyPressed(Key.a) && controller.isKeyPressed(Key.s) && this.turnBehavior.isTurnComplete()) {
            this.turnBehavior.setTurnState(Turn.COMPLETE);
        }

        if (controller.isKeyPressed(Key.b)) {
            controller.controllerScreen.switchMenus("defaultMenu");
        }
    }*/
}
