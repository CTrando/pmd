package com.mygdx.pmd.utils.yaml;

import java.util.List;

public class AnimationInfo {
    private List<String> fSprites;
    private float fDuration;

    public float getDuration() {
        return fDuration;
    }

    public void setDuration(float duration) {
        fDuration = duration;
    }

    public List<String> getSprites() {
        return fSprites;
    }

    public void setSprites(List<String> sprites) {
        fSprites = sprites;
    }
}
