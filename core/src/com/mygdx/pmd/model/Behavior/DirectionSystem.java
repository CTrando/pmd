package com.mygdx.pmd.model.Behavior;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.systems.IteratingSystem;
import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.model.Entity.*;
import com.mygdx.pmd.model.Tile.*;
import javafx.geometry.Pos;

/**
 * Created by Cameron on 2/16/2017.
 */
public class DirectionSystem extends IteratingSystem {

    private PositionComponent positionComponent;
    private DirectionComponent directionComponent;

    public DirectionSystem(){
        super(Family.all(PositionComponent.class, DirectionComponent.class).get());
        this.positionComponent = positionComponent;
        this.directionComponent = directionComponent;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

    }

    public void setFacingTile(Direction direction) {
        try {
            switch (direction) {
                case up:
                    positionComponent.facingTile = positionComponent.tileBoard[positionComponent.getCurrentTile().row + 1][positionComponent.getCurrentTile().col];
                    break;
                case down:
                    positionComponent.facingTile = positionComponent.tileBoard[positionComponent.getCurrentTile().row - 1][positionComponent.getCurrentTile().col];
                    break;
                case right:
                    positionComponent.facingTile = positionComponent.tileBoard[positionComponent.getCurrentTile().row][positionComponent.getCurrentTile().col + 1];
                    break;
                case left:
                    positionComponent.facingTile = positionComponent.tileBoard[positionComponent.getCurrentTile().row][positionComponent.getCurrentTile().col - 1];
                    break;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        }
    }

    public void setFacingTile(Tile tile) {
        positionComponent.facingTile = tile;
        if (this.isToLeft(tile))
            directionComponent.direction = Direction.right;
        if (this.isToRight(tile))
            directionComponent.direction = Direction.left;
        if (this.isAbove(tile))
            directionComponent.direction = Direction.down;
        if (this.isBelow(tile))
            directionComponent.direction = Direction.up;
    }

    private boolean isToRight(Tile tile) {
        return positionComponent.getCurrentTile().x > tile.x;
    }

    private boolean isToLeft(Tile tile) {
        return positionComponent.getCurrentTile().x < tile.x;
    }

    private boolean isAbove(Tile tile) {
        return positionComponent.getCurrentTile().y > tile.y;
    }

    private boolean isBelow(Tile tile) {
        return positionComponent.getCurrentTile().y < tile.y;
    }
}
