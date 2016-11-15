package com.mygdx.pmd.Model.Pokemon;

import com.mygdx.pmd.Controller.Controller;
import com.mygdx.pmd.Enumerations.*;
import com.mygdx.pmd.Model.Behavior.*;

public class PokemonPlayer extends Pokemon {

    public PokemonPlayer(Controller controller, int x, int y, PokemonName pokemonName) {
        super(controller, x, y, pokemonName);
        this.turnState = Turn.WAITING;

        behaviors[Behavior.INPUT_BEHAVIOR] = new InputBehavior(this);
        behaviors[Behavior.LOGIC_BEHAVIOR] = new PlayerMovementLogicBehavior(this);
        behaviors[Behavior.MOVE_BEHAVIOR] = new MoveSlowBehavior(this);
        behaviors[Behavior.ANIMATION_BEHAVIOR] = new AnimationBehavior(this);
    }
}
