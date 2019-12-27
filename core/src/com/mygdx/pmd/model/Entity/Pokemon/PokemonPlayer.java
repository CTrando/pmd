package com.mygdx.pmd.model.Entity.Pokemon;

import com.mygdx.pmd.enums.PokemonType;
import com.mygdx.pmd.model.components.PlayerControlledComponent;

public class PokemonPlayer extends Pokemon {

    public PokemonPlayer() {
        super(PokemonType.TREEKO);
        add(new PlayerControlledComponent());
    }

    @Override
    public String toString() {
        return String.format("PLayer: %s", super.toString());
    }
}
