package com.mygdx.pmd.model.instructions;

import com.mygdx.pmd.model.Entity.*;
import com.mygdx.pmd.model.Entity.Pokemon.*;
import com.mygdx.pmd.model.components.*;

/**
 * Created by Cameron on 5/5/2017.
 */
public class RandomizeLocationInstruction implements Instruction {

    private PositionComponent pc;
    private Pokemon pokemon;
    private boolean isFinished;

    //TODO generalize this class

    public RandomizeLocationInstruction(Pokemon pokemon){
        this.pc = pokemon.getComponent(PositionComponent.class);
        this.pokemon = pokemon;
    }
    @Override
    public void onInit() {

    }

    @Override
    public void onFinish() {

    }

    @Override
    public void execute() {
        pokemon.randomizeLocation();
        isFinished = true;
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }
}
