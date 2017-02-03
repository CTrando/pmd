package com.mygdx.pmd.model.Behavior.Projectile;

import com.mygdx.pmd.model.Entity.Projectile.Projectile;

/**
 * Created by Cameron on 11/17/2016.
 */
public class ProjectileCollisionBehavior extends ProjectileBehavior {
    /**
     * This class has one job, to find when a ranged projectile interacts with an entity or a unwalkable tile
     *
     * @param projectile the projectile to be acted upon
     */
    public ProjectileCollisionBehavior(Projectile projectile) {
        super(projectile);
    }

    /**
     * Set of rules to check if projectile has collided yet
     */
    @Override
    public void execute() {
        if (projectile.getCurrentTile().hasDynamicEntity() && projectile.equals(projectile.getCurrentTile())) {
            projectile.collide();
        }

        if (!projectile.isLegalToMoveTo(projectile.getCurrentTile())) {
            projectile.collide();
        }

        //this code is a range limit on how far away the projectile can go
        if (projectile.parent.getCurrentTile().calculateDistanceTo(projectile.getCurrentTile()) > projectile.move.range * 25) {
            projectile.collide();
        }
    }
}
