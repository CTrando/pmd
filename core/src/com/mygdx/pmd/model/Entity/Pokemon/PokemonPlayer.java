package com.mygdx.pmd.model.Entity.Pokemon;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Sound;
import com.mygdx.pmd.PMD;
import com.mygdx.pmd.controller.Controller;
import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.model.Entity.*;
import com.mygdx.pmd.model.Floor.*;
import com.mygdx.pmd.model.logic.*;
import com.mygdx.pmd.utils.Constants;

public class PokemonPlayer extends Pokemon {

    PokemonPlayer(Floor floor, PokemonName name){
        this(floor, 0, 0, name);
    }

    PokemonPlayer(Floor floor, int x, int y, PokemonName pokemonName) {
        super(floor, x, y, pokemonName);
    }

    @Override
    public void init(){
        super.init();
        this.tc.setTurnState(Turn.WAITING);
        this.cc.setAggressionState(Aggression.passive);

        logic = new PlayerLogic(this);
    }

    @Override
    public void randomizeLocation() {
        super.randomizeLocation();
        tc.setTurnState(Turn.WAITING);
    }

    @Override
    public void dispose() {
        super.dispose();

        tc.setTurnState(Turn.COMPLETE);
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
            -updates animationLogic last - meaning change from moving to idle would not be recorded
         */
        if (this.equals(pc.getCurrentTile()) && ac.getActionState() == Action.IDLE) {
            if (PMD.isKeyPressed(Key.shift)) {
                if (PMD.isKeyPressed(Key.down)) {
                    dc.setDirection(Direction.down);
                } else if (PMD.isKeyPressed(Key.left)) {
                    dc.setDirection(Direction.left);
                } else if (PMD.isKeyPressed(Key.right)) {
                    dc.setDirection(Direction.right);
                } else if (PMD.isKeyPressed(Key.up)) {
                    dc.setDirection(Direction.up);
                }
            } else {
                //code for setting the user's next tile
                try {
                    int curRow = pc.getCurrentTile().row;
                    int curCol = pc.getCurrentTile().col;
                    if (PMD.isKeyPressed(Key.down)) {
                        mc.possibleNextTile = (tileBoard[curRow - 1][curCol]);
                        dc.setDirection(Direction.down);
                    } else if (PMD.isKeyPressed(Key.left)) {
                        mc.possibleNextTile = (tileBoard[curRow][curCol - 1]);
                        dc.setDirection(Direction.left);
                    } else if (PMD.isKeyPressed(Key.right)) {
                        mc.possibleNextTile = (tileBoard[curRow][curCol + 1]);
                        dc.setDirection(Direction.right);
                    } else if (PMD.isKeyPressed(Key.up)) {
                        mc.possibleNextTile = (tileBoard[curRow + 1][curCol]);
                        dc.setDirection(Direction.up);
                    } else {
                        mc.possibleNextTile = (null);
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                }
            }
            anc.setCurrentAnimation(dc.getDirection()+"idle");

            //TODO switch keys to a refresh system - perhaps use an object instead of an enum


            //actions that do not affect the player's turn or action state
            if (PMD.isKeyPressed(Key.space)) {
                floor.nextFloor();
            } else if (PMD.isKeyPressedTimeSensitive(Key.a)) {
                System.out.println("Skip turn!");
                tc.setTurnState(Turn.COMPLETE);
                pc.getCurrentTile().playEvents(this);
                mc.possibleNextTile = null;
            } else if (PMD.isKeyPressedTimeSensitive(Key.p)) {
                Controller.turnsPaused = !Controller.turnsPaused;
            } else if (PMD.isKeyPressed(Key.r)) {
                //PMD.screen.game.setScreen(PMD.endScreen);
                for (Entity entity : floor.getEntities()) {
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
                attacking = true;
            }
        }
    }

    @Override
    public String toString() {
        return super.toString() + " player";
    }

    public void reset(){
        super.reset();
        tc.setTurnState(Turn.WAITING);
    }
}
