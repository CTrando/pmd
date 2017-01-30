package com.mygdx.pmd.model.Entity.Pokemon;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Sound;
import com.mygdx.pmd.PMD;
import com.mygdx.pmd.controller.Controller;
import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.model.Behavior.Pokemon.PokePlayer.*;
import com.mygdx.pmd.model.Entity.*;
import com.mygdx.pmd.screens.DungeonScreen;
import com.mygdx.pmd.utils.observers.MovementObserver;


public class PokemonPlayer extends Pokemon {

    public PokemonPlayer(Controller controller, int x, int y, PokemonName pokemonName) {
        super(controller, x, y, pokemonName);
        this.turnState = Turn.WAITING;
        this.aggression = Aggression.passive;

        behaviors[0] = new PlayerLogic(this);
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
            if (controller.isKeyPressed(Key.shift)) {
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
            //TODO switch keys to a refresh system - perhaps use an object instead of an enum
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
            } else if (controller.isKeyPressedTimeSensitive(Key.p)) {
                controller.turnsPaused = !controller.turnsPaused;
            } else if (controller.isKeyPressed(Key.r)) {
                //controller.controllerScreen.game.setScreen(PMD.endScreen);
                for (DynamicEntity dEntity : controller.dEntities) {
                    if (dEntity instanceof PokemonMob) {
                        PokemonMob pMob = (PokemonMob) dEntity;
                        pMob.pathFind = pMob.sPath;
                    }
                }
            } else if (controller.isKeyPressedTimeSensitive(Key.m)) {
                controller.controllerScreen.showHub = !controller.controllerScreen.showHub;
                PMD.manager.get("sfx/wallhit.wav", Sound.class).play();
            } else if (controller.isKeyPressed(Key.escape) && controller.controllerScreen.showHub) {
                controller.controllerScreen.showHub = false;
            } else if (controller.isKeyPressedTimeSensitive(Key.F11)) {
                Graphics.DisplayMode mode = Gdx.graphics.getDisplayMode();
                if (Gdx.graphics.isFullscreen()) {
                    Gdx.graphics.setWindowedMode(DungeonScreen.V_WIDTH, DungeonScreen.V_HEIGHT);
                } else {
                    Gdx.graphics.setFullscreenMode(mode);
                }
                //have to do this because of bug with stuck keys with full screen
                PMD.keys.get(Input.Keys.F11).set(false);
            }
        }
    }

    public boolean canAttack() {
        return (controller.isKeyPressed(Key.b) && controller.isKeyPressed(Key.t));
    }
}
