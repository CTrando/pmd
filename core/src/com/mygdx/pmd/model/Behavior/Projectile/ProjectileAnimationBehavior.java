package com.mygdx.pmd.model.Behavior.Projectile;

import com.mygdx.pmd.model.Entity.Projectile.Projectile;

/**
 * Created by Cameron on 11/25/2016.
 */
public class ProjectileAnimationBehavior extends ProjectileBehavior {
    public ProjectileAnimationBehavior(Projectile projectile) {
        super(projectile);
    }

    @Override
    public void execute() {
        switch(projectile.getActionState()){
            case MOVING:
                projectile.currentSprite = projectile.animationMap.get("movement").getCurrentSprite();
                break;
            case DEATH:
                projectile.currentSprite = projectile.animationMap.get("death").getCurrentSprite();
                break;
        }
    }
}
