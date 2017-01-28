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
        if(mob.turnState != Turn.WAITING) return;

        //make sure that if the pokemon is moving, it's turn will be set to complete and the algorithm will no longer run
        if (!mob.equals(mob.currentTile)) {
            mob.turnState = Turn.COMPLETE;
            return;
        }

        if (mob.canAttack()) {
            mob.attack(Move.SCRATCH);
            mob.turnState = Turn.PENDING;
            mob.setActionState(Action.ATTACKING);

            mob.behaviors[2] = mob.attackBehavior;
            return;
        }

        if (mob.canMove()) {
            mob.turnState = Turn.COMPLETE;

            if(mob.isForcedMove){
                mob.setActionState(Action.MOVING);
                mob.behaviors[2] = mob.moveBehavior;
                mob.isForcedMove = false;
            }
            else
            //check to see if it can pathfind
            if (pathFind()) {
                mob.setActionState(Action.MOVING);
                mob.behaviors[2] = mob.moveBehavior;
                //TODO replace filler name
            }

            if (controller.isKeyPressed(Key.s)) {
                mob.moveBehavior.setSpeed(5);
            } else mob.moveBehavior.setSpeed(1);
        }
    }


    private boolean pathFind() {
        try {
            mob.path = mob.pathFind.pathFind(controller.pokemonPlayer.getNextTile());
            //solutionNodeList = shortestPath.pathFind(controller.pokemonPlayer.nextTile);
        } catch (PathFindFailureException e) {
            System.out.println("Failed to pathfind");
        }

        if (mob.path.size <= 0) {
            return false;
        }

        this.mob.possibleNextTile = mob.path.first();
        mob.path.removeValue(this.mob.possibleNextTile, true);

        if (this.mob.isLegalToMoveTo(this.mob.possibleNextTile)) {
            this.mob.setDirectionBasedOnTile(this.mob.possibleNextTile);

            this.mob.setNextTile(this.mob.possibleNextTile);
            this.mob.possibleNextTile = null;
        } else {
            return false;
        }
        return true;
    }

    @Override
    public boolean canExecute() {
        return false;
    }
}
