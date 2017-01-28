package com.mygdx.pmd.model.Entity.Pokemon;

import com.mygdx.pmd.PMD;
import com.mygdx.pmd.controller.Controller;
import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.model.Behavior.Pokemon.*;
import com.mygdx.pmd.model.Behavior.Pokemon.PokePlayer.*;
import com.mygdx.pmd.model.Entity.*;
import com.mygdx.pmd.utils.observers.MovementObserver;


public class PokemonPlayer extends Pokemon {

    public PokemonPlayer(Controller controller, int x, int y, PokemonName pokemonName) {
        super(controller, x, y, pokemonName);
        this.turnState = Turn.WAITING;
        this.aggression = Aggression.passive;

        /*behaviors[BaseBehavior.INPUT_BEHAVIOR] = new PlayerInputBehavior(this);
        behaviors[BaseBehavior.ATTACK_BEHAVIOR] = new PlayerAttackLogicBehavior(this);
        behaviors[BaseBehavior.LOGIC_BEHAVIOR] = new PlayerMovementLogicBehavior(this);
        behaviors[BaseBehavior.MOVE_BEHAVIOR] = new MoveSlowBehavior(this);
        behaviors[BaseBehavior.ANIMATION_BEHAVIOR] = new PokemonAnimationBehavior(this);*/

        behaviors[0] = new PlayerLogic(this);
        behaviors[1] = new PokemonAnimationBehavior(this);
    }

    @Override
    public void registerObservers() {
        observers[0] = new MovementObserver(this);
    }

    @Override
    public void randomizeLocation() {
        super.randomizeLocation();
        this.turnState = Turn.WAITING;
    }

    @Override
    public void dispose() {
        super.dispose();
        controller.controllerScreen.game.setScreen(PMD.endScreen);
        //PMD.manager.get("sfx/background.ogg", Music.class).play();
    }

    public boolean canMove() {
        if (isLegalToMoveTo(possibleNextTile)) {
            if (possibleNextTile.dynamicEntities.size > 0) {
                for (DynamicEntity dEntity : possibleNextTile.dynamicEntities) {
                    dEntity.forceMoveToTile(currentTile);
                }
            }
            setNextTile(possibleNextTile);
        } else {
            return false;
        }
        possibleNextTile = null;

        return true;
    }


    /*
        These extra inputs do not take turns and can occur as often as the user wishes
     */
    public void handleInput() {
        //TODO work out if this second part of the if statement may cause a graphical bug
        /*
            -updates position first - makes action state idle
            -updates input next - so would be idle and would be able to take in input
            -updates animation last - meaning change from moving to idle would not be recorded
         */
        if (this.equals(currentTile) && getActionState() == Action.IDLE) {
            //if the user hits K, he will not be able to move, but he will be able to set his direction
            if (controller.isKeyPressed(Key.k)) {
                if (controller.isKeyPressed(Key.down)) {
                    direction = Direction.down;
                } else if (controller.isKeyPressed(Key.left)) {
                    direction = Direction.left;
                } else if (controller.isKeyPressed(Key.right)) {
                    direction = Direction.right;
                } else if (controller.isKeyPressed(Key.up)) {
                    direction = Direction.up;
                }
            } else
                //code for setting the user's next tile
                try {
                    if (controller.isKeyPressed(Key.down)) {
                        possibleNextTile = (tileBoard[currentTile.row - 1][currentTile.col]);
                    } else if (controller.isKeyPressed(Key.left)) {
                        possibleNextTile = (tileBoard[currentTile.row][currentTile.col - 1]);
                    } else if (controller.isKeyPressed(Key.right)) {
                        possibleNextTile = (tileBoard[currentTile.row][currentTile.col + 1]);
                    } else if (controller.isKeyPressed(Key.up)) {
                        possibleNextTile = (tileBoard[currentTile.row + 1][currentTile.col]);
                    } else {
                        possibleNextTile = (null);
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                }

            // set the direction based on key hit - note that one can only change directions and not move when he is not moving

            if (controller.isKeyPressed(Key.down)) {
                direction = Direction.down;
            } else if (controller.isKeyPressed(Key.left)) {
                direction = Direction.left;
            } else if (controller.isKeyPressed(Key.right)) {
                direction = Direction.right;
            } else if (controller.isKeyPressed(Key.up)) {
                direction = Direction.up;
            }

            //actions that do not affect the player's turn or action state
            if (controller.isKeyPressed(Key.space)) {
                controller.nextFloor();
            } else if (controller.isKeyPressed(Key.a)) {
                turnState = Turn.COMPLETE;
                possibleNextTile = null;
            } else if (controller.isKeyPressed(Key.p)) {
                controller.turnsPaused = !controller.turnsPaused;
            } else if (controller.isKeyPressed(Key.r)) {
                controller.controllerScreen.game.setScreen(PMD.endScreen);
            }
        }
    }

    public boolean canAttack() {
        return (controller.isKeyPressed(Key.b) && controller.isKeyPressed(Key.t));
    }
}
