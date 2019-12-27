package com.mygdx.pmd.model.components;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by Cameron on 4/24/2017.
 * This will store the animations of each entity
 */
public class AnimationComponent extends Component {
    private float fStateTime;
    private Animation<Sprite> fAnimation;

    public AnimationComponent(Animation<Sprite> animation) {
        fStateTime = 0;
        fAnimation = animation;
    }

    public void addStateTime(float dt) {
        fStateTime += dt;
    }

    public float getStateTime() {
        return fStateTime;
    }

    public Animation<Sprite> getAnimation() {
        return fAnimation;
    }
}
