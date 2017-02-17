package com.mygdx.pmd.model.Entity.Pokemon;


import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.*;
import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.interfaces.*;
import com.mygdx.pmd.model.Behavior.*;
import com.mygdx.pmd.model.Entity.*;
import com.mygdx.pmd.model.Floor.*;
import com.mygdx.pmd.model.Tile.Tile;

import java.util.HashMap;

public abstract class Pokemon extends Entity implements Renderable {
    public Array<Entity> children;

    public Entity target;

    private PokemonName pokemonName;

    protected Pokemon(Floor floor, int x, int y, PokemonName pokemonName) {
        this.children = new Array<Entity>();
        this.pokemonName = pokemonName;
    }

    public boolean isLegalToMoveTo(Tile tile) {
        if (tile == null) return false;

        if (tile.hasEntity()) {
            /*for (DynamicEntity entity : tile.dynamicEntities) {
                if (entity.isAggressive()) {
                    return false;
                }
            }*/
        }

        if (!tile.isWalkable) return false;

        return true;
    }
/*
    @Override
    public void update() {
        super.update();

        for (Entity child : children) {
            child.update();
        }
    }*/

    public void render(SpriteBatch batch){
        RenderComponent rm = Mappers.rm.get(this);
        PositionComponent pm = Mappers.pm.get(this);

        batch.draw(rm.currentSprite, pm.x, pm.y);
    }

    /*@Override
    public void render(SpriteBatch batch) {
        super.render(batch);

        for (Entity child : children) {
            child.render(batch);
        }
    }*/

   /* public void attack() {
        if(componentExists(Component.ATTACK)) {
            AttackSystem attackComponent = (AttackSystem) getComponent(Component.ATTACK);
            attackComponent.attack();
        }
    }

    public void attack(Move move) {
        if(componentExists(Component.ATTACK)) {
            AttackSystem attackComponent = (AttackSystem) getComponent(Component.ATTACK);
            attackComponent.attack(move);
        }
    }

    public boolean canMove() {
        return isLegalToMoveTo(possibleNextTile);
    }

    // in here check if the component exists
    public boolean canAttack() {
        return false;
    }

    public void takeDamage(Entity aggressor, int damage) {
        hpComponent.setHp(hp - damage);
    }

    public String toString() {
        return this.pokemonName.toString();
    }

    public void dispose() {
        this.getCurrentTile().removeEntity(this);
    }*/
}
