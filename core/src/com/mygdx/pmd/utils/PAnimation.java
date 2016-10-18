package com.mygdx.pmd.utils;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Cameron on 9/21/2016.
 */
public class PAnimation {

    public int animationLength;
    public Array<Sprite> animationSprites;
    public int animationCounter = 0;
    public int eachFrameLength;
    public int frameCounter = 0;
    public Sprite currentSprite;
    public String classifier;
    public Sprite finalSprite;

    public PAnimation(String classifier, Array<Sprite> animationSprites, Sprite finalSprite, int animationLength) {
        this.classifier = classifier;
        this.animationLength = animationLength;
        this.animationSprites = animationSprites;

        if (animationSprites.size > 0) {
            this.eachFrameLength = animationLength / animationSprites.size;
            this.currentSprite = animationSprites.get(frameCounter);
        }
        this.finalSprite = finalSprite;
    }

    public void update() {
        animationCounter++;

        if (animationCounter % eachFrameLength == 0) {
            frameCounter++;
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
