package com.mygdx.pmd.model.Behavior;

import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.model.Entity.*;
import com.mygdx.pmd.model.Entity.Pokemon.*;

/**
 * Created by Cameron on 2/22/2017.
 */
public class AttackInstruction implements Instruction {
    private Pokemon pokemon;
    private Move move;
    private boolean isFinished;

    public AttackInstruction(Pokemon pokemon, Move move) {
        this.pokemon = pokemon;
        this.move = move;
    }

    public AttackInstruction(Pokemon pokemon) {
        this(pokemon, pokemon.getRandomMove());
    }

    @Override
    public void onInit() {
        pokemon.setActionState(Action.ATTACKING);
        pokemon.attack(move);
    }

    @Override
    public void onFinish() {
        pokemon.setTurnState(Turn.COMPLETE);
        pokemon.setActionState(Action.IDLE);

        pokemon.currentAnimation.clear();
        pokemon.children.clear();
    }


    @Override
    public void execute() {
        if(pokemon.currentAnimation.isFinished()) {
            for (Entity entity : pokemon.children) {
                if (entity.shouldBeDestroyed) {
                    isFinished = true;
                }
            }
        }
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }
}
