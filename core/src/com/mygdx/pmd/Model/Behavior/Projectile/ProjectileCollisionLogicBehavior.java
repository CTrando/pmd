package com.mygdx.pmd.model.Behavior.Projectile;

import com.mygdx.pmd.enumerations.Action;
import com.mygdx.pmd.model.Entity.Entity;
import com.mygdx.pmd.model.Entity.Projectile.Projectile;

/**
 * Created by Cameron on 11/17/2016.
 */
public class ProjectileCollisionLogicBehavior extends ProjectileBehavior {
    public ProjectileCollisionLogicBehavior(Projectile projectile) {
        super(projectile);
    }

    @Override
    public void execute() {
        if(projectile.shouldBeDestroyed) return;
        if(projectile.currentTile == null) return;

        if(projectile.currentTile.hasEntity() && projectile.equals(projectile.currentTile)){
            if(projectile.parent.currentAnimation.isFinished()) {
                for (Entity entity : projectile.currentTile.getEntityList()) {
                    entity.takeDamage(20);
                }
                projectile.takeDamage(1);
                projectile.shouldBeDestroyed = true;
                projectile.actionState = Action.DEATH;
            }
        }

        if(!projectile.isLegal()){
            projectile.takeDamage(1);
            projectile.shouldBeDestroyed = true;
            projectile.actionState = Action.DEATH;
        }
    }
}
