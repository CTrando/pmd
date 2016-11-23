package com.mygdx.pmd.Model.Entity.Pokemon;

import com.mygdx.pmd.Controller.Controller;
import com.mygdx.pmd.Enumerations.*;
import com.mygdx.pmd.Model.Behavior.*;
import com.mygdx.pmd.Model.Behavior.Entity.AnimationBehavior;
import com.mygdx.pmd.Model.Behavior.Entity.MoveSlowBehavior;
import com.mygdx.pmd.Model.Behavior.Pokemon.PokePlayer.PlayerInputBehavior;
import com.mygdx.pmd.Model.Behavior.Pokemon.PokePlayer.PlayerMovementLogicBehavior;

public class PokemonPlayer extends Pokemon {

    public PokemonPlayer(Controller controller, int x, int y, PokemonName pokemonName) {
        super(controller, x, y, pokemonName);
        this.turnState = Turn.WAITING;

        behaviors[BaseBehavior.INPUT_BEHAVIOR] = new PlayerInputBehavior(this);
        behaviors[BaseBehavior.LOGIC_BEHAVIOR] = new PlayerMovementLogicBehavior(this);
        behaviors[BaseBehavior.MOVE_BEHAVIOR] = new MoveSlowBehavior(this);
        behaviors[BaseBehavior.ANIMATION_BEHAVIOR] = new AnimationBehavior(this);
    }
}
