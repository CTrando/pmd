package com.mygdx.pmd.Model.Behavior.Projectile;

import com.mygdx.pmd.Model.Entity.Entity;
import com.mygdx.pmd.Model.Entity.Projectile.Projectile;

/**
 * Created by Cameron on 11/17/2016.
 */
public class ProjectileCollisionLogicBehavior extends ProjectileBehavior {
    public ProjectileCollisionLogicBehavior(Projectile projectile) {
        super(projectile);
    }

    @Override
    public void execute() {
        if(projectile.currentTile == null) return;

        if(projectile.currentTile.hasEntity() && !projectile.isAttackFinished){
            for(Entity entity: projectile.currentTile.getEntityList()) {
                entity.takeDamage(1);
            }
            projectile.takeDamage(1);
            projectile.isAttackFinished = true;
        }
    }
}
