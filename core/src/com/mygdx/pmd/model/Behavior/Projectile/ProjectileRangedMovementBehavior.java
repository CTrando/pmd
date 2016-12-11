package com.mygdx.pmd.model.Behavior.Projectile;

import com.mygdx.pmd.model.Entity.Projectile.Projectile;
import com.mygdx.pmd.model.Tile.Tile;

/**
 * Created by Cameron on 11/17/2016.
 */
public class ProjectileRangedMovementBehavior extends ProjectileBehavior{


    public ProjectileRangedMovementBehavior(Projectile projectile) {
        super(projectile);
    }

    @Override
    public void execute(){
        if(!canExecute()) return;

        switch (projectile.direction) {
                case up:
                    projectile.y += 1;
                    break;
                case down:
                    projectile.y -= 1;
                    break;
                case right:
                    projectile.x += 1;
                    break;
                case left:
                    projectile.x -= 1;
                    break;
            }
            projectile.currentTile = Tile.getTileAt(projectile.x, projectile.y, projectile.tileBoard);
    }

    @Override
    public boolean canExecute() {
        if(projectile.shouldBeDestroyed) return false;
        return true;
    }

}
