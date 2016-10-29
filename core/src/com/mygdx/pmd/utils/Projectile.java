package com.mygdx.pmd.utils;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.mygdx.pmd.Enumerations.Direction;
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

    public Tile currentTile;

    private Sprite currentSprite;

    public boolean hasHit;

    public Direction direction;

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
        this.x = parent.getCurrentTile().x;
        this.y = parent.getCurrentTile().y;

        Array<Sprite> array = new Array<Sprite>();
        array.add(DungeonScreen.sprites.get("treekodownattack3"));
        projectileAnimation = new PAnimation("attack", array, null, 10);

        this.direction = direction;
        this.parent = parent;
    }

    //TODO alter the projectile to make it have health, and alter the mutator methods for damage and health so that when it reaches 0 it removes itself from the updatelist
    @Override
    public void update() {
        currentSprite = projectileAnimation.getCurrentSprite();
        if(targetTile != null) {
            if (this.equals(this.targetTile) && projectileAnimation.isFinished()) {
                hasHit = true;
                if (this.targetTile.hasAPokemon()) {
                    parent.dealDamage(targetTile.getCurrentPokemon());
                    parent.controller.controllerScreen.manager.get("sfx/wallhit.wav", Sound.class).play();
                }
            }
        } else if(direction != null) {
            switch(direction){
                case up:
                    y+=2; break;
                case down:
                    y-=2; break;
                case right:
                    x+=2; break;
                case left:
                    x-=2; break;
            }

            currentTile = Tile.getTileAt(x, y, parent.tileBoard);
            if(currentTile == null || currentTile instanceof GenericTile)
                hasHit = true;
            else if(currentTile.getCurrentPokemon() != null && currentTile.getCurrentPokemon() != parent) {
                hasHit = true;
                currentTile.getCurrentPokemon().takeDamage(1);
            }


        }
    }

    @Override
    public void render(SpriteBatch batch) {
        if (currentSprite != null)
            batch.draw(currentSprite, x, y, currentSprite.getWidth(), currentSprite.getHeight());
    }

    @Override
    public boolean isLegalToMoveTo(Tile tile) {
        if(tile instanceof GenericTile || tile.hasAPokemon()) return false;
        return true;
    }
}
