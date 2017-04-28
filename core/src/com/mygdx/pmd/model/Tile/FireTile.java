package com.mygdx.pmd.model.Tile;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import com.mygdx.pmd.PMD;
import com.mygdx.pmd.model.Floor.*;
import com.mygdx.pmd.model.components.*;
import com.mygdx.pmd.utils.PAnimation;

/**
 * Created by Cameron on 4/25/2017.
 */
public class FireTile extends Tile {
    private AnimationComponent anc;

    public FireTile(int r, int c, Floor floor) {
        super(r, c, floor, "OCEAN");
        this.loadAnimations();

        this.anc = getComponent(AnimationComponent.class);
        this.anc.setCurrentAnimation("wave");
        rc.setSprite(anc.getCurrentSprite());
    }

    @Override
    public void loadAnimations() {
        AnimationComponent anc = new AnimationComponent(this);
        Array<Sprite> sprites = new Array<Sprite>();
        sprites.add(PMD.sprites.get("fire1"));
        sprites.add(PMD.sprites.get("fire2"));
        sprites.add(PMD.sprites.get("fire3"));
        sprites.add(PMD.sprites.get("fire4"));
        sprites.add(PMD.sprites.get("fire5"));
        sprites.add(PMD.sprites.get("fire6"));
        sprites.add(PMD.sprites.get("fire7"));
        sprites.add(PMD.sprites.get("fire8"));


        PAnimation animation = new PAnimation("wave", sprites, 120, true);

        anc.putAnimation("wave", animation);
        components.put(AnimationComponent.class, anc);
    }

    @Override
    public void update() {
        rc.setSprite(anc.getCurrentSprite());
    }

    @Override
    public void runLogic() {

    }
}
