package com.mygdx.pmd.model.entity.pokemon;

import com.mygdx.pmd.enums.PokemonType;
import com.mygdx.pmd.model.components.CameraComponent;
import com.mygdx.pmd.model.components.InputControlledComponent;
import com.mygdx.pmd.model.components.PlayerComponent;

public class PokemonPlayer extends Pokemon {

    public PokemonPlayer() {
        super(PokemonType.TREEKO);
        add(new InputControlledComponent());
        add(new PlayerComponent());
        add(new CameraComponent());
    }

    @Override
    public String toString() {
        return String.format("Player: %s", super.toString());
    }
}
