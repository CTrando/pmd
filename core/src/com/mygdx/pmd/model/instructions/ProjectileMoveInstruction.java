package com.mygdx.pmd.model.instructions;

import com.mygdx.pmd.model.Entity.Projectile.Projectile;
import com.mygdx.pmd.model.Tile.Tile;
import com.mygdx.pmd.model.components.*;
import com.mygdx.pmd.model.instructions.*;


/**
 * Created by Cameron on 11/17/2016.
 */
public class ProjectileMoveInstruction implements Instruction {

    Projectile projectile;
    Tile[][] tileBoard;

    private PositionComponent pc;
    public ProjectileMoveInstruction(Projectile projectile) {
        this.projectile = projectile;
        this.tileBoard = projectile.tileBoard;
        this.pc = projectile.pc;
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
                projectile.pc.y += projectile.mc.getSpeed();
                break;
            case down:
                projectile.pc.y -= projectile.mc.getSpeed();
                break;
            case right:
                projectile.pc.x += projectile.mc.getSpeed();
                break;
            case left:
                projectile.pc.x -= projectile.mc.getSpeed();
                break;
        }
        Tile tile = Tile.getTileAt(projectile.pc.x, projectile.pc.y, tileBoard);
        if(projectile.equals(tile)){
            pc.setCurrentTile(tile);
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
