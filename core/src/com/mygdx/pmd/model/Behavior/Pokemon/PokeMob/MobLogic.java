package com.mygdx.pmd.model.Behavior.Pokemon.PokeMob;

import com.mygdx.pmd.PMD;
import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.exceptions.PathFindFailureException;
import com.mygdx.pmd.model.Behavior.*;
import com.mygdx.pmd.model.Behavior.Pokemon.*;
import com.mygdx.pmd.model.Entity.*;
import com.mygdx.pmd.model.Entity.Pokemon.PokemonMob;

/**
 * Created by Cameron on 1/20/2017.
 */
public class MobLogic implements Logic {
    private PokemonMob mob;

    public MobLogic(PokemonMob mob) {
        this.mob = mob;
    }

    @Override
    public void execute() {
        if (mob.getHP() <= 0) {
            mob.shouldBeDestroyed = true;
        }

        if(mob.shouldBeDestroyed) return;

        //ensure that when this runs the pokemon's turn is always waiting
        if (canAct()) {
            //make sure that if the pokemon is moving, it's turn will be set to complete and the algorithm will no longer run
         /*   if (!mob.equals(mob.getCurrentTile())) {
                mob.setTurnState(Turn.COMPLETE);
                return;
            }*/

            //will turn to face the player if the mob is aggressive
            if (mob.isAggressive()) {
                mob.setDirection(mob.target.getCurrentTile());
                mob.setFacingTile(mob.getDirection());

                if (mob.target.shouldBeDestroyed) {
                    mob.target = mob.floor.getPlayer();
                    mob.aggression = Aggression.passive;
                    mob.pathFind = mob.wander;
                }
            }

            if (mob.canAttack()) {
                mob.instructions.add(new AttackInstruction(mob));

                mob.setTurnState(Turn.PENDING);
                mob.setActionState(Action.ATTACKING);

                return;
            }
            else
            if (mob.canMove()) {
                if (mob.isForcedMove) {

                    mob.setSpeed(1);
                    mob.isForcedMove = false;
                } else {
                    if (mob.isAggressive()) {
                        mob.pathFind = mob.sPath;
                    }
                    //check to see if it can pathfind
                    if (pathFind()) {
                        if (mob.isWithinRange(mob.floor.getPlayer())) {
                            mob.setSpeed(1);
                        } else {
                            mob.setSpeed(25);
                        }
                    }
                }

                if (PMD.isKeyPressed(Key.s)) {
                    mob.setSpeed(5);
                }

                mob.instructions.add(new MoveInstruction(mob, mob.getNextTile()));
                mob.setDirection(mob.getNextTile());
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

    private boolean canAct(){
        return mob.getTurnState() == Turn.WAITING && mob.getActionState() == Action.IDLE && mob.instructions.isEmpty() && mob.currentInstruction == Entity.NO_INSTRUCTION;
    }
}
