package com.mygdx.pmd.model.Entity.Projectile;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import com.mygdx.pmd.PMD;
import com.mygdx.pmd.enumerations.Action;
import com.mygdx.pmd.enumerations.Move;
import com.mygdx.pmd.model.Behavior.BaseBehavior;
import com.mygdx.pmd.model.Behavior.Projectile.ProjectileAnimationBehavior;
import com.mygdx.pmd.model.Behavior.Projectile.ProjectileCollisionLogicBehavior;
import com.mygdx.pmd.model.Behavior.Projectile.ProjectileRangedMovementBehavior;
import com.mygdx.pmd.model.Entity.DynamicEntity;
import com.mygdx.pmd.model.Entity.Pokemon.Pokemon;
import com.mygdx.pmd.model.Tile.Tile;
import com.mygdx.pmd.utils.PAnimation;

/**
 * Created by Cameron on 10/18/2016.
 */
public class Projectile extends DynamicEntity {

    //TODO FIX UP THIS CLASS

    private PAnimation projectileAnimation;
    public Pokemon parent;
    public Move move;
    public boolean isRanged;
    public int damage;
    public int speed;

    public Projectile(Pokemon parent, Move move) {
        super(parent.controller, parent.facingTile.x, parent.facingTile.y);
        this.direction = parent.direction;
        this.isTurnBased = false;
        this.hp = 1;
        this.currentTile = parent.facingTile;
        this.parent = parent;
        this.move = move;
        this.damage = move.damage;
        this.speed = move.speed;
        this.isRanged = move.isRanged();

        behaviors[BaseBehavior.ATTACK_BEHAVIOR] = new ProjectileCollisionLogicBehavior(this);


        projectileAnimation = new PAnimation("attack", move.projectileMovementAnimation, null, 20, true);
        animationMap.put("movement", projectileAnimation);

        projectileAnimation = new PAnimation("death", move.projectileDeathAnimation, null, move.animationLength, false);
        animationMap.put("death", projectileAnimation);

        behaviors[BaseBehavior.ANIMATION_BEHAVIOR] = new ProjectileAnimationBehavior(this);

        if (move.isRanged()) {
            behaviors[BaseBehavior.LOGIC_BEHAVIOR] = new ProjectileRangedMovementBehavior(this);
            this.setActionState(Action.MOVING);
        } else {
            for(DynamicEntity dEntity: currentTile.dynamicEntities){
                dEntity.takeDamage(move.damage);
            }
            this.dispose();
        }
    }

    @Override
    public void update() {
        if (parent.currentAnimation.isFinished()) {
            super.update();
        }

        if (projectileAnimation.isFinished() && this.shouldBeDestroyed) {
            controller.addToRemoveList(this);
            this.parent.projectile = null;
        }
    }

    @Override
    public boolean isLegalToMoveTo(Tile tile) {
        return tile.isWalkable;
    }

    @Override
    public void registerObservers() {

    }

    public void dispose() {
        this.takeDamage(1);
        this.shouldBeDestroyed = true;
        this.setActionState(Action.DEATH);
        PMD.manager.get("sfx/wallhit.wav", Sound.class).play();
    }
}

