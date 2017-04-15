package com.mygdx.pmd.model.Entity.Pokemon;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.*;
import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.interfaces.*;
import com.mygdx.pmd.model.Entity.*;
import com.mygdx.pmd.model.Floor.*;
import com.mygdx.pmd.model.Tile.GenericTile;
import com.mygdx.pmd.model.Tile.Tile;
import com.mygdx.pmd.model.logic.*;
import com.mygdx.pmd.screens.DungeonScreen;
import com.mygdx.pmd.utils.*;

import java.util.Set;

import static com.mygdx.pmd.screens.DungeonScreen.PPM;

public abstract class Pokemon extends DynamicEntity implements TurnBaseable, Damageable, Aggressible, Logical {
    public Array<DynamicEntity> children;
    public DynamicEntity target;

    Logic logic;
    private PokemonName pokemonName;

    public Array<Move> moves;
    private Move move;

    public boolean attacking;

    Pokemon(Floor floor, int x, int y, PokemonName pokemonName) {
        super(floor, x, y);
        setHP(100);
        setTurnState(Turn.COMPLETE);
        setAggression(Aggression.passive);
        setFacingTile(getDirection());

        this.pokemonName = pokemonName;
        this.children = new Array<DynamicEntity>();

        //initialize moves and add default move
        moves = new Array<Move>(4);

        //default move
        moves.add(Move.SCRATCH);
    }

    /**
     * Possible error with this because of casting - will try to avoid later
     * @param tile
     * @return
     */
    @Override
    public boolean isLegalToMoveTo(Tile tile) {
        if (tile == null) return false;

        if (tile.hasEntityOfType(Movable.class)) {
            for (Aggressible aggressible : PUtils.getObjectsOfType(Aggressible.class, tile.getEntityList())) {
                if (aggressible.isAggressive()) {
                    return false;
                }
            }
        }
        return tile.isWalkable;
    }

    @Override
    public void update() {
        // have it run its logic first before it executes its instructions
        // may have negative repercussions
        this.runLogic();

        for(DynamicEntity child: children){
            child.update();
        }

        animationLogic.execute();
        super.update();
    }

    @Override
    public void render(SpriteBatch batch){

        if(getNextTile() != null && getNextTile() != getCurrentTile()){
            DungeonScreen.sRenderer.setColor(Color.RED);
            DungeonScreen.sRenderer.rect(getNextTile().x/PPM, getNextTile().y/PPM, Constants.TILE_SIZE/PPM, Constants
                    .TILE_SIZE/PPM);
        }

        for(DynamicEntity child: children){
            child.render(batch);
        }

        super.render(batch);
    }

    public void randomizeLocation() {
        Tile random = floor.chooseUnoccupiedTile();

        if (random.isWalkable) {
            this.setNextTile(random);

            this.removeFromTile();
            this.addToTile(random);
            this.setFacingTile(getDirection());

            this.setCurrentTile(random);
            this.possibleNextTile = null;
        } else randomizeLocation();
    }

    @Override
    public Aggression getAggression() {
        return aggression;
    }

    @Override
    public void setAggression(Aggression aggression) {
        this.aggression = aggression;
    }

    public boolean canSeeEnemy() {
        if (this.aggression != Aggression.aggressive) return false;
        int rOffset = 0;
        int cOffset = 0;

        switch (getDirection()) {
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
        for (int i = 1; i <= Constants.VISIBILITY_RANGE; i++) {
            //these are the rules for viewing things
            try {
                Tile tile = tileBoard[getCurrentTile().row + i * rOffset][getCurrentTile().col + i * cOffset];
                if (tile instanceof GenericTile) return false;

                if(tile == getCurrentTile()) return false;

                for(Entity entity: tile.getEntityList()){
                    if(entity == target){
                        return true;
                    }
                }

            } catch (ArrayIndexOutOfBoundsException e) {
            }
        }
        return false;
    }

    public void runLogic(){
        logic.execute();
    }

    @Override
    public void takeDamage(Pokemon parent, int damage) {
        this.setHP(this.getHP() - damage);
    }

    public int getHP() {
        return hp;
    }

    @Override
    public void setHP(int HP) {
        this.hp = HP;
        if (this.hp <= 0) {
            this.hp = 0;
        }

        if(this.hp > 100){
            this.hp = 100;
        }
    }

    public Turn getTurnState() {
        return turnState;
    }

    public void setTurnState(Turn turnState) {
        this.turnState = turnState;
    }

    public String toString(){
        return this.pokemonName.toString();
    }

    public void dispose() {
        this.getCurrentTile().removeEntity(this);
    }

    public Move getRandomMove() {
        return moves.random();
    }

    public Move getRandomRangedMove() {
        Array<Move> retMoves = new Array<Move>();
        for(Move move: moves){
            if(move.isRanged()){
                retMoves.add(move);
            }
        }
        return retMoves.random();
    }

    public Move getMove() {
        return move;
    }

    public void setMove(Move move){
        this.move = move;
    }

    public void reset(){
        super.reset();
        this.setTurnState(Turn.COMPLETE);
    }
}
