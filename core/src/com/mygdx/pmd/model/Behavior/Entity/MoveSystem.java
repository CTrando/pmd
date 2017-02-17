package com.mygdx.pmd.model.Behavior.Entity;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.systems.IteratingSystem;
import com.mygdx.pmd.enumerations.Action;
import com.mygdx.pmd.model.Behavior.*;
import com.mygdx.pmd.model.Entity.*;
import com.mygdx.pmd.model.Tile.*;

import java.awt.event.ComponentEvent;

/**
 * Created by Cameron on 1/20/2017.
 */
public class MoveSystem extends IteratingSystem {
    public boolean isForcedMove;
    private PositionComponent pm;
    private ActionComponent am;

    public MoveSystem() {
        super(Family.all(PositionComponent.class, ActionComponent.class).get());
    }


    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        pm = Mappers.pm.get(entity);
        am = Mappers.am.get(entity);

        if (!pm.equals(pm.getNextTile())) {
            this.moveToTile(pm.getNextTile(), pm.speed);
        }

        if (pm.equals(pm.getCurrentTile())) {
            am.setActionState(Action.IDLE);
        }

        updateCurrentTile();
    }

    private void move(int dx, int dy) {
        pm.x += dx;
        pm.y += dy;
    }

    public void moveToTile(Tile nextTile, int speed) {
        if (nextTile == null)
            return;

        if (pm.equals(nextTile)) {
            return;
        }

        if (pm.y > nextTile.y && pm.x > nextTile.x) {
            this.move(-speed, -speed);
        } else if (pm.y < nextTile.y && pm.x > nextTile.x) {
            this.move(-speed, speed);
        } else if (pm.y < nextTile.y && pm.x < nextTile.x) {
            this.move(speed, speed);
        } else if (pm.y > nextTile.y && pm.x < nextTile.x) {
            this.move(speed, -speed);
        } else if (pm.y > nextTile.y) {
            this.move(0, -speed);
        } else if (pm.y < nextTile.y) {
            this.move(0, speed);
        } else if (pm.x < nextTile.x) {
            this.move(speed, 0);
        } else if (pm.x > nextTile.x) {
            this.move(-speed, 0);
        }
    }

    public void forceMoveToTile(Tile nextTile) {
        pm.setNextTile(nextTile);
        isForcedMove = true;
    }

    public void updateCurrentTile(){
        Tile tile = Tile.getTileAt(pm.x, pm.y, pm.tileBoard);
        if(pm.equals(tile))
            pm.setCurrentTile(tile);
    }
}
