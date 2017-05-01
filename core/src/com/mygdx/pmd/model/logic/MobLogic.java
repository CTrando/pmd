package com.mygdx.pmd.model.logic;

import com.badlogic.gdx.utils.Array;
import com.mygdx.pmd.PMD;
import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.exceptions.PathFindFailureException;
import com.mygdx.pmd.model.Entity.*;
import com.mygdx.pmd.model.Entity.Pokemon.PokemonMob;
import com.mygdx.pmd.model.Tile.*;
import com.mygdx.pmd.model.components.*;
import com.mygdx.pmd.model.instructions.*;
import com.mygdx.pmd.utils.Constants;
import javafx.geometry.Pos;

import java.util.Arrays;

/**
 * Created by Cameron on 1/20/2017.
 */
public class MobLogic extends PokemonLogic {
    private PokemonMob mob;
    private boolean skipTurn;

    public MobLogic(PokemonMob mob) {
        super(mob);
        this.mob = mob;
    }

    @Override
    public void execute() {
        if (mob.cc.getHp() <= 0) {
            mob.shouldBeDestroyed = true;
        }

        if (mob.shouldBeDestroyed) {
            return;
        }
        //too long

        if (mob.mc.isForcedMove()) {
            System.out.println("forced move");

            anc.setCurrentAnimation(dc.getDirection().toString());
            this.determineSpeed();
            tc.setTurnState(Turn.COMPLETE);
            skipTurn = true;
            return;
        }

        //ensure that when this runs the pokemon's turn is always waiting
        if (canAct()) {
            //variable to skip turn //TODO make it a function instead
            if(skipTurn){
                skipTurn = false;
                tc.setTurnState(Turn.COMPLETE);
                return;
            }
            if(tc.isTurnWaiting()) {
                bfs(mob.floor.getPlayer().getComponent(PositionComponent.class).getCurrentTile());
                return;
            }
            //will turn to face the player if the mob is aggressive
            if (mob.cc.isAggressive()) {
                PositionComponent targetPC = mob.target.getComponent(PositionComponent.class);
                DirectionComponent targetDC = mob.target.getComponent(DirectionComponent.class);

                dc.setDirection(targetPC.getCurrentTile());
                mc.setFacingTile(dc.getDirection());

                if (mob.target.shouldBeDestroyed) {
                    mob.target = mob.floor.getPlayer();
                    mob.cc.setAggressionState(Aggression.passive);
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
                    int dist = enemyTile.dist(mob.pc.getCurrentTile());

                    if (rangedMove != null && dist <= rangedMove.range) {
                        mob.setMove(mob.getRandomRangedMove());
                        attack();
                    }
                }
            }

            if (canMove()) {
                move();
                return;
            }
        }
    }

    private void bfs(Tile tile){
        tc.setTurnState(Turn.COMPLETE);
        mob.children.add(mob.pc.getCurrentTile());
        Array<Tile> newTiles = new Array<Tile>();
        //not while because I want it to run once
        for(Entity entity: mob.children){
            if(entity instanceof Tile){
                Tile wTile = (Tile) entity;
                Tile[][] tileBoard = wTile.tileBoard;
                int row = wTile.row;
                int col = wTile.col;

                if(!wTile.visited){
                    wTile.visited = true;
                    newTiles.add(tileBoard[row+1][col]);
                    newTiles.add(tileBoard[row-1][col]);
                    newTiles.add(tileBoard[row][col+1]);
                    newTiles.add(tileBoard[row][col-1]);
                }
            }
        }
        tween(newTiles);
        mob.children.addAll(newTiles);
    }

    private void tween(Array<Tile> tiles){
        Array<Tile> rm = new Array<Tile>();
        for(int i = 0; i< tiles.size; i++) {
            Tile tile = tiles.get(i);
            if(!tile.isWalkable || mob.children.contains(tile, true)){
                rm.add(tile);
            }
        }
        tiles.removeAll(rm, true);
    }

    private void attack(Move move) {
        tc.setTurnState(Turn.PENDING);
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

    @Override
    void move() {
        // set the next tile based on if the mob has been forced to move or not

        if (mob.cc.isAggressive()) {
            mob.pathFind = mob.sPath;
        }
        //see if it can pathfind, meaning there was no error thrown
        if (pathFind()) {
            //this method depends on current tile not move component
            dc.setDirection(mc.possibleNextTile);

            if (this.mob.isLegalToMoveTo(mc.possibleNextTile)) {
                mc.setNextTile(mc.possibleNextTile);
                mc.possibleNextTile = null;
            }
        }

        if (mc.getNextTile() != null && mc.getNextTile() != pc.getCurrentTile()) {
            mob.instructions.add(new MoveInstruction(mob, mc.getNextTile()));

            //actually need this
            ac.setActionState(Action.MOVING);

            dc.setDirection(mc.getNextTile());
            anc.setCurrentAnimation(dc.getDirection().toString());
            this.determineSpeed();
        } else {
            ac.setActionState(Action.IDLE);
        }
        tc.setTurnState(Turn.COMPLETE);
        //tell the mob to go to to the next tile
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
            // not one behind change back to movement component later
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
