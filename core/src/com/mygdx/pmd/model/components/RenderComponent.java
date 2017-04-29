package com.mygdx.pmd.model.components;

import com.badlogic.gdx.graphics.g2d.*;
import com.mygdx.pmd.model.Entity.*;

import static com.mygdx.pmd.screens.DungeonScreen.PPM;

/**
 * Created by Cameron on 4/23/2017.
 */
public class RenderComponent implements Component {
    private Entity entity;

    //TODO make it so that it takes in multiple sprites and renders in order

    protected PositionComponent pc;
    private Sprite sprite;

    public RenderComponent(Entity entity) {
        this.entity = entity;
        this.pc = entity.getComponent(PositionComponent.class);
    }

    public void render(SpriteBatch batch) {
        assert pc != null : "Position Component must be initialized before entity can be rendered";
        assert sprite != null : "Sprite cannot be null";

        if (pc == null || sprite == null) {
            return;
        }

        batch.draw(sprite, pc.x / PPM, pc.y / PPM, sprite.getWidth() / PPM, sprite.getHeight()
                / PPM);

        //should propagate down until it hits an entity with no children
        for (Entity child : entity.children) {
            RenderComponent rc = child.getComponent(RenderComponent.class);
            if(rc == null){
                System.out.println("hello");
            }
            rc.render(batch);
        }
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
}
