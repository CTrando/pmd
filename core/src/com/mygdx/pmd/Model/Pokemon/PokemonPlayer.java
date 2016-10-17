package com.mygdx.pmd.Model.Pokemon;

import com.badlogic.gdx.audio.Sound;
import com.mygdx.pmd.Controller.Controller;
import com.mygdx.pmd.Enumerations.Action;
import com.mygdx.pmd.Enumerations.Turn;
import com.mygdx.pmd.Enumerations.PokemonName;
import com.mygdx.pmd.Screen.DungeonScreen;

public class PokemonPlayer extends Pokemon {


    public PokemonPlayer(int x, int y, Controller controller, boolean move, PokemonName pokemonName) {
        super(controller, x, y, move, pokemonName);
        this.setTurnState(Turn.WAITING);
    }

    @Override
    public void updatePosition() {
        if (this.getNextTile() != null) {
            if (!this.equals(this.getCurrentTile()))
                this.motionLogic(); //explanatory
            else if (this.equals(this.getCurrentTile())) {
                controller.unfreezeKeys();
                this.getCurrentTile().playEvents();
                //this.setTurnState(Turn.COMPLETE);
                this.actionState = Action.IDLE;
                this.setNextTile(null);
            }
        }
    }

    public void motionLogic() {
        if (this.isWithinArea(controller.loadedArea)) {
            if (controller.isSPressed()) {
                this.moveFast();
            } else
                this.goToTile(this.getCurrentTile(), 1);
        }
    }

    public void checkInputAndSetNextTile() {
        if (controller.isKeyPressed()) {
            try {
                if (controller.isDownPressed() && controller.isRightPressed()) {
                    this.setNextTile(tileBoard[this.getCurrentTile().getRow() - 1][this.getCurrentTile().getCol() + 1]);
                } else if (controller.isUpPressed() && controller.isRightPressed()) {
                    this.setNextTile(tileBoard[this.getCurrentTile().getRow() + 1][this.getCurrentTile().getCol() + 1]);
                } else if (controller.isUpPressed() && controller.isLeftPressed()) {
                    this.setNextTile(tileBoard[this.getCurrentTile().getRow() + 1][this.getCurrentTile().getCol() - 1]);
                } else if (controller.isDownPressed() && controller.isLeftPressed()) {
                    this.setNextTile(tileBoard[this.getCurrentTile().getRow() - 1][this.getCurrentTile().getCol() - 1]);
                } else if (controller.isDownPressed()) {
                    this.setNextTile(tileBoard[this.getCurrentTile().getRow() - 1][this.getCurrentTile().getCol()]);
                } else if (controller.isLeftPressed()) {
                    this.setNextTile(tileBoard[this.getCurrentTile().getRow()][this.getCurrentTile().getCol() - 1]);
                } else if (controller.isRightPressed()) {
                    this.setNextTile(tileBoard[this.getCurrentTile().getRow()][this.getCurrentTile().getCol() + 1]);
                } else if (controller.isUpPressed()) {
                    this.setNextTile(tileBoard[this.getCurrentTile().getRow() + 1][this.getCurrentTile().getCol()]);
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                e.printStackTrace();
            }

            if (this.getNextTile() != null) {
                if (this.isLegalToMoveTo(this.getNextTile())) {
                    this.actionState = Action.MOVING;
                    controller.freezeKeys();
                    this.setTurnState(Turn.COMPLETE);
                    this.setCurrentTile(this.getNextTile());
                } else {
                    controller.controllerScreen.manager.get("sfx/wallhit.wav", Sound.class).play();
                    this.setNextTile(null);
                    return;
                }
            }

        }
    }

    public void updateKeyEvents() {
        if (!controller.keyFrozen)
            this.checkInputAndSetNextTile(); //explanatory

        if (controller.isDownPressed() && controller.isRightPressed()) {
            this.downRight();
        } else if (controller.isUpPressed() && controller.isRightPressed()) {
            this.upRight();
        } else if (controller.isUpPressed() && controller.isLeftPressed()) {
            this.upLeft();
        } else if (controller.isDownPressed() && controller.isLeftPressed()) {
            this.downLeft();
        } else if (controller.isDownPressed()) {
            this.down();
        } else if (controller.isLeftPressed()) {
            this.left();
        } else if (controller.isRightPressed()) {
            this.right();
        } else if (controller.isUpPressed()) {
            this.up();
        }

        if (controller.isSpacePressed()) {
            controller.getCurrentFloor().getFloorGenerator().generateFloor();
            controller.getCurrentFloor().getFloorGenerator().controller.randomizeAllPokemonLocation();
        }

        if (controller.isAPressed && this.getTurnState() == Turn.WAITING) {
            this.setTurnState(Turn.COMPLETE);
        }

        if (controller.isAPressed && controller.isSPressed()) {
            this.setTurnState(Turn.COMPLETE);
        }

        if (controller.isBPressed) {
            controller.controllerScreen.switchMenus("defaultMenu");
        }
    }

    @Override
    public void updateLogic() {
        if(this.getTurnState() != Turn.COMPLETE)
            this.updateKeyEvents();

        switch (this.actionState) {
            case MOVING:
                this.updatePosition();
                break;

        }
    }

}
