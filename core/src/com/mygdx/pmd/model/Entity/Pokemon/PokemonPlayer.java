package com.mygdx.pmd.model.Entity.Pokemon;

import com.mygdx.pmd.model.components.*;

public class PokemonPlayer extends Pokemon {

    public PokemonPlayer() {
        super(PokemonType.TREECKO);
        add(new PlayerControlledComponent());
    }

    @Override
    public String toString() {
        return String.format("PLayer: %s", super.toString());
    }
}
