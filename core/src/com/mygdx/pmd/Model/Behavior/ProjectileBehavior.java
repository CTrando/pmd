package com.mygdx.pmd.Model.Behavior;

import com.mygdx.pmd.utils.Entity;
import com.mygdx.pmd.utils.Projectile;

/**
 * Created by Cameron on 11/17/2016.
 */
public abstract class ProjectileBehavior extends Behavior {
    Projectile projectile;
    public ProjectileBehavior(Projectile projectile) {
        super(projectile);
        this.projectile = projectile;
    }
}
