package com.mygdx.pmd.model.Entity.Projectile;

import com.badlogic.gdx.audio.Sound;
import com.mygdx.pmd.PMD;
import com.mygdx.pmd.enumerations.Action;
import com.mygdx.pmd.enumerations.Move;
import com.mygdx.pmd.model.Behavior.*;
import com.mygdx.pmd.model.Behavior.Projectile.ProjectileAnimationBehavior;
import com.mygdx.pmd.model.Behavior.Projectile.ProjectileCollisionBehavior;
import com.mygdx.pmd.model.Behavior.Projectile.ProjectileMovementBehavior;
import com.mygdx.pmd.model.Entity.DynamicEntity;
import com.mygdx.pmd.model.Entity.Pokemon.Pokemon;
import com.mygdx.pmd.model.Tile.Tile;
import com.mygdx.pmd.utils.PAnimation;

/**
 * Created by Cameron on 10/18/2016.
 */
public class Projectile extends DynamicEntity {
    public Pokemon parent;

    //instance fields from move
    public Move move;
    public boolean isRanged;
    public int damage;
    public int speed;
    private PAnimation projectileAnimation;

    public Projectile(Pokemon parent, Move move) {
        // put down location as the parent's facing tile's location
        // set default values
        // TODO what if facing tile is null
        super(parent.controller, parent.facingTile.x, parent.facingTile.y);
        this.parent = parent;
        this.isTurnBased = false;
        this.direction = parent.direction;

        //store move data
        this.move = move;
        this.damage = move.damage;
        this.speed = move.speed;
        this.isRanged = move.isRanged();

        // load all the things
        if (move.isRanged()) {
            this.loadMovementLogic();
            this.loadCollisionLogic();
        } else {
            this.collide();
        }
        this.loadAnimations();
    }

    /**
     * set a behavior that will allow for movement
     */
    public void loadMovementLogic() {
        behaviors[2] = new ProjectileMovementBehavior(this);
        this.setActionState(Action.MOVING);
    }

    /**
     * set collision logic
     */
    public void loadCollisionLogic() {
        behaviors[0] = new ProjectileCollisionBehavior(this);
    }

    /**
     * initialize animations - include adding animation behavior
     */
    public void loadAnimations() {
        projectileAnimation = new PAnimation("attack", move.projectileMovementAnimation, null, 20, true);
        animationMap.put("movement", projectileAnimation);

        projectileAnimation = new PAnimation("death", move.projectileDeathAnimation, null, move.animationLength, false);
        animationMap.put("death", projectileAnimation);

        behaviors[1] = new ProjectileAnimationBehavior(this);
    }

    @Override
    public void update() {
        // only update the projectile when the parent's attack animation has finished
        if (parent.currentAnimation.isFinished()) {
            super.update();
        }

        if (projectileAnimation.isFinished() && this.shouldBeDestroyed) {
            for (DynamicEntity dEntity : currentTile.dynamicEntities) {
                dEntity.takeDamage(move.damage);
            }

            controller.addToRemoveList(this);

            //setting this to null so parent will know that the attack has finished
            this.parent.projectile = null;
        }
    }

    public void collide() {
        this.shouldBeDestroyed = true;
        this.setActionState(Action.COLLISION);

        // play sound effect
        PMD.manager.get("sfx/wallhit.wav", Sound.class).play();

        // ensure that the collision class and movement class don't run anymore
        this.behaviors[0] = this.noBehavior;
        this.behaviors[2] = this.noBehavior;
    }

    @Override
    public boolean isLegalToMoveTo(Tile tile) {
        return tile.isWalkable;
    }

    @Override
    public void registerObservers() {

    }
}

