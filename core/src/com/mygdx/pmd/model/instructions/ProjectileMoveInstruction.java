package com.mygdx.pmd.model.instructions;

import com.mygdx.pmd.model.Entity.Projectile.Projectile;
import com.mygdx.pmd.model.Tile.Tile;
import com.mygdx.pmd.model.instructions.*;


/**
 * Created by Cameron on 11/17/2016.
 */
public class ProjectileMoveInstruction implements Instruction {

    Projectile projectile;
    Tile[][] tileBoard;
    public ProjectileMoveInstruction(Projectile projectile) {
        this.projectile = projectile;
        this.tileBoard = projectile.tileBoard;
    }

    @Override
    public void onInit() {

    }

    @Override
    public void onFinish() {

    }

    @Override
    public void execute() {
        assert projectile.dc.getDirection() != null: "The direction in projectile movement is null";

        switch (projectile.dc.getDirection()) {
            case up:
                projectile.y += projectile.speed;
                break;
            case down:
                projectile.y -= projectile.speed;
                break;
            case right:
                projectile.x += projectile.speed;
                break;
            case left:
                projectile.x -= projectile.speed;
                break;
        }
        Tile tile = Tile.getTileAt(projectile.x, projectile.y, tileBoard);
        if(projectile.equals(tile)){
            projectile.setCurrentTile(tile);
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
