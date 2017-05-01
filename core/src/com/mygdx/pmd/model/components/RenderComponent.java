package com.mygdx.pmd.model.components;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.Array;
import com.mygdx.pmd.model.Entity.*;

import static com.mygdx.pmd.screens.DungeonScreen.PPM;

/**
 * Created by Cameron on 4/23/2017.
 */
public class RenderComponent implements Component {
    protected Entity entity;

    public static final int FOREGROUND = 1;
    public static final int BACKGROUND = 0;

    protected PositionComponent pc;
    private Array<Sprite> sprites;

    public RenderComponent(Entity entity) {
        this.entity = entity;
        this.pc = entity.getComponent(PositionComponent.class);
        this.sprites = new Array<Sprite>();
        this.sprites.setSize(10);
    }

    public void render(SpriteBatch batch) {
        renderSelf(batch);
        renderChildren(batch);
    }

    protected void renderSelf(SpriteBatch batch) {
        assert pc != null : "Position Component must be initialized before entity can be rendered";
        assert sprites != null : "Sprite cannot be null";

        if (pc == null || sprites.size <= 0) {
            return;
        }

        for(Sprite sprite: sprites) {
            if(sprite != null) {
                batch.draw(sprite, pc.x / PPM, pc.y / PPM, sprite.getWidth() / PPM, sprite.getHeight()
                        / PPM);
            }
        }

    }

    protected void renderChildren(SpriteBatch batch){
        //should propagate down until it hits an entity with no children
        for (Entity child : entity.children) {
            RenderComponent rc = child.getComponent(RenderComponent.class);
            if(rc == null){
                System.out.println("hello");
            }
            rc.render(batch);
        }
    }

    public void setSprite(Sprite sprite){
        this.sprites.set(FOREGROUND, sprite);
    }

    public void setSprite(Sprite sprite, int ZINDEX) {
        this.sprites.set(ZINDEX, sprite);
    }
}
