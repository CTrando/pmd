package com.mygdx.pmd.model.logic;

import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.interfaces.Damageable;
import com.mygdx.pmd.model.Entity.*;
import com.mygdx.pmd.model.Entity.Pokemon.*;
import com.mygdx.pmd.model.Entity.Projectile.Projectile;
import com.mygdx.pmd.utils.*;

/**
 * Created by Cameron on 11/17/2016.
 */
public class ProjectileLogic implements Logic {
    private Projectile projectile;
    private Pokemon parent;
    private PAnimation animation;

    /**
     * This class has one job, to find when a ranged projectile interacts with an entity or a unwalkable tile
     *
     * @param projectile the projectile to be acted upon
     */
    public ProjectileLogic(Projectile projectile) {
        this.projectile = projectile;
        this.parent = projectile.parent;
        this.animation = projectile.animation;
    }

    /**
     * Set of rules to check if projectile has collided yet
     */
    public void execute() {
        projectile.animationLogic.execute();

        if (animation.isFinished() && projectile.getActionState() == Action.COLLISION) {
            for (Damageable damageable : PUtils.getObjectsOfType(Damageable.class, projectile.getCurrentTile()
                                                                                             .getEntityList()))
            {
                damageable.takeDamage(parent, projectile.move.damage);
            }

            if (projectile.move.equals(Move.INSTANT_KILLER)) {
                System.out.println("RKO OUT OF NOWHERE");
            }

            //let parent know that the attack has finished
            projectile.shouldBeDestroyed = true;
        }
    }
}
