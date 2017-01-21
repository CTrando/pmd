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
        if(!this.canExecute()) return;

        switch(pMob.getActionState()) {
            case MOVING:
                pMob.currentAnimation = pMob.animationMap.get(pMob.direction.toString());
                pMob.currentSprite = pMob.animationMap.get(pMob.direction.toString()).getCurrentSprite();
                break;
            case ATTACKING:
                pMob.currentAnimation = pMob.animationMap.get(pMob.direction.toString() + "attack");
                pMob.currentSprite = pMob.animationMap.get(pMob.direction.toString() + "attack").getCurrentSprite();
                break;
            case IDLE:
                if(pMob.currentAnimation != null)
                    pMob.currentAnimation.clear();
                pMob.currentAnimation = pMob.animationMap.get("noanimation");
                pMob.currentSprite = pMob.animationMap.get(pMob.direction.toString()).finalSprite;
                pMob.animationMap.get(pMob.direction.toString()).clear();
        }
    }

    @Override
    public boolean canExecute() {
        return true;
    }


}
