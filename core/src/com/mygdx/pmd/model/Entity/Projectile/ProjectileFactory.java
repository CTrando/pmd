package com.mygdx.pmd.model.Entity.Projectile;

import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.model.Entity.Pokemon.*;
import com.mygdx.pmd.model.Tile.*;
import com.mygdx.pmd.model.components.*;
import com.mygdx.pmd.model.instructions.*;
import com.mygdx.pmd.utils.PAnimation;

import javax.swing.text.Position;

/**
 * Created by Cameron on 4/29/2017.
 */
public class ProjectileFactory {

    public static Projectile createProjectile(Pokemon parent, Move move){
        Projectile projectile = new Projectile(parent, move);
        loadAnimations(projectile, move);

        PositionComponent pc = projectile.getComponent(PositionComponent.class);
        MoveComponent mc = new MoveComponent(projectile);
        ActionComponent ac = new ActionComponent(projectile);
        DirectionComponent dc = new DirectionComponent(projectile);
        RenderComponent rc = new RenderComponent(projectile);
        AnimationComponent anc = projectile.getComponent(AnimationComponent.class);

        projectile.components.put(PositionComponent.class, pc);
        projectile.components.put(MoveComponent.class, mc);
        projectile.components.put(ActionComponent.class, ac);
        projectile.components.put(DirectionComponent.class, dc);
        projectile.components.put(RenderComponent.class, rc);

        pc.setCurrentTile(parent.mc.getFacingTile());
        dc.setDirection(parent.dc.getDirection());
        mc.setNextTile(findFutureTile(projectile, dc.getDirection()));
        mc.setSpeed(move.speed);

        if (move.isRanged()) {
            ac.setActionState(Action.MOVING);
            projectile.instructions.add(new MoveInstruction(projectile, mc.getNextTile()));
            projectile.instructions.add(new CollideInstruction(projectile));
        } else {
            ac.setActionState(Action.IDLE);
            projectile.instructions.add(new CollideInstruction(projectile));
        }

        projectile.init();
        return projectile;
    }

    private static void loadAnimations(Projectile projectile, Move move){
        //TODO fix loading particle effects with projectiles render component loads too soon so PE is null there
        AnimationComponent anc = new AnimationComponent(projectile);
        PAnimation animation = new PAnimation("attack", move.projectileMovementAnimation, 20, true);
        anc.putAnimation("up", animation);
        anc.putAnimation("down", animation);
        anc.putAnimation("right", animation);
        anc.putAnimation("left", animation);

        anc.putAnimation("upidle", animation);
        anc.putAnimation("downidle", animation);
        anc.putAnimation("leftidle", animation);
        anc.putAnimation("rightidle", animation);

        animation = new PAnimation("death", move.projectileCollisionAnimation, move.animationLength,
                                   false);
        anc.putAnimation("death", animation);
        projectile.components.put(AnimationComponent.class, anc);
    }

    private static Tile findFutureTile(Projectile projectile, Direction direction){
        PositionComponent pc = projectile.getComponent(PositionComponent.class);

        Tile[][] tileBoard = projectile.tileBoard;
        Move move = projectile.move;

        int row = pc.getCurrentTile().row;
        int col = pc.getCurrentTile().col;

        switch (direction) {
            case up:
                for (int i = 0; i < move.range; i++) {
                    Tile tile = tileBoard[row + i][col];
                    if (projectile.isValidTarget(tile) || i == move.range - 1) {
                        return tile;
                    }
                }
                break;
            case down:
                for (int i = 0; i < move.range; i++) {
                    Tile tile = tileBoard[row - i][col];
                    if (projectile.isValidTarget(tile) || i == move.range - 1) {
                        return tile;
                    }
                }
                break;
            case left:
                for (int j = 0; j < move.range; j++) {
                    Tile tile = tileBoard[row][col - j];
                    if (projectile.isValidTarget(tile) || j == move.range - 1) {
                        return tile;
                    }
                }
                break;
            case right:
                for (int j = 0; j < move.range; j++) {
                    Tile tile = tileBoard[row][col + j];
                    if (projectile.isValidTarget(tile) || j == move.range - 1) {
                        return tile;
                    }
                }
                break;
        }
        return null;
    }
}
