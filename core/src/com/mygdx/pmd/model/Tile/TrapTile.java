package com.mygdx.pmd.model.Tile;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.pmd.model.Entity.*;
import com.mygdx.pmd.model.Floor.*;
import com.mygdx.pmd.model.components.*;

/**
 * Created by Cameron on 4/28/2017.
 */
public abstract class TrapTile extends Tile {
    protected boolean hasTriggered;
    protected Entity receiver;
    protected AnimationComponent anc;
    protected Sprite backgroundSprite;

    protected TrapTile(int r, int c, Floor floor, String classifier) {
        super(r, c, floor, classifier);
    }
}
