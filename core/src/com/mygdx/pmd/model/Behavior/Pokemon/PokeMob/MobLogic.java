package com.mygdx.pmd.model.Behavior.Pokemon.PokeMob;

import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.exceptions.PathFindFailureException;
import com.mygdx.pmd.model.Behavior.Pokemon.PokemonBehavior;
import com.mygdx.pmd.model.Entity.Pokemon.PokemonMob;

/**
 * Created by Cameron on 1/20/2017.
 */
public class MobLogic extends PokemonBehavior {
    private PokemonMob mob;

    public MobLogic(PokemonMob mob) {
        super(mob);
        this.mob = mob;
    }

    @Override
    public void execute() {
        //ensure that when this runs the pokemon's turn is always waiting
        if (mob.isTurnWaiting()) {
            //make sure that if the pokemon is moving, it's turn will be set to complete and the algorithm will no longer run
            if (!mob.equals(mob.getCurrentTile())) {
                mob.setTurnState(Turn.COMPLETE);
                return;
            }
            mob.setTurnState(Turn.COMPLETE);

            //will turn to face the player if the mob is aggressive
            if (mob.isAggressive()) {
                mob.setDirectionBasedOnTile(mob.target.getCurrentTile());
                mob.setFacingTileBasedOnDirection(mob.direction);

                if (mob.target.shouldBeDestroyed) {
                    mob.target = controller.pokemonPlayer;
                    mob.aggression = Aggression.passive;
                    mob.pathFind = mob.wander;
                }
            }

            if (mob.canAttack()) {
                mob.attack(Move.SCRATCH);
                mob.setTurnState(Turn.PENDING);
                mob.setActionState(Action.ATTACKING);

                mob.behaviors[2] = mob.attackBehavior;
                return;
            }

            if (mob.canMove()) {
                if (mob.isForcedMove) {
                    mob.setActionState(Action.MOVING);

                    mob.setSpeed(1);
                    mob.behaviors[2] = mob.moveBehavior;
                    mob.isForcedMove = false;
                } else {
                    if (mob.isAggressive()) {
                        mob.pathFind = mob.sPath;
                    }
                    //check to see if it can pathfind
                    if (pathFind()) {
                        if (mob.isWithinRange(controller.pokemonPlayer)) {
                            mob.setActionState(Action.MOVING);
                            mob.behaviors[2] = mob.moveBehavior;
                            mob.setSpeed(1);
                        } else {
                            mob.behaviors[2] = mob.moveBehavior;
                            mob.setSpeed(25);
                            mob.setActionState(Action.IDLE);
                        }
                    }
                }

                if (controller.isKeyPressed(Key.s)) {
                    mob.setSpeed(5);
                }

                mob.setDirectionBasedOnTile(mob.getNextTile());
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
