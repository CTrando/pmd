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

        if(this.distanceFormula(mainPokemon.x, mainPokemon.y, o1.x, o1.y) < this.distanceFormula(mainPokemon.x, mainPokemon.y, o2.x, o2.y))
            return -1;
        else if(this.distanceFormula(mainPokemon.x, mainPokemon.y, o1.x, o1.y) > this.distanceFormula(mainPokemon.x, mainPokemon.y, o2.x, o2.y))
            return 1;
        else return 0;
    }

    public double distanceFormula(int x1, int y1, int x2, int y2){
        return Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
    }
}
