package com.mygdx.pmd.model.components;

import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.model.Entity.*;
import com.mygdx.pmd.model.Entity.Pokemon.*;

/**
 * Created by Cameron on 4/20/2017.
 */
public class MobCombatComponent extends CombatComponent {
    PokemonMob mob;
    public MobCombatComponent(PokemonMob entity) {
        super(entity);
        this.mob = entity;
    }

    @Override
    public void takeDamage(Entity damager, int damage){
        super.takeDamage(damager, damage);
        mob.cc.setAggressionState(Aggression.aggressive);
        mob.target = damager;
    }
}
