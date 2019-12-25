package com.mygdx.pmd.model.Entity.tile;

import com.badlogic.ashley.core.Entity;
import com.mygdx.pmd.model.components.PositionComponent;
import com.mygdx.pmd.model.components.RenderComponent;
import com.mygdx.pmd.utils.AssetManager;
import com.mygdx.pmd.utils.Constants;

public class Tile extends Entity {

    public Tile(int x, int y) {
        int normalX = x - (Constants.TILE_SIZE / 2);
        int normalY = y - (Constants.TILE_SIZE / 2);

        add(new RenderComponent(AssetManager.getInstance().getSprite("bricktile")));
        add(new PositionComponent(normalX * Constants.TILE_SIZE, normalY * Constants.TILE_SIZE));
    }
}
