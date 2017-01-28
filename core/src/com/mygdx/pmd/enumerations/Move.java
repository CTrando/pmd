package com.mygdx.pmd.enumerations;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import com.mygdx.pmd.PMD;

/**
 * Created by Cameron on 7/23/2016.
 */
public enum Move {


    NOMOVE(false,0,1,20),
    SCRATCH(true,20,1,30),
    GROWL(false,0,1,20),
    POUND(false,10,1,20), INSTANT_KILLER(false,100000000,5,50);

    private final boolean isRanged;
    public final int damage;
    public final int speed;
    public final Array<Sprite> projectileMovementAnimation;
    public final Array<Sprite> projectileDeathAnimation;
    public final int animationLength;

    Move(final boolean isRanged, int damage, int speed, int animationLength){
        this.isRanged = isRanged;
        this.damage = damage;
        this.speed = speed;
        this.animationLength = animationLength;

        //this.projectileDeathAnimation = getDeathSprites();
        this.projectileDeathAnimation = getClawSprites();
        this.projectileMovementAnimation = getMovementSprites();
    }

    public boolean isRanged(){
        return isRanged;
    }

    //I don't want to construct the animation object here because I don't want them to share the same animation object, only the same sprites

    public Array<Sprite> getMovementSprites(){
        Array<Sprite> retArr = new Array<Sprite>();
        retArr.add(PMD.sprites.get("projectile1"));
        retArr.add(PMD.sprites.get("projectile2"));
        retArr.add(PMD.sprites.get("projectile3"));

        return retArr;
    }

    public Array<Sprite> getDeathSprites(){
        Array<Sprite> retArr = new Array<Sprite>();
        retArr.add(PMD.sprites.get("projectiledeath1"));
        retArr.add(PMD.sprites.get("projectiledeath2"));
        retArr.add(PMD.sprites.get("projectiledeath3"));

        return retArr;
    }

    public Array<Sprite> getClawSprites(){
        Array<Sprite> retArr = new Array<Sprite>();
        retArr.add(PMD.sprites.get("claw1"));
        retArr.add(PMD.sprites.get("claw2"));
        retArr.add(PMD.sprites.get("claw3"));
        retArr.add(PMD.sprites.get("claw4"));
        retArr.add(PMD.sprites.get("claw5"));
        retArr.add(PMD.sprites.get("claw6"));
        retArr.add(PMD.sprites.get("claw7"));

        return retArr;
    }

}
