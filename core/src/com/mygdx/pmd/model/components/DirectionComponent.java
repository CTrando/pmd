package com.mygdx.pmd.model.components;

import com.badlogic.ashley.core.Component;
import com.mygdx.pmd.enums.Direction;

/**
 * Created by Cameron on 4/16/2017.
 */
public class DirectionComponent implements Component {
    private Direction direction;

    public DirectionComponent() {
        this(Direction.down);
    }

    public DirectionComponent(Direction d) {
        this.direction = d;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
