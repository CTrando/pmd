package com.mygdx.pmd.Model.Behavior;

import com.mygdx.pmd.utils.Entity;
import com.mygdx.pmd.utils.Projectile;

/**
 * Created by Cameron on 11/17/2016.
 */
public class ProjectileMovementLogicBehavior extends ProjectileBehavior{


    public ProjectileMovementLogicBehavior(Projectile projectile) {
        super(projectile);
    }

    @Override
    public void execute(){
        if(projectile.direction != null) {
            switch (projectile.direction) {
                case up:
                    projectile.y += 2;
                    break;
                case down:
                    projectile.y -= 2;
                    break;
                case right:
                    projectile.x += 2;
                    break;
                case left:
                    projectile.x -= 2;
                    break;
            }
        }
    }

}
