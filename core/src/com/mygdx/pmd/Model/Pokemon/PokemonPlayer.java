package com.mygdx.pmd.Model.Pokemon;

import com.mygdx.pmd.Controller.Controller;
import com.mygdx.pmd.Enumerations.*;

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
            if (this.nextTile == null)
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
                controller.freezeKeys();
                this.turnState = Turn.COMPLETE;
                this.setCurrentTile(this.getNextTile());
            } else {
                return;
            }
            this.setNextTile(null); //set it to null so turn does not remain complete look up
        }
    }


    public void handleInput() {
        if (!controller.keyFrozen) {
            if (controller.isKeyPressed()) {
                try {
                    if (controller.isDownPressed() && controller.isRightPressed()) {
                        this.setNextTile(tileBoard[this.getCurrentTile().row - 1][this.getCurrentTile().col + 1]);
                    } else if (controller.isUpPressed() && controller.isRightPressed()) {
                        this.setNextTile(tileBoard[this.getCurrentTile().row + 1][this.getCurrentTile().col + 1]);
                    } else if (controller.isUpPressed() && controller.isLeftPressed()) {
                        this.setNextTile(tileBoard[this.getCurrentTile().row + 1][this.getCurrentTile().col - 1]);
                    } else if (controller.isDownPressed() && controller.isLeftPressed()) {
                        this.setNextTile(tileBoard[this.getCurrentTile().row - 1][this.getCurrentTile().col - 1]);
                    } else if (controller.isDownPressed()) {
                        this.setNextTile(tileBoard[this.getCurrentTile().row - 1][this.getCurrentTile().col]);
                    } else if (controller.isLeftPressed()) {
                        this.setNextTile(tileBoard[this.getCurrentTile().row][this.getCurrentTile().col - 1]);
                    } else if (controller.isRightPressed()) {
                        this.setNextTile(tileBoard[this.getCurrentTile().row][this.getCurrentTile().col + 1]);
                    } else if (controller.isUpPressed()) {
                        this.setNextTile(tileBoard[this.getCurrentTile().row + 1][this.getCurrentTile().col]);
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                }
            }
        }
        if (controller.isDownPressed() && controller.isRightPressed()) {
            this.direction = Direction.downright;
        } else if (controller.isUpPressed() && controller.isRightPressed()) {
            this.direction = Direction.upright;
        } else if (controller.isUpPressed() && controller.isLeftPressed()) {
            this.direction = Direction.upleft;
        } else if (controller.isDownPressed() && controller.isLeftPressed()) {
            this.direction = Direction.downleft;
        } else if (controller.isDownPressed()) {
            this.direction = Direction.down;
        } else if (controller.isLeftPressed()) {
            this.direction = Direction.left;
        } else if (controller.isRightPressed()) {
            this.direction = Direction.right;
        } else if (controller.isUpPressed()) {
            this.direction = Direction.up;
        }

        if (controller.isSpacePressed()) {
            controller.getCurrentFloor().getFloorGenerator().generateFloor();
            controller.getCurrentFloor().getFloorGenerator().controller.randomizeAllPokemonLocation();
        }
        if (controller.isAPressed && this.turnState == Turn.WAITING) {
            this.turnState = Turn.COMPLETE;
        }
        if (controller.isAPressed && controller.isSPressed()) {
            this.turnState = Turn.COMPLETE;
        }
        if (controller.isBPressed) {
            controller.controllerScreen.switchMenus("defaultMenu");
        }
    }
}
