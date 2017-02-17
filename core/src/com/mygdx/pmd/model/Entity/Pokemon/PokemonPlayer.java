package com.mygdx.pmd.model.Entity.Pokemon;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Sound;
import com.mygdx.pmd.PMD;
import com.mygdx.pmd.controller.Controller;
import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.model.Behavior.Pokemon.PokePlayer.*;
import com.mygdx.pmd.model.Entity.*;
import com.mygdx.pmd.model.Floor.*;
import com.mygdx.pmd.utils.Constants;
import com.mygdx.pmd.utils.observers.MovementObserver;


public class PokemonPlayer extends Pokemon {

    public PokemonPlayer(Floor floor, PokemonName name){
        this(floor, 0, 0, name);
    }

    public PokemonPlayer(Floor floor, int x, int y, PokemonName pokemonName) {
        super(floor, x, y, pokemonName);
        this.setTurnState(Turn.WAITING);
        //this.aggression = Aggression.passive;
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

        this.setTurnState(Turn.COMPLETE);
        floor.getScreen().game.switchScreen(PMD.endScreen);

        System.out.println("WOE IS ME I AM DEAD");

        //PMD.manager.get("sfx/background.ogg", Music.class).play();
    }

    /*
        handles all input - including movement, attack and menus
     */
    public void handleInput() {
        /*
            -updates position first - makes action state idle
            -updates input next - so would be idle and would be able to take in input
            -updates animation last - meaning change from moving to idle would not be recorded
         */
        if (this.equals(getCurrentTile()) && getActionState() == Action.IDLE) {
            if (PMD.isKeyPressed(Key.shift)) {
                if (PMD.isKeyPressed(Key.down)) {
                    direction = Direction.down;
                } else if (PMD.isKeyPressed(Key.left)) {
                    direction = Direction.left;
                } else if (PMD.isKeyPressed(Key.right)) {
                    direction = Direction.right;
                } else if (PMD.isKeyPressed(Key.up)) {
                    direction = Direction.up;
                }
            } else {
                //code for setting the user's next tile
                try {
                    if (PMD.isKeyPressed(Key.down)) {
                        possibleNextTile = (tileBoard[getCurrentTile().row - 1][getCurrentTile().col]);
                    } else if (PMD.isKeyPressed(Key.left)) {
                        possibleNextTile = (tileBoard[getCurrentTile().row][getCurrentTile().col - 1]);
                    } else if (PMD.isKeyPressed(Key.right)) {
                        possibleNextTile = (tileBoard[getCurrentTile().row][getCurrentTile().col + 1]);
                    } else if (PMD.isKeyPressed(Key.up)) {
                        possibleNextTile = (tileBoard[getCurrentTile().row + 1][getCurrentTile().col]);
                    } else {
                        possibleNextTile = (null);
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                }
            }
            //if the user hits K, he will not be able to currentMove, but he will be able to set his direction
            // set the direction based on key hit - note that one can only change directions and not currentMove when he is not moving
            //TODO switch keys to a refresh system - perhaps use an object instead of an enum
            if (PMD.isKeyPressed(Key.down)) {
                direction = Direction.down;
            } else if (PMD.isKeyPressed(Key.left)) {
                direction = Direction.left;
            } else if (PMD.isKeyPressed(Key.right)) {
                direction = Direction.right;
            } else if (PMD.isKeyPressed(Key.up)) {
                direction = Direction.up;
            }

            //actions that do not affect the player's turn or action state
            if (PMD.isKeyPressed(Key.space)) {
                floor.nextFloor();
            } else if (PMD.isKeyPressed(Key.a)) {
                this.setTurnState(Turn.COMPLETE);
                possibleNextTile = null;
            } else if (PMD.isKeyPressedTimeSensitive(Key.p)) {
                Controller.turnsPaused = !Controller.turnsPaused;
            } else if (PMD.isKeyPressed(Key.r)) {
                //PMD.screen.game.setScreen(PMD.endScreen);
                for (Entity entity : floor.entities()) {
                    if (entity instanceof PokemonMob) {
                        PokemonMob pMob = (PokemonMob) entity;
                        pMob.pathFind = pMob.sPath;
                    }
                }
            } else if (PMD.isKeyPressedTimeSensitive(Key.m)) {
                floor.getScreen().toggleHub();
                PMD.manager.get("sfx/wallhit.wav", Sound.class).play();
            } else if (PMD.isKeyPressed(Key.escape)) {
                floor.getScreen().toggleHub();
            } else if (PMD.isKeyPressedTimeSensitive(Key.F11)) {
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
            if (PMD.isKeyPressed(Key.b) && PMD.isKeyPressed(Key.t)) {
                currentMove = Move.SWIPERNOSWIPING;
            }
        }
    }

    /**
     * @return true if the pokemon has a currentMove available, false if not
     */
    public boolean canAttack() {
        return currentMove != null;
    }

    @Override
    public String toString() {
        return super.toString() + " player";
    }
}
