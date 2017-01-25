package com.mygdx.pmd.model.Behavior.Projectile;

import com.mygdx.pmd.model.Behavior.BaseBehavior;
import com.mygdx.pmd.model.Entity.Projectile.Projectile;
import com.mygdx.pmd.model.Tile.Tile;

/**
 * Created by Cameron on 11/17/2016.
 */
public abstract class ProjectileBehavior extends BaseBehavior {
    Projectile projectile;
    Tile[][] tileBoard;

    public ProjectileBehavior(Projectile projectile) {
        super(projectile);
        this.projectile = projectile;
        this.tileBoard = projectile.tileBoard;
    }
}
