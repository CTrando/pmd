package com.mygdx.pmd.enumerations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.*;
import com.mygdx.pmd.PMD;

/**
 * Created by Cameron on 7/23/2016.
 */
public enum Move {


    NOMOVE(false,0,1,1,20, "",""),
    SCRATCH(true,3,20,1,30, "projectilemotion", "claw"),
    SWIPERNOSWIPING(false, 3, 10, 1, 30, "projectilemotion", "claw"),
    INSTANT_KILLER(true,100, 100000000,5,20, "projectilemotion", "explosion");

    private final boolean isRanged;
    public final int damage;
    public final int speed;
    public final Array<Sprite> projectileMovementAnimation;
    public final Array<Sprite> projectileCollisionAnimation;
    public final int animationLength;
    public JsonReader jsonReader;
    public int range;

    Move(final boolean isRanged, int range, int damage, int speed, int animationLength, String motionHandle, String collisionHandle){
        this.isRanged = isRanged;
        this.damage = damage;
        this.range = range;
        this.speed = speed;
        this.animationLength = animationLength;
        this.jsonReader = new JsonReader();

        this.projectileCollisionAnimation = getSpritesFromJson(Gdx.files.internal("utils/MoveStorage.json"), collisionHandle); // getClawSprites();
        this.projectileMovementAnimation = getSpritesFromJson(Gdx.files.internal("utils/MoveStorage.json"), motionHandle);
    }

    public boolean isRanged(){
        return isRanged;
    }

    public Array<Sprite> getSpritesFromJson(FileHandle file, String classifier){
        Array<Sprite> retArr = new Array<Sprite>();
        JsonValue jsonValue = jsonReader.parse(file);
        JsonValue spritesJson = jsonValue.get(classifier);

        if(spritesJson != null) {
            for (JsonValue spriteJson : spritesJson.iterator()) {
                Sprite sprite = PMD.sprites.get(spriteJson.asString());
                retArr.add(sprite);
            }
        }
        return retArr;
    }

}
