package com.mygdx.pmd.model.Behavior.Projectile;

import com.badlogic.gdx.audio.Sound;
import com.mygdx.pmd.PMD;
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
                    dEntity.takeDamage(projectile.damage);
                }

                projectile.takeDamage(1);
                projectile.shouldBeDestroyed = true;
                projectile.setActionState(Action.DEATH);
                PMD.manager.get("sfx/wallhit.wav", Sound.class).play();
            }
        }

        if (!projectile.isLegalToMoveTo(projectile.currentTile)) {
            projectile.takeDamage(1);
            projectile.shouldBeDestroyed = true;
            projectile.setActionState(Action.DEATH);
            PMD.manager.get("sfx/wallhit.wav", Sound.class).play();
        }
    }

    @Override
    public boolean canExecute() {
        if (projectile.shouldBeDestroyed) {
            return false;
        }
        if (projectile.currentTile == null) { //meed to incorporate into isLeagltomoveto
            PMD.manager.get("sfx/wallhit.wav", Sound.class).play();
            projectile.shouldBeDestroyed = true;
            projectile.setActionState(Action.DEATH);

            return false;
        }
        return true;
    }
}
