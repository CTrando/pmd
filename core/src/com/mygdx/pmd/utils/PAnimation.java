package com.mygdx.pmd.utils;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import com.mygdx.pmd.enumerations.Key;

/**
 * Created by Cameron on 9/21/2016.
 */
public class PAnimation {

    public int animationLength;
    public Array<Sprite> animationSprites;
    public int animationCounter = 0;
    public int eachFrameLength =1;
    public int frameCounter = 0;
    public Sprite currentSprite;
    public String classifier;
    public Sprite finalSprite;
    public Key key;
    boolean isLooping;

    public PAnimation(String classifier, Array<Sprite> animationSprites, Sprite finalSprite, int animationLength, boolean isLooping) {
        this.classifier = classifier;
        this.animationLength = animationLength;
        this.animationSprites = animationSprites;
        this.isLooping = isLooping;

        if (animationSprites.size > 0) {
            this.eachFrameLength = animationLength / animationSprites.size;
            this.currentSprite = animationSprites.get(frameCounter);
        }
        this.finalSprite = finalSprite;
    }

    public void update() {
        if(isFinished() && !isLooping) return;

        animationCounter++;

        if (animationCounter % eachFrameLength == 0) {
            frameCounter++;
            if(animationSprites.size >0)
                currentSprite = animationSprites.get(frameCounter % animationSprites.size);
        }
    }

    public Sprite getCurrentSprite() {
        this.update();
        return currentSprite;
    }

    public boolean isFinished() {
        return frameCounter >= animationSprites.size;
    }

    public void clear() {
        animationCounter = 0;
        frameCounter = 0;
    }

    public String toString() {
        return classifier;
    }
}
