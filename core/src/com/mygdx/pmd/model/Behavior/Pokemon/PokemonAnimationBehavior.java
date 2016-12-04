package com.mygdx.pmd.model.Behavior.Pokemon;

import com.mygdx.pmd.model.Entity.Pokemon.Pokemon;

/**
 * Created by Cameron on 11/8/2016.
 */
public class PokemonAnimationBehavior extends PokemonBehavior {

    public PokemonAnimationBehavior(Pokemon pokemon){
        super(pokemon);
    }

    @Override
    public void execute() {
        switch(pokemon.getActionState()) {
            case MOVING:
                pokemon.currentAnimation = pokemon.animationMap.get(pokemon.direction.toString());
                pokemon.currentSprite = pokemon.animationMap.get(pokemon.direction.toString()).getCurrentSprite();
                break;
            case ATTACKING:
                pokemon.currentAnimation = pokemon.animationMap.get(pokemon.direction.toString() + "attack");
                pokemon.currentSprite = pokemon.animationMap.get(pokemon.direction.toString() + "attack").getCurrentSprite();
                break;
            case IDLE:
                if(pokemon.currentAnimation != null)
                    pokemon.currentAnimation.clear();
                pokemon.currentAnimation = pokemon.animationMap.get("noanimation");
                pokemon.currentSprite = pokemon.animationMap.get(pokemon.direction.toString()).finalSprite;
                pokemon.animationMap.get(pokemon.direction.toString()).clear();
        }
    }


}
