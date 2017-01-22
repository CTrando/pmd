package com.mygdx.pmd.model.Entity.Pokemon;

import com.mygdx.pmd.PMD;
import com.mygdx.pmd.controller.Controller;
import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.model.Behavior.Pokemon.PokePlayer.PlayerLogic;
import com.mygdx.pmd.model.Behavior.Pokemon.PokemonAnimationBehavior;
import com.mygdx.pmd.model.Entity.DynamicEntity;
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
                return false;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }

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

    public void handleExtraInput() {
        // set the direction based on key hit - note that one can only change directions and not move when he is not moving
        if (this.equals(currentTile)) {
            if (controller.isKeyPressed(Key.down)) {
                direction = Direction.down;
            } else if (controller.isKeyPressed(Key.left)) {
                direction = Direction.left;
            } else if (controller.isKeyPressed(Key.right)) {
                direction = Direction.right;
            } else if (controller.isKeyPressed(Key.up)) {
                direction = Direction.up;
            }

            if (getActionState() == Action.IDLE) {

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


    }

    public boolean canAttack() {
        return (controller.isKeyPressed(Key.b) && controller.isKeyPressed(Key.t));
    }
}
