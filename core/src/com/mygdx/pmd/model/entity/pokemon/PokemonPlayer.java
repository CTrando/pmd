package com.mygdx.pmd.model.entity.pokemon;

import com.mygdx.pmd.enums.PokemonType;
import com.mygdx.pmd.model.components.CameraComponent;
import com.mygdx.pmd.model.components.InputControlledComponent;

public class PokemonPlayer extends Pokemon {

    public PokemonPlayer() {
        super(PokemonType.TREEKO);
        add(new InputControlledComponent());
        add(new CameraComponent());
    }

    @Override
    public String toString() {
        return String.format("PLayer: %s", super.toString());
    }
}
