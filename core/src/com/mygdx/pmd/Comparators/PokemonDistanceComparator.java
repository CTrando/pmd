package com.mygdx.pmd.Comparators;

import com.mygdx.pmd.Model.Pokemon.Pokemon;

import java.util.Comparator;

/**
 * Created by Cameron on 9/7/2016.
 */
public class PokemonDistanceComparator implements Comparator<Pokemon> {

    Pokemon mainPokemon;

    public PokemonDistanceComparator(Pokemon mainPokemon)
    {
        this.mainPokemon = mainPokemon;
    }


    @Override
    public int compare(Pokemon o1, Pokemon o2) {

        if(this.mainPokemon.getCurrentTile().calculateDistance(o1.getCurrentTile()) < this.mainPokemon.getCurrentTile().calculateDistance(o2.getCurrentTile()))
            return -1;
        else if(this.mainPokemon.getCurrentTile().calculateDistance(o1.getCurrentTile()) > this.mainPokemon.getCurrentTile().calculateDistance(o2.getCurrentTile()))
            return 1;
        else return 0;
    }
}
