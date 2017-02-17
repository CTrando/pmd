/*
package com.mygdx.pmd.model.Entity.Projectile;

import com.badlogic.gdx.audio.Sound;
import com.mygdx.pmd.PMD;
import com.mygdx.pmd.enumerations.Action;
import com.mygdx.pmd.enumerations.Move;
import com.mygdx.pmd.model.Behavior.*;
import com.mygdx.pmd.model.Behavior.Projectile.ProjectileAnimationComponent;
import com.mygdx.pmd.model.Behavior.Projectile.ProjectileCollisionComponent;
import com.mygdx.pmd.model.Behavior.Projectile.ProjectileMovementComponent;
import com.mygdx.pmd.model.Entity.*;
import com.mygdx.pmd.model.Entity.Pokemon.Pokemon;
import com.mygdx.pmd.utils.PAnimation;

*/
/**
 * Created by Cameron on 10/18/2016.
 *//*

public class Projectile extends Entity {
    public Pokemon parent;

    //instance fields from currentMove
    public Move move;
    public boolean isRanged;
    public int damage;
    public int speed;
    private PAnimation projectileAnimation;

    public Projectile(Pokemon parent) {
        // put down location as the parent's facing tile's location
        // set default values
        // TODO what if facing tile is null
        super(parent.floor, parent.facingTile.x, parent.facingTile.y);
        this.parent = parent;
        this.isTurnBased = false;
        this.direction = parent.direction;

        //store currentMove data
        this.move = parent.currentMove;
        this.damage = move.damage;
        this.speed = move.speed;
        this.isRanged = move.isRanged();

        // load all the things
        if (move.isRanged()) {
            this.addComponent(Component.MOVE, new ProjectileMovementComponent(this));
            this.addComponent(Component.COLLISION, new ProjectileCollisionComponent(this));
        } else {
            this.collide();
        }
        this.loadAnimations();
    }

    */
/**
     * initialize animations - include adding animation behavior
     *//*

    private void loadAnimations() {
        projectileAnimation = new PAnimation("attack", move.projectileMovementAnimation, null, 20, true);
        animationMap.put("movement", projectileAnimation);

        projectileAnimation = new PAnimation("death", move.projectileCollisionAnimation, null, move.animationLength, false);
        animationMap.put("death", projectileAnimation);
    }

    @Override
    public void update() {
        // only update the projectile when the parent's attack animation has finished
        if (parent.currentAnimation.isFinished()) {
            super.update();
        }

        if (projectileAnimation.isFinished() && this.getActionState() == Action.COLLISION) {
            for (Entity entity : getCurrentTile().entities) {
                // use hp component for this
                //entity.takeDamage(parent, move.damage);
            }

            if(move.equals(Move.INSTANT_KILLER)){
                System.out.println("RKO OUT OF NOWHERE");
            }

            //setting this to null so parent will know that the attack has finished
            this.parent.children.removeValue(this, true);
            this.shouldBeDestroyed = true;
        }
    }

    public void collide() {
        this.setActionState(Action.COLLISION);

        // play sound effect
        PMD.manager.get("sfx/wallhit.wav", Sound.class).play();

        // ensure that the collision class and movement class don't run anymore
        this.removeComponent(Component.MOVE);
        this.removeComponent(Component.COLLISION);
    }

    @Override
    public void registerObservers() {

    }

    @Override
    public void dispose() {

    }
}

*/
