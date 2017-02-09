package com.mygdx.pmd.model.Behavior.Pokemon;

import com.mygdx.pmd.model.Entity.Pokemon.*;

/**
 * Created by Cameron on 11/8/2016.
 */
public class PokemonAnimationBehavior extends PokemonBehavior {

    public PokemonAnimationBehavior(Pokemon pokemon){
        super(pokemon);
    }

    @Override
    public void execute() {
        switch(pMob.getActionState()) {
            case MOVING:
                pMob.currentAnimation = pMob.animationMap.get(pMob.direction.toString());
                //pMob.currentSprite = pMob.animationMap.get(pMob.direction.toString()).getCurrentSprite();
                break;
            case ATTACKING:
                pMob.currentAnimation = pMob.animationMap.get(pMob.direction.toString() + "attack");
                break;
            case IDLE:
             /*   if(pMob.currentAnimation != null)
                    pMob.currentAnimation.clear();*/
                pMob.currentAnimation = pMob.animationMap.get(pMob.direction.toString() + "idle");
                //pMob.currentSprite = pMob.animationMap.get(pMob.direction.toString()).finalSprite;
        }
        pMob.currentSprite = pMob.currentAnimation.getCurrentSprite();
    }
}
