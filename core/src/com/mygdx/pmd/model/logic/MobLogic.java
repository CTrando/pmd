package com.mygdx.pmd.model.logic;

import com.mygdx.pmd.PMD;
import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.exceptions.PathFindFailureException;
import com.mygdx.pmd.model.Entity.*;
import com.mygdx.pmd.model.Entity.Pokemon.PokemonMob;
import com.mygdx.pmd.model.Tile.*;
import com.mygdx.pmd.model.components.*;
import com.mygdx.pmd.model.instructions.*;

/**
 * Created by Cameron on 1/20/2017.
 */
public class MobLogic extends PokemonLogic {
    private PokemonMob mob;
    private MoveComponent mc;
    private ActionComponent ac;
    private TurnComponent tc;

    public MobLogic(PokemonMob mob) {
        super(mob);
        this.mob = mob;
        this.mc = mob.mc;
        this.ac = mob.ac;
        this.tc = mob.tc;
    }

    @Override
    public void execute() {
        if (mob.getHP() <= 0) {
            mob.shouldBeDestroyed = true;
        }

        if (mob.shouldBeDestroyed) {
            return;
        }

        //ensure that when this runs the pokemon's turn is always waiting
        if (canAct()) {
            //will turn to face the player if the mob is aggressive
            if (mob.isAggressive()) {
                mob.setDirection(mob.target.getCurrentTile());
                mc.setFacingTile(mob.target.dc.getDirection());

                if (mob.target.shouldBeDestroyed) {
                    mob.target = mob.floor.getPlayer();
                    mob.aggression = Aggression.passive;
                    mob.pathFind = mob.wander;
                }
            }

            if (canAttack()) {
                mob.resetMove();
                if (isEnemyAdjacent()) {
                    attack();
                    //return is used to prevent the mob from moving
                    return;
                } else {
                    Tile enemyTile = findEnemyTile();
                    Move rangedMove = mob.getRandomRangedMove();
                    int dist = enemyTile.dist(mob.getCurrentTile());

                    if (rangedMove != null && dist <= rangedMove.range) {
                        mob.setMove(mob.getRandomRangedMove());
                        attack();
                        return;
                    }
                }
            }

            if (canMove()) {
                move();
                tc.setTurnState(Turn.COMPLETE);
                return;
            }
        }
    }

    private void attack(Move move) {
        mob.instructions.add(new AttackInstruction(mob, move));
        tc.setTurnState(Turn.PENDING);
    }

    @Override
    void attack() {
        if (mob.getMove() == null) {
            attack(mob.getRandomMove());
        } else {
            attack(mob.getMove());
        }
    }

    boolean canAttack() {
        return mob.canSeeEnemy() && mob.getAggression() == Aggression.aggressive;
    }

    @Override
    void move() {
        // set the next tile based on if the mob has been forced to move or not
        if (mob.isForcedMove) {
            System.out.println("forced move");
            mc.setSpeed(1);
            mob.isForcedMove = false;
        } else {
            if (mob.isAggressive()) {
                mob.pathFind = mob.sPath;
            }
            //see if it can pathfind, meaning there was no error thrown
            if (pathFind()) {
                mc.setFacingTile(mc.possibleNextTile);

                //this method depends on current tile not move component
                this.mob.setDirection(mc.possibleNextTile);

                if (this.mob.isLegalToMoveTo(mc.possibleNextTile)) {
                    mc.setNextTile(mc.possibleNextTile);
                    mc.possibleNextTile = null;
                }

            }
        }
        dc.setDirection(mc.getNextTile());
        this.determineSpeed();
        //tell the mob to go to to the next tile
        mob.instructions.add(new MoveInstruction(mob, mc.getNextTile()));
    }

    @Override
    boolean canMove() {
        return tc.getTurnState() == Turn.WAITING;
    }

    private void determineSpeed() {
        if (mob.isWithinRange(mob.floor.getPlayer())) {
            mc.setSpeed(1);
            if(PMD.isKeyPressed(Key.s)) {
                mc.setSpeed(5);
            }
        } else {
            mc.setSpeed(25);
        }
    }

    private boolean pathFind() {
        try {
            mob.path = mob.pathFind.pathFind(mob.target.mc.getNextTile());
        } catch (PathFindFailureException e) {
            System.out.println("Failed to pathfind");
        }

        if (mob.path.size <= 0) {
            return false;
        }

        mc.possibleNextTile = mob.path.first();
        mob.path.removeValue(mc.possibleNextTile, true);

        return true;
    }

    @Override
    boolean canAct() {
        return tc.getTurnState() == Turn.WAITING && ac.getActionState() == Action.IDLE && mob.instructions.isEmpty()
                && mob.currentInstruction == Entity.NO_INSTRUCTION;
    }
}
