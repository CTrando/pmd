package com.mygdx.pmd.model.Behavior.Projectile;

import com.mygdx.pmd.enumerations.Action;
import com.mygdx.pmd.model.Entity.DynamicEntity;
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
        if (!canExecute()) return;

        if (!projectile.currentTile.isDynamicEntityEmpty() && projectile.equals(projectile.currentTile)) {
            if (projectile.parent.currentAnimation.isFinished()) {
                for (DynamicEntity dEntity : projectile.currentTile.dynamicEntities) {
                    dEntity.takeDamage(20);
                }

                projectile.takeDamage(1);
                projectile.shouldBeDestroyed = true;
                projectile.setActionState(Action.DEATH);
            }
        }

        if (!projectile.isLegalToMoveTo(projectile.currentTile)) {
            projectile.takeDamage(1);
            projectile.shouldBeDestroyed = true;
            projectile.setActionState(Action.DEATH);
        }
    }

    @Override
    public boolean canExecute() {
        if (projectile.shouldBeDestroyed) {
            return false;
        }
        if (projectile.currentTile == null) {
            projectile.shouldBeDestroyed = true;
            return false;
        }
        return true;
    }
}
