package com.mygdx.pmd.model.Behavior.Entity;

import com.mygdx.pmd.enumerations.Action;
import com.mygdx.pmd.model.Behavior.*;
import com.mygdx.pmd.model.Entity.*;
import com.mygdx.pmd.model.Tile.*;

import java.awt.event.ComponentEvent;

/**
 * Created by Cameron on 1/20/2017.
 */
public class MoveComponent extends Component{
    public boolean isForcedMove;

    public MoveComponent(Entity entity) {
        super(entity);
    }

    public void update() {
        if (!entity.equals(entity.getNextTile())) {
            this.moveToTile(entity.getNextTile(), entity.speed);
        }

        if (entity.equals(entity.getCurrentTile())) {
            entity.setActionState(Action.IDLE);
        }

        updateCurrentTile();
    }

    private void move(int dx, int dy) {
        entity.x += dx;
        entity.y += dy;
    }

    public void moveToTile(Tile nextTile, int speed) {
        if (nextTile == null)
            return;

        if (entity.equals(nextTile)) {
            return;
        }

        if (entity.y > nextTile.y && entity.x > nextTile.x) {
            this.move(-speed, -speed);
        } else if (entity.y < nextTile.y && entity.x > nextTile.x) {
            this.move(-speed, speed);
        } else if (entity.y < nextTile.y && entity.x < nextTile.x) {
            this.move(speed, speed);
        } else if (entity.y > nextTile.y && entity.x < nextTile.x) {
            this.move(speed, -speed);
        } else if (entity.y > nextTile.y) {
            this.move(0, -speed);
        } else if (entity.y < nextTile.y) {
            this.move(0, speed);
        } else if (entity.x < nextTile.x) {
            this.move(speed, 0);
        } else if (entity.x > nextTile.x) {
            this.move(-speed, 0);
        }
    }

    public void forceMoveToTile(Tile nextTile) {
        entity.setNextTile(nextTile);
        isForcedMove = true;
    }

    public void updateCurrentTile(){
        Tile tile = Tile.getTileAt(entity.x, entity.y, entity.tileBoard);
        if(entity.equals(tile))
            entity.setCurrentTile(tile);
    }
}
