package com.mygdx.pmd.utils;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.mygdx.pmd.Enumerations.Direction;
import com.mygdx.pmd.Model.Pokemon.Pokemon;
import com.mygdx.pmd.Model.TileType.Tile;
import com.mygdx.pmd.Screen.DungeonScreen;

/**
 * Created by Cameron on 10/18/2016.
 */
public class Projectile extends Entity {

    private PAnimation projectileAnimation;
    private Pokemon parent;
    private Tile targetTile;

    private Sprite currentSprite;

    public boolean hasHit;

    public Projectile(Tile targetTile, Pokemon parent) {
        this.targetTile = targetTile;
        this.parent = parent;

        this.x = parent.facingTile.x;
        this.y = parent.facingTile.y;

        Array<Sprite> array = new Array<Sprite>();
        array.add(DungeonScreen.sprites.get("treekodownattack3"));
        projectileAnimation = new PAnimation("attack", array, null, 10);
    }

    public Projectile(Direction direction, Pokemon parent) {
        this.parent = parent;
    }

    @Override
    public void update() {
        currentSprite = projectileAnimation.getCurrentSprite();
        if (this.equalsTile(this.targetTile) && projectileAnimation.isFinished()) {
            hasHit = true;
            if (parent.canAttack() != null) {
                parent.dealDamage(parent.canAttack());
                parent.controller.controllerScreen.manager.get("sfx/wallhit.wav", Sound.class).play();
            }
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        if (currentSprite != null)
            batch.draw(currentSprite, x, y, currentSprite.getWidth(), currentSprite.getHeight());
    }
}
