package com.mygdx.pmd.model.entity.pokemon;

import com.mygdx.pmd.enums.PokemonType;
import com.mygdx.pmd.model.components.MobComponent;
import com.mygdx.pmd.model.components.PositionComponent;

public class PokemonMob extends Pokemon {

    public PokemonMob() {
        super(PokemonType.TREEKO);
        add(new MobComponent());
        add(new PositionComponent(1, 1));
    }

    @Override
    public String toString() {
        return String.format("Mob: %s", super.toString());
    }
}
