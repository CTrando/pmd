package com.mygdx.pmd.model.Entity.Pokemon;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Sound;
import com.mygdx.pmd.PMD;
import com.mygdx.pmd.controller.Controller;
import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.model.Behavior.Pokemon.PokePlayer.*;
import com.mygdx.pmd.model.Entity.*;
import com.mygdx.pmd.model.Floor.*;
import com.mygdx.pmd.utils.Constants;


public class PokemonPlayer extends Pokemon {

    public PokemonPlayer(Floor floor, PokemonName name){
        this(floor, 0, 0, name);
    }

    public PokemonPlayer(Floor floor, int x, int y, PokemonName pokemonName) {
        super(floor, x, y, pokemonName);
        /*this.setTurnState(Turn.WAITING);
        //this.aggression = Aggression.passive;*/
    }

  /*  @Override
    public void registerObservers() {
        observers[0] = new MovementObserver(this);
    }

    @Override
    public void randomizeLocation() {
        super.randomizeLocation();
        this.setTurnState(Turn.WAITING);
    }

    @Override
    public void dispose() {
        super.dispose();

        this.setTurnState(Turn.COMPLETE);
        floor.getScreen().game.switchScreen(PMD.endScreen);

        System.out.println("WOE IS ME I AM DEAD");

        //PMD.manager.get("sfx/background.ogg", Music.class).play();
    }

    *//**
     * @return true if the pokemon has a currentMove available, false if not
     *//*
    public boolean canAttack() {
        return currentMove != null;
    }
*/
    @Override
    public String toString() {
        return super.toString() + " player";
    }
}
