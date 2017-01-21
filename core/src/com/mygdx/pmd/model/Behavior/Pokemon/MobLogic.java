package com.mygdx.pmd.model.Behavior.Pokemon;

import com.mygdx.pmd.enumerations.Action;
import com.mygdx.pmd.enumerations.Turn;
import com.mygdx.pmd.exceptions.PathFindFailureException;
import com.mygdx.pmd.model.Behavior.Pokemon.PokeMob.MobAttackLogicBehavior;
import com.mygdx.pmd.model.Entity.Pokemon.PokemonMob;

/**
 * Created by Cameron on 1/20/2017.
 */
public class MobLogic extends PokemonBehavior {
    PokemonMob pMob;
    public MobLogic(PokemonMob pMob) {
        super(pMob);
        this.pMob = pMob;
    }

    @Override
    public void execute() {
        if(this.pMob.turnState != Turn.WAITING || !pMob.equals(pMob.currentTile)) {
            pMob.turnState = Turn.COMPLETE;
            return;
        }

        if(pMob.canAttack()){
            pMob.turnState = Turn.PENDING;
            pMob.setActionState(Action.ATTACKING);

            pMob.behaviors[2] = pMob.attackBehavior;
            pMob.attackBehavior.resetAttack();
            return;
        }

        if(pMob.canMove()) { //TODO can fix stuttering here by changing when the action state is set
            pMob.turnState = Turn.COMPLETE;
            pMob.setActionState(Action.MOVING);
            pMob.behaviors[2] = pMob.moveBehavior;

            pathFind();
            fillerNameHere();
        }
/*
        this.pMob.behaviors[0] = this.pMob.noBehavior;
*/
    }


    public boolean pathFind(){
        try {
            pMob.path = pMob.pathFind.pathFind(controller.pokemonPlayer.nextTile);
            //solutionNodeList = shortestPath.pathFind(controller.pokemonPlayer.nextTile);
        } catch (PathFindFailureException e) {
            System.out.println("Failed to pathfind");
        }

        if (pMob.path.size <= 0) {
            return false;
        }
        return true;
    }

    public boolean fillerNameHere() {
        //TODO Rename this filler name
        if(pMob.path.size == 0) return false;

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
