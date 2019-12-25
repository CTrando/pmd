package com.mygdx.pmd.model.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Sprite;


/**
 * Created by Cameron on 4/23/2017.
 */
public class RenderComponent implements Component {
    private Sprite fSprite;
    private int fIndex;

    public RenderComponent(Sprite sprite) {
        this(sprite, 0);
    }

    public RenderComponent(Sprite sprite, int index) {
        fSprite = sprite;
        fIndex = index;
    }

    public int getZIndex() {
        return fIndex;
    }

    public void setSprite(Sprite sprite) {
        fSprite = sprite;
    }

    public Sprite getSprite() {
        return fSprite;
    }
}
