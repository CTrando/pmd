package com.mygdx.pmd.model.Behavior.Pokemon;

import com.mygdx.pmd.model.Behavior.*;
import com.mygdx.pmd.model.Entity.Pokemon.*;

import static com.mygdx.pmd.enumerations.Action.MOVING;

/**
 * Created by Cameron on 11/8/2016.
 */
public class PokemonAnimationComponent extends Component {

    public PokemonAnimationComponent(Pokemon pokemon){
        super(pokemon);
    }

    @Override
    public void update() {
        switch(entity.getActionState()) {
            case MOVING:
                entity.currentAnimation = entity.animationMap.get(entity.direction.toString());
                //pMob.currentSprite = pMob.animationMap.get(pMob.direction.toString()).getCurrentSprite();
                break;
            case ATTACKING:
                entity.currentAnimation = entity.animationMap.get(entity.direction.toString() + "attack");
                break;
            case IDLE:
             /*   if(pMob.currentAnimation != null)
                    pMob.currentAnimation.clear();*/
                entity.currentAnimation = entity.animationMap.get(entity.direction.toString() + "idle");
                //pMob.currentSprite = pMob.animationMap.get(pMob.direction.toString()).finalSprite;
        }
        entity.currentSprite = entity.currentAnimation.getCurrentSprite();
    }
}
