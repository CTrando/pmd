package com.mygdx.pmd.enumerations;

import com.mygdx.pmd.utils.PRandomInt;

/**
 * Created by Cameron on 8/28/2016.
 */
public enum PokemonName {
    treeko, squirtle;

    public static PokemonName getRandomName(){
        return values()[PRandomInt.random(0, values().length-1)];
    }
}
