package com.mygdx.pmd.model.logic;

import com.mygdx.pmd.PMD;
import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.exceptions.PathFindFailureException;
import com.mygdx.pmd.model.Entity.*;
import com.mygdx.pmd.model.Entity.Pokemon.PokemonMob;
import com.mygdx.pmd.model.Tile.*;
import com.mygdx.pmd.model.components.*;
import com.mygdx.pmd.model.instructions.*;
import com.mygdx.pmd.utils.AI.BFS;
import com.mygdx.pmd.utils.Constants;

/**
 * Created by Cameron on 1/20/2017.
 */
public class MobLogic extends PokemonLogic {
    private PokemonMob mob;
    private boolean skipTurn;
    private BFS bfs;

    public MobLogic(PokemonMob mob) {
        super(mob);
        this.mob = mob;
        mob.pathFind = mob.wander;
        this.bfs = new BFS(mob);
    }

    @Override
    public void execute() {
        if(checkDestroyed()) return;

        //TODO this method is too long
        if (mob.mc.isForcedMove()) {
            System.out.println("forced move");

            anc.setCurrentAnimation(dc.getDirection().toString());
            tc.setTurnState(Turn.COMPLETE);
            determineSpeed();
            skipTurn = true;
            return;
        }

        /*
        TODO if it is close to player then start caring whether it can act, otherwise just set its turn state to
        complete anyways
        ensure that when this runs the pokemon's turn is always waiting
        */
        if (canAct()) {
            //variable to skip turn //TODO make it a function instead
            if(skipTurn){
                skipTurn = false;
                tc.setTurnState(Turn.COMPLETE);
                return;
            }

            anc.setCurrentAnimation(dc.getDirection()+"idle");
            updateAggression();

            if (mob.cc.isAggressive()) {
                Entity target = mob.cc.getTarget();
                lookAt(target);
            }

            if (canAttack()) {
                mob.resetMove();
                if (isEnemyAdjacent()) {
                    attack();
                    return;
                } else {
                    Tile enemyTile = findEnemyTile();
                    Move rangedMove = mob.getRandomRangedMove();
                    int dist = enemyTile.dist(mob.pc.getCurrentTile());

                    if (rangedMove != null && dist <= rangedMove.range) {
                        mob.setMove(mob.getRandomRangedMove());
                        attack();
                    }
                    //if cannot attack then move instead so no return statement here
                }
            }

            if (canMove()) {
                setPathFindType();
                //see if no errors thrown
                if(pathFind()){
                    dc.setDirection(mc.possibleNextTile);
                    if (this.mob.isLegalToMoveTo(mc.possibleNextTile)) {
                        mc.setNextTile(mc.possibleNextTile);
                        mc.possibleNextTile = null;
                    }
                }
                move();
                return;
            }
        }
    }

    private boolean checkDestroyed(){
        if (mob.shouldBeDestroyed) {
            return true;
        }

        if (mob.cc.getHp() <= 0) {
            mob.shouldBeDestroyed = true;
            return true;
        }
        return false;
    }

    private void lookAt(Entity target){
        PositionComponent targetPC = target.getComponent(PositionComponent.class);

        dc.setDirection(targetPC.getCurrentTile());
        mc.setFacingTile(dc.getDirection());
    }

    private void updateAggression(){
        if (mob.cc.getTarget() != null && mob.cc.getTarget().shouldBeDestroyed) {
            mob.cc.setTarget(null);
            mob.cc.setAggressionState(Aggression.passive);
            mob.pathFind = mob.wander;
        }
    }

    private void attack(Move move) {
        tc.setTurnState(Turn.PENDING);
        mob.instructions.add(new AnimateInstruction(mob, dc.getDirection()+"attack"));
        mob.instructions.add(new AttackInstruction(mob, move));
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
        return mob.canSeeEnemy() && mob.cc.isAggressive();
    }

    private void setPathFindType(){
        if (mob.cc.isAggressive()) {
            mob.pathFind = bfs;
        }
    }

    @Override
    void move() {
        if (mc.getNextTile() != null && mc.getNextTile() != pc.getCurrentTile()) {
            mob.instructions.add(new MoveInstruction(mob, mc.getNextTile()));
            ac.setActionState(Action.MOVING);

            dc.setDirection(mc.getNextTile());
            anc.setCurrentAnimation(dc.getDirection().toString());
            this.determineSpeed();
        } else {
            ac.setActionState(Action.IDLE);
        }
        tc.setTurnState(Turn.COMPLETE);
    }

    @Override
    boolean canMove() {
        return tc.getTurnState() == Turn.WAITING;
    }

    private void determineSpeed() {
        if (mob.isWithinRange(mob.floor.getPlayer())) {
            mc.setSpeed(1);
            if (PMD.isKeyPressed(Key.s)) {
                //TODO fix this by using a variable or doing some trick math
                mc.setSpeed(Constants.TILE_SIZE/4);
            }
        } else {
            mc.setSpeed(Constants.TILE_SIZE);
        }
    }

    private boolean pathFind() {
        try {
            MoveComponent targetMC = mob.target.getComponent(MoveComponent.class);
            mob.path = mob.pathFind.pathFind(targetMC.getNextTile());
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
                && mob.currentInstruction == Entity.NO_INSTRUCTION && !mc.isForcedMove();
    }
}
