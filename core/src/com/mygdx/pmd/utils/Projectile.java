package com.mygdx.pmd.utils;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import com.mygdx.pmd.Enumerations.Direction;
import com.mygdx.pmd.Model.Behavior.Behavior;
import com.mygdx.pmd.Model.Behavior.MoveSlowBehavior;
import com.mygdx.pmd.Model.Behavior.ProjectileCollisionLogicBehavior;
import com.mygdx.pmd.Model.Behavior.ProjectileMovementLogicBehavior;
import com.mygdx.pmd.Model.Pokemon.Pokemon;
import com.mygdx.pmd.Model.TileType.GenericTile;
import com.mygdx.pmd.Model.TileType.Tile;
import com.mygdx.pmd.Screen.DungeonScreen;

/**
 * Created by Cameron on 10/18/2016.
 */
public class Projectile extends Entity {

    private PAnimation projectileAnimation;
    private Pokemon parent;
    private Tile targetTile;
    public boolean isAttackFinished;
    public boolean destroy;

    public Direction direction;

    public Projectile(Tile targetTile, Pokemon parent) {
        super(parent.controller, parent.facingTile.x, parent.facingTile.y);

        behaviors[Behavior.ATTACK_BEHAVIOR] = new ProjectileCollisionLogicBehavior(this);
        behaviors[Behavior.LOGIC_BEHAVIOR] = new ProjectileMovementLogicBehavior(this);
        behaviors[Behavior.MOVE_BEHAVIOR] = new MoveSlowBehavior(this);

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

   /* @Override
    public void updateAnimation() {
        currentSprite = projectileAnimation.getCurrentSprite();
    }

    @Override
    public void updateLogic() {
        if (targetTile != null) {
            if (this.equals(this.targetTile) && projectileAnimation.isFinished()) {
                this.takeDamage(1);
                if (this.targetTile.hasEntity()) {
                    parent.dealDamage(targetTile.getCurrentPokemon(), 1);
                    parent.controller.controllerScreen.manager.get("sfx/wallhit.wav", Sound.class).play();
                }
            }
        } else {
            this.currentTile = Tile.getTileAt(x, y, parent.tileBoard);
            if (this.currentTile == null || currentTile instanceof GenericTile)
                this.takeDamage(1);
            else if (currentTile.getCurrentPokemon() != null && currentTile.getCurrentPokemon() != parent) {
                this.takeDamage(1);
                this.dealDamage(currentTile.getCurrentPokemon(), 1);
            }
        }
    }

    @Override
    public void updatePosition() {
        if(direction != null) {
            switch (direction) {
                case up:
                    y += 2;
                    break;
                case down:
                    y -= 2;
                    break;
                case right:
                    x += 2;
                    break;
                case left:
                    x -= 2;
                    break;
            }
        }
    }*/

    @Override
    public boolean isLegalToMoveTo(Tile tile) {
        if(tile instanceof GenericTile || tile.hasEntity()) return false;
        return true;
    }
}

