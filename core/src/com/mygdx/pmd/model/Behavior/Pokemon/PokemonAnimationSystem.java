package com.mygdx.pmd.model.Behavior.Pokemon;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.mygdx.pmd.model.Behavior.*;
import com.mygdx.pmd.model.Entity.Pokemon.*;

import static com.mygdx.pmd.enumerations.Action.ATTACKING;
import static com.mygdx.pmd.enumerations.Action.MOVING;
import static com.mygdx.pmd.model.Behavior.Mappers.am;

/**
 * Created by Cameron on 11/8/2016.
 */
public class PokemonAnimationSystem extends IteratingSystem {

    public PokemonAnimationSystem(Pokemon pokemon){
        super(Family.all(ActionComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

    }
}
