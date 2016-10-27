package com.mygdx.pmd.utils;

import com.badlogic.gdx.utils.Array;
import com.mygdx.pmd.Enumerations.AttackType;
import com.mygdx.pmd.Enumerations.PokemonName;
import com.mygdx.pmd.Model.Pokemon.Pokemon;

/**
 * Created by Cameron on 10/21/2016.
 */
public class Attack {
    private int counter;
    private String name;
    public PAnimation animation;
    private Pokemon pokemon;
    public AttackType type;
    public boolean animationFinish;

    public Attack(Pokemon pokemon, AttackType type) {
        this.pokemon = pokemon;
        this.name = pokemon.pokemonName;
        this.type = type;

        animation = pokemon.animationMap.get(pokemon.direction.toString() + "attack");
    }

    public void update(){
        if(isFinished())
            return;
        counter++;
        if(isFinished()) {
            animationFinish = false;
            switch(type) {
                case DIRECT: pokemon.controller.projectiles.add(new Projectile(pokemon.facingTile, pokemon));
                    break;
                case RANGED: pokemon.controller.projectiles.add(new Projectile(pokemon.direction, pokemon));
            }
        }
    }

    public boolean isFinished() {
        return counter>30;
    }
}
