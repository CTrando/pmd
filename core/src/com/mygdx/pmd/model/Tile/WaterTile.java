package com.mygdx.pmd.model.Tile;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import com.mygdx.pmd.PMD;
import com.mygdx.pmd.model.Floor.*;
import com.mygdx.pmd.model.components.*;
import com.mygdx.pmd.utils.PAnimation;

/**
 * Created by Cameron on 5/5/2017.
 */
public class WaterTile extends Tile {

    private AnimationComponent anc;

    public WaterTile(int r, int c, Floor floor) {
        super(r, c, floor, "WATER");
        this.isWalkable = false;
        this.loadAnimations();

        this.anc = getComponent(AnimationComponent.class);
        this.anc.setCurrentAnimation("water");
    }

    @Override
    public void update() {
        super.update();
        rc.setSprite(anc.getCurrentSprite());
    }

    @Override
    protected void loadAnimations() {
        AnimationComponent anc = new AnimationComponent(this);
        Array<Sprite> sprites = new Array<Sprite>();
        sprites.add(PMD.sprites.get("water1"));
        sprites.add(PMD.sprites.get("water2"));
        sprites.add(PMD.sprites.get("water3"));

        PAnimation animation = new PAnimation("water", sprites, 30, true);
        anc.putAnimation("water", animation);
        components.put(AnimationComponent.class, anc);
    }

    @Override
    public void runLogic() {

    }
}
