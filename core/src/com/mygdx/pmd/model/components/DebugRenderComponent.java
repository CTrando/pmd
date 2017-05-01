package com.mygdx.pmd.model.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.pmd.model.Entity.*;
import com.mygdx.pmd.model.Entity.Pokemon.*;
import com.mygdx.pmd.screens.DungeonScreen;
import com.mygdx.pmd.utils.Constants;

import static com.mygdx.pmd.screens.DungeonScreen.PPM;

/**
 * Created by Cameron on 4/30/2017.
 */
public class DebugRenderComponent extends RenderComponent {
    public DebugRenderComponent(Pokemon pokemon) {
        super(pokemon);
    }

    @Override
    public void render(SpriteBatch batch) {
        super.renderSelf(batch);
        for(Entity child: entity.children) {
            PositionComponent pc = child.getComponent(PositionComponent.class);
            DungeonScreen.sRenderer.setColor(Color.RED);
            DungeonScreen.sRenderer.rect(pc.x/PPM, pc.y/PPM, Constants.TILE_SIZE/PPM, Constants.TILE_SIZE/PPM);
        }
    }
}
