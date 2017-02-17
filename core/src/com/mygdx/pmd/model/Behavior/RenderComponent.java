package com.mygdx.pmd.model.Behavior;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.pmd.utils.PAnimation;

import java.util.HashMap;

/**
 * Created by Cameron on 2/17/2017.
 */
public class RenderComponent implements Component {
    public Sprite currentSprite;

    public PAnimation animation;
    public HashMap<String, PAnimation> animationMap;

    public RenderComponent(){
        animationMap = new HashMap<String, PAnimation>();
    }
}
