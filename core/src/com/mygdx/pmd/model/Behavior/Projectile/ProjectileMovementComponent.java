/*
package com.mygdx.pmd.model.Behavior.Projectile;

import com.mygdx.pmd.model.Behavior.*;
import com.mygdx.pmd.model.Entity.Projectile.Projectile;
import com.mygdx.pmd.model.Tile.Tile;


*/
/**
 * Created by Cameron on 11/17/2016.
 *//*

public class ProjectileMovementComponent extends Component {

    Projectile projectile;
    public ProjectileMovementComponent(Projectile projectile) {
        super(projectile);
        projectile = (Projectile) entity;
    }

    @Override
    public void update() {
        assert projectile.direction != null: "The direction in projectile movement is null";

        switch (projectile.direction) {
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
        Tile tile = Tile.getTileAt(projectile.x, projectile.y, projectile.tileBoard);
        if(projectile.equals(tile)){
            projectile.setCurrentTile(tile);
        }
    }
}
*/
