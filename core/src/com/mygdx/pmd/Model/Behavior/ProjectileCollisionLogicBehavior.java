package com.mygdx.pmd.Model.Behavior;

import com.mygdx.pmd.utils.Entity;
import com.mygdx.pmd.utils.Projectile;

/**
 * Created by Cameron on 11/17/2016.
 */
public class ProjectileCollisionLogicBehavior extends ProjectileBehavior {
    public ProjectileCollisionLogicBehavior(Projectile projectile) {
        super(projectile);
    }

    @Override
    public void execute() {
        if(projectile.currentTile.hasAPokemon()){
            projectile.currentTile.getCurrentPokemon().takeDamage(1);
            projectile.takeDamage(1);
            projectile.isAttackFinished = true;
        }
    }
}
