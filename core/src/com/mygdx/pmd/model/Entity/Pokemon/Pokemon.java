package com.mygdx.pmd.model.Entity.Pokemon;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.*;
import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.interfaces.TurnBased;
import com.mygdx.pmd.model.Behavior.*;
import com.mygdx.pmd.model.Behavior.Entity.*;
import com.mygdx.pmd.model.Behavior.Pokemon.*;
import com.mygdx.pmd.model.Entity.*;
import com.mygdx.pmd.model.Floor.*;
import com.mygdx.pmd.model.Tile.Tile;
import com.mygdx.pmd.utils.*;

public abstract class Pokemon extends Entity implements TurnBased {
    public Array<Entity> children;

    public Entity target;

    private PokemonName pokemonName;

    public Array<Move> moves;
    public Move currentMove;
    public HPComponent hpComponent;

    protected Pokemon(Floor floor, int x, int y, PokemonName pokemonName) {
        super(floor, x, y);
        this.direction = Direction.down;
        this.setActionState(Action.IDLE);

        this.children = new Array<Entity>();

        //initialize moves and add default move
        moves = new Array<Move>(4);
        moves.add(Move.SCRATCH);

        this.pokemonName = pokemonName;

        this.setTurnState(Turn.COMPLETE);
        this.isTurnBased = true;

        this.registerObservers();
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

    @Override
    public void update() {
        super.update();

        for (Entity child : children) {
            child.update();
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);

        for (Entity child : children) {
            child.render(batch);
        }
    }

    public void attack() {
        if(componentExists(Component.ATTACK)) {
            AttackComponent attackComponent = (AttackComponent) getComponent(Component.ATTACK);
            attackComponent.attack();
        }
    }

    public void attack(Move move) {
        if(componentExists(Component.ATTACK)) {
            AttackComponent attackComponent = (AttackComponent) getComponent(Component.ATTACK);
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
    }
}
