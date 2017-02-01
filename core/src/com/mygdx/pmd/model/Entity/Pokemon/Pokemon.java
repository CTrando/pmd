package com.mygdx.pmd.model.Entity.Pokemon;


import com.mygdx.pmd.controller.Controller;
import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.interfaces.Turnbaseable;
import com.mygdx.pmd.model.Behavior.BaseBehavior;
import com.mygdx.pmd.model.Behavior.Entity.*;
import com.mygdx.pmd.model.Behavior.Pokemon.*;
import com.mygdx.pmd.model.Entity.DynamicEntity;
import com.mygdx.pmd.model.Entity.Projectile.Projectile;
import com.mygdx.pmd.model.Tile.GenericTile;
import com.mygdx.pmd.model.Tile.Tile;
import com.mygdx.pmd.utils.PAnimation;

public abstract class Pokemon extends DynamicEntity implements Turnbaseable {
    public PAnimation currentAnimation;
    public BaseBehavior attackBehavior;
    public MoveBehavior moveBehavior;

    public DynamicEntity target;

    public PokemonName pokemonName;
    public Projectile projectile;

    public PokemonBehavior logic;

    protected Pokemon(Controller controller, int x, int y, PokemonName pokemonName) {
        super(controller, x, y);
        this.direction = Direction.down;
        this.setActionState(Action.IDLE);

        this.attackBehavior = new AttackBehavior(this);
        this.moveBehavior = new MoveSlowBehavior(this);

        this.setTurnState(Turn.COMPLETE);
        this.isTurnBased = true;

        behaviors[1] = new PokemonAnimationBehavior(this);
        this.registerObservers();
    }

    @Override
    public boolean isLegalToMoveTo(Tile tile) {
        if (tile == null) return false;

        if (tile.hasDynamicEntity()) {
            for (DynamicEntity dEntity : tile.dynamicEntities) {
                if (dEntity.isAggressive()) {
                    return false;
                }
            }
        }

        if (!tile.isWalkable)
            return false;
        return true;
    }

    @Override
    public void update() {
        super.update();
        updateCurrentTile();
        setFacingTileBasedOnDirection(direction);

       /* if (getActionState() == Action.IDLE) {
            behaviors[2] = this.noBehavior;
        }*/

        if (hp <= 0) shouldBeDestroyed = true;
        if (shouldBeDestroyed) {
            controller.addToRemoveList(this);
            if (this instanceof PokemonPlayer) controller.controllerScreen.game.dispose();
            System.out.println("WOE IS ME I AM DEAD");
            this.dispose();
        }
    }

    public void attack(Move move) {
        this.projectile = new Projectile(this, move);
        controller.directlyAddEntity(this.projectile);
    }

    public boolean isEnemyInSight() {
        if (this.aggression != Aggression.aggressive) return false;
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
        for (int i = 1; i < 5; i++) {
            //these are the rules for viewing things
            try {
                Tile tile = tileBoard[currentTile.row + i * rOffset][currentTile.col + i * cOffset];
                if (tile instanceof GenericTile) return false;

                if(tile == currentTile) return false;

                for(DynamicEntity dEntity: tile.dynamicEntities){
                    if(dEntity == target){
                        return true;
                    }
                }

            } catch (ArrayIndexOutOfBoundsException e) {
            }
        }
        return false;
    }

    public void dispose() {
        this.currentTile.removeEntity(this);
    }
}
