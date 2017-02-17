package com.mygdx.pmd.model.Behavior;

import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.model.Entity.*;
import com.mygdx.pmd.model.Tile.*;

/**
 * Created by Cameron on 2/16/2017.
 */
public class DirectionComponent extends Component {

    public DirectionComponent(Entity entity){
        super(entity);
    }

    @Override
    public void update() {

    }

    public void setFacingTile(Direction direction) {
        try {
            switch (direction) {
                case up:
                    entity.facingTile = entity.tileBoard[entity.getCurrentTile().row + 1][entity.getCurrentTile().col];
                    break;
                case down:
                    entity.facingTile = entity.tileBoard[entity.getCurrentTile().row - 1][entity.getCurrentTile().col];
                    break;
                case right:
                    entity.facingTile = entity.tileBoard[entity.getCurrentTile().row][entity.getCurrentTile().col + 1];
                    break;
                case left:
                    entity.facingTile = entity.tileBoard[entity.getCurrentTile().row][entity.getCurrentTile().col - 1];
                    break;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        }
    }

    public void setFacingTile(Tile tile) {
        entity.facingTile = tile;
        if (this.isToLeft(tile))
            entity.direction = Direction.right;
        if (this.isToRight(tile))
            entity.direction = Direction.left;
        if (this.isAbove(tile))
            entity.direction = Direction.down;
        if (this.isBelow(tile))
            entity.direction = Direction.up;
    }

    private boolean isToRight(Tile tile) {
        return entity.getCurrentTile().x > tile.x;
    }

    private boolean isToLeft(Tile tile) {
        return entity.getCurrentTile().x < tile.x;
    }

    private boolean isAbove(Tile tile) {
        return entity.getCurrentTile().y > tile.y;
    }

    private boolean isBelow(Tile tile) {
        return entity.getCurrentTile().y < tile.y;
    }
}
