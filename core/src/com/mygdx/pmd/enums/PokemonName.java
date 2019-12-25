package com.mygdx.pmd.enums;

import com.mygdx.pmd.utils.MathLogic;

/**
 * Created by Cameron on 8/28/2016.
 */
public enum PokemonName {
    treeko, squirtle;

    public static PokemonName getRandomName(){
        return values()[MathLogic.random(0, values().length-1)];
    }
}
