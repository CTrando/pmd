package com.mygdx.pmd.model.Behavior.Pokemon.PokePlayer;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Sound;
import com.mygdx.pmd.PMD;
import com.mygdx.pmd.controller.Controller;
import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.model.Behavior.*;
import com.mygdx.pmd.model.Behavior.Entity.*;
import com.mygdx.pmd.model.Entity.*;
import com.mygdx.pmd.model.Entity.Pokemon.*;
import com.mygdx.pmd.utils.Constants;

import static sun.audio.AudioPlayer.player;

/**
 * Created by Cameron on 1/21/2017.
 */
public class PlayerLogicSystem extends IteratingSystem {

    public PlayerLogicSystem(){
        super(Family.all(PlayerControlledComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        HPComponent hp = Mappers.hp.get(entity);
        TurnComponent tm = Mappers.tm.get(entity);
        PositionComponent pm = Mappers.pm.get(entity);
        AttackComponent atm = Mappers.atm.get(entity);
        ActionComponent am = Mappers.am.get(entity);

        if (hp.hp <= 0) {
            hp.shouldBeDestroyed = true;
        }

        if (tm.isTurnWaiting() && pm.equals(pm.getCurrentTile())) {
            this.handleInput(entity);

            /*if (atm.canAttack()) {
                atm.attack(player.currentMove);
                atm.currentMove = null;

                am.setActionState(Action.ATTACKING);
                tm.setTurnState(Turn.PENDING);

                //player.behaviors[2] = player.attackBehavior;
            } else */

            if (true){//player.canMove()) {
                pm.setNextTile(pm.possibleNextTile);
                pm.possibleNextTile = null;

               /* if(player.componentExists(Component.DIRECTION)){
                    DirectionSystem directionComponent = (DirectionSystem) player.getComponent(Component.DIRECTION);
                    directionComponent.setFacingTile(player.getNextTile());
                }*/

               /* if (pm.getNextTile().hasEntity()) {
                    for (Entity entity : player.getNextTile().entities) {
                        if (entity != player) {
                            if(entity.componentExists(Component.MOVE)){
                                MoveComponent moveComponent = (MoveComponent) entity.getComponent(Component.MOVE);
                                moveComponent.forceMoveToTile(player.getCurrentTile());
                            }
                            entity.direction = player.direction.getOppositeDirection();
                        }
                    }
                }*/

                tm.setTurnState(Turn.COMPLETE);
                am.setActionState(Action.MOVING);

                //player.behaviors[2] = player.moveBehavior;

             /*   if (PMD.isKeyPressed(Key.s)) {
                    player.setSpeed(5);
                } else player.setSpeed(1);*/
            }

        } else if (am.getActionState() == Action.IDLE) {
            System.out.println(tm.getTurnState());
            System.out.println(am.getActionState());
        }
    }

    /*
        handles all input - including movement, attack and menus
     */
    public void handleInput(Entity entity) {
        HPComponent hp = Mappers.hp.get(entity);
        TurnComponent tm = Mappers.tm.get(entity);
        PositionComponent pm = Mappers.pm.get(entity);
        AttackComponent atm = Mappers.atm.get(entity);
        ActionComponent am = Mappers.am.get(entity);
        DirectionComponent dm = Mappers.dm.get(entity);


        /*
            -updates position first - makes action state idle
            -updates input next - so would be idle and would be able to take in input
            -updates animation last - meaning change from moving to idle would not be recorded
         */
        if (pm.equals(pm.getCurrentTile()) && am.getActionState() == Action.IDLE) {
            if (PMD.isKeyPressed(Key.shift)) {
                if (PMD.isKeyPressed(Key.down)) {
                    dm.direction = Direction.down;
                } else if (PMD.isKeyPressed(Key.left)) {
                    dm.direction = Direction.left;
                } else if (PMD.isKeyPressed(Key.right)) {
                    dm.direction = Direction.right;
                } else if (PMD.isKeyPressed(Key.up)) {
                    dm.direction = Direction.up;
                }
            } else {
                //code for setting the user's next tile
                try {
                    if (PMD.isKeyPressed(Key.down)) {
                        pm.possibleNextTile = (pm.tileBoard[pm.getCurrentTile().row - 1][pm.getCurrentTile().col]);
                    } else if (PMD.isKeyPressed(Key.left)) {
                        pm.possibleNextTile = (pm.tileBoard[pm.getCurrentTile().row][pm.getCurrentTile().col - 1]);
                    } else if (PMD.isKeyPressed(Key.right)) {
                        pm.possibleNextTile = (pm.tileBoard[pm.getCurrentTile().row][pm.getCurrentTile().col + 1]);
                    } else if (PMD.isKeyPressed(Key.up)) {
                        pm.possibleNextTile = (pm.tileBoard[pm.getCurrentTile().row + 1][pm.getCurrentTile().col]);
                    } else {
                        pm.possibleNextTile = (null);
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                }
            }
            //if the user hits K, he will not be able to currentMove, but he will be able to set his direction
            // set the direction based on key hit - note that one can only change directions and not currentMove when he is not moving
            //TODO switch keys to a refresh system - perhaps use an object instead of an enum
            if (PMD.isKeyPressed(Key.down)) {
                dm.direction = Direction.down;
            } else if (PMD.isKeyPressed(Key.left)) {
                dm.direction = Direction.left;
            } else if (PMD.isKeyPressed(Key.right)) {
                dm.direction = Direction.right;
            } else if (PMD.isKeyPressed(Key.up)) {
                dm.direction = Direction.up;
            }

         /*   //actions that do not affect the player's turn or action state
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
            }*/
        }
    }
}
