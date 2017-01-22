package com.mygdx.pmd.model.Behavior.Pokemon.PokeMob;

import com.mygdx.pmd.enumerations.Action;
import com.mygdx.pmd.enumerations.Move;
import com.mygdx.pmd.enumerations.Turn;
import com.mygdx.pmd.exceptions.PathFindFailureException;
import com.mygdx.pmd.model.Behavior.Pokemon.PokemonBehavior;
import com.mygdx.pmd.model.Entity.Pokemon.PokemonMob;

/**
 * Created by Cameron on 1/20/2017.
 */
public class MobLogic extends PokemonBehavior {
    private PokemonMob pMob;

    public MobLogic(PokemonMob pMob) {
        super(pMob);
        this.pMob = pMob;
    }

    @Override
    public void execute() {
        //ensure that when this runs the pokemon's turn is always waiting
        if(pMob.turnState != Turn.WAITING) return;

        //make sure that if the pokemon is moving, it's turn will be set to complete and the algorithm will no longer run
        if (!pMob.equals(pMob.currentTile)) {
            pMob.turnState = Turn.COMPLETE;
            return;
        }

        if (pMob.canAttack()) {
            pMob.attack(Move.SCRATCH);
            pMob.turnState = Turn.PENDING;
            pMob.setActionState(Action.ATTACKING);

            pMob.behaviors[2] = pMob.attackBehavior;
            return;
        }

        if (pMob.canMove()) {
            pMob.turnState = Turn.COMPLETE;

            if(pMob.isForcedMove){
                pMob.setActionState(Action.MOVING);
                pMob.behaviors[2] = pMob.moveBehavior;
                pMob.isForcedMove = false;
            }
            else
            //check to see if it can pathfind
            if (pathFind()) {
                pMob.setActionState(Action.MOVING);
                pMob.behaviors[2] = pMob.moveBehavior;
                //TODO replace filler name
            }
        }
    }


    private boolean pathFind() {
        try {
            pMob.path = pMob.pathFind.pathFind(controller.pokemonPlayer.nextTile);
            //solutionNodeList = shortestPath.pathFind(controller.pokemonPlayer.nextTile);
        } catch (PathFindFailureException e) {
            System.out.println("Failed to pathfind");
        }

        if (pMob.path.size <= 0) {
            return false;
        }

        this.pMob.possibleNextTile = pMob.path.first();
        pMob.path.removeValue(this.pMob.possibleNextTile, true);

        if (this.pMob.isLegalToMoveTo(this.pMob.possibleNextTile)) {
            this.pMob.setDirectionBasedOnTile(this.pMob.possibleNextTile);

            this.pMob.setNextTile(this.pMob.possibleNextTile);
            this.pMob.possibleNextTile = null;
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
