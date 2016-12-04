package com.mygdx.pmd.model.Entity.Pokemon;

import com.mygdx.pmd.controller.Controller;
import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.model.Behavior.*;
import com.mygdx.pmd.model.Behavior.Pokemon.PokemonAnimationBehavior;
import com.mygdx.pmd.model.Behavior.Entity.MoveSlowBehavior;
import com.mygdx.pmd.model.Behavior.Pokemon.PokePlayer.PlayerInputBehavior;
import com.mygdx.pmd.model.Behavior.Pokemon.PokePlayer.PlayerMovementLogicBehavior;
import com.mygdx.pmd.utils.observers.MovementObserver;

public class PokemonPlayer extends Pokemon {

    public PokemonPlayer(Controller controller, int x, int y, PokemonName pokemonName) {
        super(controller, x, y, pokemonName);
        this.turnState = Turn.WAITING;
        this.registerObservers();

        behaviors[BaseBehavior.INPUT_BEHAVIOR] = new PlayerInputBehavior(this);
        behaviors[BaseBehavior.LOGIC_BEHAVIOR] = new PlayerMovementLogicBehavior(this);
        behaviors[BaseBehavior.MOVE_BEHAVIOR] = new MoveSlowBehavior(this);
        behaviors[BaseBehavior.ANIMATION_BEHAVIOR] = new PokemonAnimationBehavior(this);
    }

    @Override
    public void registerObservers(){
        observers[0] = new MovementObserver(this);
    }
}
