/*
package com.mygdx.pmd.model.Behavior.Pokemon.PokeMob;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.systems.IteratingSystem;
import com.mygdx.pmd.PMD;
import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.exceptions.PathFindFailureException;
import com.mygdx.pmd.model.Behavior.*;
import com.mygdx.pmd.model.Behavior.Entity.*;
import com.mygdx.pmd.model.Entity.Pokemon.PokemonMob;

import javax.swing.text.Position;

*/
/**
 * Created by Cameron on 1/20/2017.
 *//*

public class MobLogicSystem extends IteratingSystem {
    private PokemonMob mob;

    private TurnComponent turnComponent;
    private ActionComponent actionComponent;
    private PositionComponent positionComponent;
    private HPComponent hpComponent;

    public MobLogicSystem() {
        super(Family.all(TurnComponent.class, ActionComponent.class, PositionComponent.class, HPComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        if (hpComponent.hp <= 0) {
            mob.shouldBeDestroyed = true;
        }

        if(mob.shouldBeDestroyed) return;

        //ensure that when this runs the pokemon's turn is always waiting
        if (turnComponent.isTurnWaiting()) {
            //make sure that if the pokemon is moving, it's turn will be set to complete and the algorithm will no longer run
            if (!mob.equals(mob.getCurrentTile())) {
                mob.setTurnState(Turn.COMPLETE);
                return;
            }
            mob.setTurnState(Turn.COMPLETE);

            //will turn to face the player if the mob is aggressive
           */
/* if (mob.isAggressive()) {
                mob.setFacingTile(mob.target.getCurrentTile());
                mob.setFacingTile(mob.direction);

                if (mob.target.shouldBeDestroyed) {
                    mob.target = floor.getPlayer();
                    mob.aggression = Aggression.passive;
                    mob.pathFind = mob.wander;
                }
            }*//*

            mob.pathFind = mob.wander;

            if (mob.canAttack()) {
                mob.attack();

                mob.setTurnState(Turn.PENDING);
                mob.setActionState(Action.ATTACKING);

                //mob.behaviors[2] = mob.attackBehavior;
                return;
            }

            if (mob.canMove()) {
                MoveComponent moveComponent = (MoveComponent) mob.getComponent(Component.MOVE);
                if (moveComponent.isForcedMove) {
                    mob.setActionState(Action.MOVING);

                    mob.setSpeed(1);
                    //mob.behaviors[2] = entity.moveBehavior;
                    moveComponent.isForcedMove = false;
                } else {
                    */
/*if (mob.isAggressive()) {
                        mob.pathFind = mob.sPath;
                    }*//*

                    //check to see if it can pathfind
                    if (pathFind()) {
                        if (mob.isWithinRange(mob.floor.getPlayer())) {
                            mob.setActionState(Action.MOVING);
                            //mob.behaviors[2] = entity.moveBehavior;
                            mob.setSpeed(1);

                            if(mob.componentExists(Component.DIRECTION)) {
                                DirectionSystem directionComponent = (DirectionSystem) mob.getComponent(Component.DIRECTION);

                                directionComponent.setFacingTile(mob.getNextTile());
                            }
                        } else {
                            //mob.behaviors[2] = entity.moveBehavior;
                            mob.setSpeed(25);
                            mob.setActionState(Action.IDLE);
                        }
                    }
                }

                if (PMD.isKeyPressed(Key.s)) {
                    mob.setSpeed(5);
                } else mob.setSpeed(1);
                mob.setTurnState(Turn.COMPLETE);
            }
        }
    }

    private boolean pathFind() {
        try {
            mob.path = mob.pathFind.pathFind(mob.target.getNextTile());
        } catch (PathFindFailureException e) {
            System.out.println("Failed to pathfind");
        }

        if (mob.path.size <= 0) {
            return false;
        }

        this.mob.possibleNextTile = mob.path.first();
        mob.path.removeValue(this.mob.possibleNextTile, true);

        if (this.mob.isLegalToMoveTo(this.mob.possibleNextTile)) {
            this.mob.setNextTile(this.mob.possibleNextTile);
            this.mob.possibleNextTile = null;
        } else {
            return false;
        }
        return true;
    }
}
*/
