package com.mygdx.pmd.model.Entity.tile;

import com.badlogic.ashley.core.Entity;
import com.mygdx.pmd.model.components.PositionComponent;
import com.mygdx.pmd.model.components.RenderComponent;
import com.mygdx.pmd.utils.AssetManager;
import com.mygdx.pmd.utils.Constants;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Tile extends Entity {
    private int fx;
    private int fy;
    private Set<Entity> fEntities;

    public Tile(int x, int y) {
        fEntities = new HashSet<>();
        fx = x;
        fy = y;

        add(new RenderComponent(AssetManager.getInstance().getSprite("bricktile")));
        add(new PositionComponent(x * Constants.TILE_SIZE, y * Constants.TILE_SIZE));
    }

    public void addEntity(Entity entity) {
        fEntities.add(entity);
    }

    public boolean removeEntity(Entity entity) {
        return fEntities.remove(entity);
    }

    public boolean isEmpty() {
        return fEntities.isEmpty();
    }

    public int getX() {
        return fx;
    }

    public int getY() {
        return fy;
    }
}
