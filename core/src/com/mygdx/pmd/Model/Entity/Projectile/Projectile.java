package com.mygdx.pmd.Model.Entity.Projectile;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import com.mygdx.pmd.Enumerations.Direction;
import com.mygdx.pmd.Model.Behavior.BaseBehavior;
import com.mygdx.pmd.Model.Behavior.Entity.MoveSlowBehavior;
import com.mygdx.pmd.Model.Behavior.Projectile.ProjectileCollisionLogicBehavior;
import com.mygdx.pmd.Model.Behavior.Projectile.ProjectileMovementLogicBehavior;
import com.mygdx.pmd.Model.Entity.Pokemon.Pokemon;
import com.mygdx.pmd.Model.Tile.GenericTile;
import com.mygdx.pmd.Model.Tile.Tile;
import com.mygdx.pmd.Screen.DungeonScreen;
import com.mygdx.pmd.Model.Entity.Entity;
import com.mygdx.pmd.utils.PAnimation;

/**
 * Created by Cameron on 10/18/2016.
 */
public class Projectile extends Entity {

    private PAnimation projectileAnimation;
    private Pokemon parent;
    private Tile targetTile;
    public boolean isAttackFinished;
    public boolean destroy;

    public Projectile(Tile targetTile, Pokemon parent) {
        super(parent.controller, parent.facingTile.x, parent.facingTile.y);

        behaviors[BaseBehavior.ATTACK_BEHAVIOR] = new ProjectileCollisionLogicBehavior(this);
        behaviors[BaseBehavior.LOGIC_BEHAVIOR] = new ProjectileMovementLogicBehavior(this);
        behaviors[BaseBehavior.MOVE_BEHAVIOR] = new MoveSlowBehavior(this);

        this.hp = 1;
        this.targetTile = targetTile;
        this.currentTile = parent.facingTile;
        this.parent = parent;

        Array<Sprite> array = new Array<Sprite>();
        array.add(DungeonScreen.sprites.get("treekodownattack3"));
        projectileAnimation = new PAnimation("attack", array, null, 10, false);
        parent.controller.projectiles.add(this);
    }

    public Projectile(Direction direction, Pokemon parent) {
        super(parent.controller, parent.currentTile.x, parent.currentTile.y);
        this.hp = 1;
        this.x = parent.getCurrentTile().x;
        this.y = parent.getCurrentTile().y;

        Array<Sprite> array = new Array<Sprite>();
        array.add(DungeonScreen.sprites.get("treekodownattack3"));
        projectileAnimation = new PAnimation("attack", array, null, 10, false);

        this.direction = direction;
        this.parent = parent;
    }

    //TODO alter the projectile to make it have health, and alter the mutator methods for damage and health so that when it reaches 0 it removes itself from the updatelist
    @Override
    public void update() {
        for(int i = 0; i< behaviors.length; i++){
            behaviors[i].execute();
        }
        if(isAttackFinished) {
            if (parent.currentAnimation.isFinished()) {
                parent.projectile = null;
                destroy = true;
            }
        }
    }

    @Override
    public boolean isLegalToMoveTo(Tile tile) {
        if(tile instanceof GenericTile || tile.hasEntity()) return false;
        return true;
    }
}

