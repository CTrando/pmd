package com.mygdx.pmd.Model.Behavior.Projectile;

import com.mygdx.pmd.Model.Behavior.BaseBehavior;
import com.mygdx.pmd.Model.Entity.Projectile.Projectile;

/**
 * Created by Cameron on 11/17/2016.
 */
public abstract class ProjectileBehavior extends BaseBehavior {
    Projectile projectile;
    public ProjectileBehavior(Projectile projectile) {
        super(projectile);
        this.projectile = projectile;
    }
}
