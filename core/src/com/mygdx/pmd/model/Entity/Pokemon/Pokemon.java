package com.mygdx.pmd.model.Entity.Pokemon;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.*;
import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.interfaces.TurnBased;
import com.mygdx.pmd.model.Behavior.BaseBehavior;
import com.mygdx.pmd.model.Behavior.Entity.*;
import com.mygdx.pmd.model.Behavior.Pokemon.*;
import com.mygdx.pmd.model.Entity.DynamicEntity;
import com.mygdx.pmd.model.Entity.Projectile.Projectile;
import com.mygdx.pmd.model.Floor.*;
import com.mygdx.pmd.model.Tile.GenericTile;
import com.mygdx.pmd.model.Tile.Tile;
import com.mygdx.pmd.utils.*;

public abstract class Pokemon extends DynamicEntity implements TurnBased {
    public PAnimation currentAnimation;
    public BaseBehavior attackBehavior;
    public MoveBehavior moveBehavior;

    public Array<DynamicEntity> children;

    public DynamicEntity target;

    public PokemonBehavior logic;
    private PokemonName pokemonName;

    public Array<Move> moves;
    public Move currentMove;

    protected Pokemon(Floor floor, int x, int y, PokemonName pokemonName) {
        super(floor, x, y);
        this.direction = Direction.down;
        this.setActionState(Action.IDLE);

        this.children = new Array<DynamicEntity>();

        //initialize moves and add default move
        moves = new Array<Move>(4);
        moves.add(Move.SCRATCH);

        this.pokemonName = pokemonName;
        this.loadAnimations(pokemonName);

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

        if (!tile.isWalkable) return false;

        return true;
    }

    @Override
    public void update() {
        super.update();

        for(DynamicEntity child: children){
            child.update();
        }

        updateCurrentTile();
        setFacingTileBasedOnDirection(direction);
    }

    @Override
    public void render(SpriteBatch batch){
        super.render(batch);

        for(DynamicEntity child: children){
            child.render(batch);
        }
    }

    public void attack(){
        this.currentMove = moves.random();
        Projectile projectile = new Projectile(this, currentMove);
        this.children.add(projectile);
    }

    public void attack(Move move) {
        this.currentMove = move;
        Projectile projectile = new Projectile(this, currentMove);
        this.children.add(projectile);
    }

    public void randomizeLocation() {
        Tile random = floor.chooseUnoccupiedTile();

        if (random.isWalkable) {
            this.setNextTile(random);
            this.setCurrentTile(random);
            this.possibleNextTile = null;
        } else randomizeLocation();

        this.setActionState(Action.IDLE);
        this.setTurnState(Turn.COMPLETE);
    }

    protected boolean canSeeEnemy() {
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
        for (int i = 1; i < Constants.VISIBILITY_RANGE; i++) {
            //these are the rules for viewing things
            try {
                Tile tile = tileBoard[getCurrentTile().row + i * rOffset][getCurrentTile().col + i * cOffset];
                if (tile instanceof GenericTile) return false;

                if(tile == getCurrentTile()) return false;

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

    public String toString(){
        return this.pokemonName.toString();
    }

    public void dispose() {
        this.getCurrentTile().removeEntity(this);
    }
}
