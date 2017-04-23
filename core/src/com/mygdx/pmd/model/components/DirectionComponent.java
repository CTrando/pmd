package com.mygdx.pmd.model.components;

import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.model.Entity.*;
import com.mygdx.pmd.model.Tile.*;

/**
 * Created by Cameron on 4/16/2017.
 */
public class DirectionComponent implements Component {
    private Direction direction;
    private Entity entity;
    private PositionComponent pc;

    public DirectionComponent(Entity entity) {
        this.entity = entity;
        this.direction = Direction.down;
        this.pc = entity.getComponent(PositionComponent.class);
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setDirection(Tile tile) {
        Tile currentTile = pc.getCurrentTile();

        if (currentTile.col > tile.col) {
            setDirection(Direction.left);
            return;
        }
        if (currentTile.col < tile.col) {
            setDirection(Direction.right);
            return;
        }
        if (currentTile.row > tile.row) {
            setDirection(Direction.down);
            return;
        }
        if (currentTile.row < tile.row) {
            setDirection(Direction.up);
            return;
        }
    }
}
