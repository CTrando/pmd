package com.mygdx.pmd.model.Entity.Pokemon;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Sound;
import com.mygdx.pmd.PMD;
import com.mygdx.pmd.controller.Controller;
import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.model.Behavior.Pokemon.PokePlayer.*;
import com.mygdx.pmd.model.Entity.*;
import com.mygdx.pmd.utils.Constants;
import com.mygdx.pmd.utils.observers.MovementObserver;


public class PokemonPlayer extends Pokemon {

    public PokemonPlayer(Controller controller, int x, int y, PokemonName pokemonName) {
        super(controller, x, y, pokemonName);
        this.setTurnState(Turn.WAITING);
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
        this.setTurnState(Turn.WAITING);
    }

    @Override
    public void dispose() {
        super.dispose();
        controller.screen.game.setScreen(PMD.endScreen);
        //PMD.manager.get("sfx/background.ogg", Music.class).play();
    }

    public boolean canMove() {
        return isLegalToMoveTo(possibleNextTile);
    }

    /*
        handles all input - including movement, attack and menus
     */
    public void handleInput() {
        //TODO work out if this second part of the if statement may cause a graphical bug
        /*
            -updates position first - makes action state idle
            -updates input next - so would be idle and would be able to take in input
            -updates animation last - meaning change from moving to idle would not be recorded
         */
        if (this.equals(getCurrentTile()) && getActionState() == Action.IDLE) {
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
            } else {
                //code for setting the user's next tile
                try {
                    if (controller.isKeyPressed(Key.down)) {
                        possibleNextTile = (tileBoard[getCurrentTile().row - 1][getCurrentTile().col]);
                    } else if (controller.isKeyPressed(Key.left)) {
                        possibleNextTile = (tileBoard[getCurrentTile().row][getCurrentTile().col - 1]);
                    } else if (controller.isKeyPressed(Key.right)) {
                        possibleNextTile = (tileBoard[getCurrentTile().row][getCurrentTile().col + 1]);
                    } else if (controller.isKeyPressed(Key.up)) {
                        possibleNextTile = (tileBoard[getCurrentTile().row + 1][getCurrentTile().col]);
                    } else {
                        possibleNextTile = (null);
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                }
            }
            //if the user hits K, he will not be able to move, but he will be able to set his direction
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
                this.setTurnState(Turn.COMPLETE);
                possibleNextTile = null;
            } else if (controller.isKeyPressedTimeSensitive(Key.p)) {
                controller.turnsPaused = !controller.turnsPaused;
            } else if (controller.isKeyPressed(Key.r)) {
                //controller.screen.game.setScreen(PMD.endScreen);
                for (DynamicEntity dEntity : controller.dEntities) {
                    if (dEntity instanceof PokemonMob) {
                        PokemonMob pMob = (PokemonMob) dEntity;
                        pMob.pathFind = pMob.sPath;
                    }
                }
            } else if (controller.isKeyPressedTimeSensitive(Key.m)) {
                controller.screen.toggleHub();
                PMD.manager.get("sfx/wallhit.wav", Sound.class).play();
            } else if (controller.isKeyPressed(Key.escape) && controller.screen.showHub) {
                controller.screen.toggleHub();
            } else if (controller.isKeyPressedTimeSensitive(Key.F11)) {
                Graphics.DisplayMode mode = Gdx.graphics.getDisplayMode();
                if (Gdx.graphics.isFullscreen()) {
                    Gdx.graphics.setWindowedMode(Constants.V_WIDTH, Constants.V_HEIGHT);
                } else {
                    Gdx.graphics.setFullscreenMode(mode);
                }
                //have to do this because of bug with stuck keys with full screen
                PMD.keys.get(Input.Keys.F11).set(false);
            }

            //these are for the attacks
            if (controller.isKeyPressed(Key.b) && controller.isKeyPressed(Key.t)) {
                move = Move.SWIPERNOSWIPING;
            }
        }
    }

    /**
     * @return true if the pokemon has a move available, false if not
     */
    public boolean canAttack() {
        return move != null;
    }

    @Override
    public String toString(){
        return super.toString() + " player";
    }
}
