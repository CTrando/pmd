package com.mygdx.pmd.model.Entity.Projectile;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import com.mygdx.pmd.PMD;
import com.mygdx.pmd.enumerations.Action;
import com.mygdx.pmd.enumerations.Move;
import com.mygdx.pmd.model.Behavior.BaseBehavior;
import com.mygdx.pmd.model.Behavior.Projectile.ProjectileAnimationBehavior;
import com.mygdx.pmd.model.Behavior.Projectile.ProjectileCollisionLogicBehavior;
import com.mygdx.pmd.model.Behavior.Projectile.ProjectileRangedMovementBehavior;
import com.mygdx.pmd.model.Entity.Pokemon.Pokemon;
import com.mygdx.pmd.model.Tile.GenericTile;
import com.mygdx.pmd.model.Tile.Tile;
import com.mygdx.pmd.screens.DungeonScreen;
import com.mygdx.pmd.model.Entity.Entity;
import com.mygdx.pmd.utils.PAnimation;

/**
 * Created by Cameron on 10/18/2016.
 */
public class Projectile extends Entity {

    private PAnimation projectileAnimation;
    public Pokemon parent;
    public Move move;
    public boolean isRanged;

    public Projectile(Pokemon parent, Move move) {
        super(parent.controller, parent.facingTile.x, parent.facingTile.y);
        this.direction = parent.direction;
        this.isTurnBased = false;
        this.hp = 1;
        this.currentTile = parent.facingTile;
        this.parent = parent;
        this.move = move;
        this.isRanged = move.isRanged();

        behaviors[BaseBehavior.ATTACK_BEHAVIOR] = new ProjectileCollisionLogicBehavior(this);

        if(move.isRanged()){
            behaviors[BaseBehavior.LOGIC_BEHAVIOR] = new ProjectileRangedMovementBehavior(this);
            behaviors[BaseBehavior.ANIMATION_BEHAVIOR] = new ProjectileAnimationBehavior(this);
            this.actionState = Action.MOVING;
        }


        Array<Sprite> array = new Array<Sprite>();
        array.add(PMD.sprites.get("projectile1"));
        array.add(PMD.sprites.get("projectile2"));
        array.add(PMD.sprites.get("projectile3"));

        projectileAnimation = new PAnimation("attack", array, null, 20, true);
        animationMap.put("movement", projectileAnimation);

        array = new Array<Sprite>();
        array.add(PMD.sprites.get("projectiledeath1"));
        array.add(PMD.sprites.get("projectiledeath2"));
        array.add(PMD.sprites.get("projectiledeath3"));

        projectileAnimation = new PAnimation("death", array, null, 20, false);
        animationMap.put("death", projectileAnimation);
    }

    @Override
    public void update(){
        if(parent.currentAnimation.isFinished()) {
            super.update();
        }

        if(projectileAnimation.isFinished() && this.shouldBeDestroyed){
            controller.removeEntity(this);
            this.parent.projectile = null;
        }
    }

    @Override
    public boolean isLegalToMoveTo(Tile tile) {
        if(tile instanceof GenericTile || tile.hasEntity()) return false;
        return true;
    }

    public boolean isLegal(){
        if(currentTile instanceof GenericTile || currentTile == null) return false;
        return true;
    }
}

