package com.mygdx.pmd.Model.Entity.Pokemon;


import com.mygdx.pmd.Controller.Controller;
import com.mygdx.pmd.Enumerations.*;
import com.mygdx.pmd.Model.Behavior.BaseBehavior;
import com.mygdx.pmd.Model.Entity.Entity;
import com.mygdx.pmd.Model.Entity.Projectile.Projectile;
import com.mygdx.pmd.Model.Tile.Tile;
import com.mygdx.pmd.utils.*;


public abstract class Pokemon extends Entity {
    public Turn turnState = Turn.COMPLETE;
    public PAnimation currentAnimation;
    public Tile[][] tileBoard;
    public Action actionState = Action.IDLE;
    public PokemonName pokemonName;
    public Projectile projectile;

    protected Pokemon(Controller controller, int x, int y, PokemonName pokemonName) {
        super(controller, x, y);
        this.isTurnBased = true;
        this.direction = Direction.down;
        this.pokemonName = pokemonName;
    }

    @Override
    public void update() {
        for (int i = 0; i < behaviors.length; i++) {
            behaviors[i].execute();
        }
    }

    public void setBehavior(BaseBehavior behavior, int index) {
        behaviors[index] = behavior;
    }

    @Override
    public boolean isLegalToMoveTo(Tile tile) {
        if (tile == null) return false;

        if(tile.hasEntity()) {
            for(Entity entity: tile.getEntityList()) {
                if (entity.aggression == Aggression.aggressive)
                    return false;
            }
        }

        if (!tile.isWalkable)
            return false;
        return true;
    }
}
