package com.mygdx.pmd.model.Behavior.Projectile;

import com.mygdx.pmd.model.Entity.Projectile.Projectile;
import com.mygdx.pmd.model.Tile.Tile;

/**
 * Created by Cameron on 11/17/2016.
 */
public class ProjectileRangedMovementBehavior extends ProjectileBehavior {


    public ProjectileRangedMovementBehavior(Projectile projectile) {
        super(projectile);
    }

    @Override
    public void execute() {
        if (!canExecute()) return;

        switch (projectile.direction) {
            case up:
                projectile.y += projectile.speed;
                break;
            case down:
                projectile.y -= projectile.speed;
                break;
            case right:
                projectile.x += projectile.speed;
                break;
            case left:
                projectile.x -= projectile.speed;
                break;
        }
        projectile.currentTile = Tile.getTileAt(projectile.x, projectile.y, projectile.tileBoard);

        //this code is a range limit on how far away the projectile can go 
        if(projectile.parent.currentTile.calculateDistance(projectile.currentTile) > 5*25){
            projectile.dispose();
        }
    }

    @Override
    public boolean canExecute() {
        if (projectile.shouldBeDestroyed) return false;
        return true;
    }

}
