/*
package com.mygdx.pmd.model.Behavior;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.model.Behavior.Projectile.*;
import com.mygdx.pmd.model.Entity.Projectile.*;

*/
/**
 * Created by Cameron on 2/16/2017.
 *//*

public class AttackSystem extends IteratingSystem {
    ImmutableArray<Entity> entities;

    public AttackSystem(){
        super(Family.all(TurnComponent.class, ActionComponent.class, AttackComponent.class).get());
    }

    @Override
    public void addedToEngine(Engine engine){
        entities = engine.getEntitiesFor(Family.all(TurnComponent.class, ActionComponent.class, AttackComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TurnComponent turn = Mappers.tm.get(entity);
        ActionComponent action = Mappers.am.get(entity);

        if (action.getActionState() == Action.ATTACKING &&
                pokemon.children.size == 0 && pokemon.currentAnimation.isFinished()) {
            turn.setTurnState(Turn.COMPLETE);
            action.setActionState(Action.IDLE);
            pokemon.currentAnimation.clear();
        }

    }

    public void update() {
        if (actionComponent.getActionState() == Action.ATTACKING &&
                pokemon.children.size == 0 && pokemon.currentAnimation.isFinished()) {
            turnComponent.setTurnState(Turn.COMPLETE);
            actionComponent.setActionState(Action.IDLE);
            pokemon.currentAnimation.clear();
        }
    }

    public void attack() {
        attackComponent.currentMove = attackComponent.moves.random();
        attack(attackComponent.currentMove);
    }

    public void attack(Move move) {
        attackComponent.currentMove = move;
        Projectile projectile = new Projectile(pokemon);
        projectile.addComponent(ANIMATION, new ProjectileAnimationComponent(projectile));

        pokemon.children.add(projectile);
    }

    protected boolean canSeeEnemy() {
        */
/*if (this.aggression != Aggression.aggressive) return false;
        int rOffset = 0;
        int cOffset = 0;

        switch (this.direction) {
            case down:
                rOffset = -1;
                break;
            case up:
                rOffset = 1;
                break;
            case right:
                cOffset = 1;
                break;
            case left:
                cOffset = -1;
        }
        for (int i = 1; i < Constants.VISIBILITY_RANGE; i++) {
            //these are the rules for viewing things
            try {
                Tile tile = tileBoard[getCurrentTile().row + i * rOffset][getCurrentTile().col + i * cOffset];
                if (tile instanceof GenericTile) return false;

                if(tile == getCurrentTile()) return false;

                for(DynamicEntity entity: tile.dynamicEntities){
                    if(entity == target){
                        return true;
                    }
                }

            } catch (ArrayIndexOutOfBoundsException e) {
            }
        }*//*

        return false;
    }
}
*/
