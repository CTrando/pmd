package com.mygdx.pmd.model.Entity.Pokemon;


import com.mygdx.pmd.interfaces.Turnbaseable;
import com.mygdx.pmd.controller.Controller;
import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.model.Entity.DynamicEntity;
import com.mygdx.pmd.model.Entity.Entity;
import com.mygdx.pmd.model.Entity.Projectile.Projectile;
import com.mygdx.pmd.model.Tile.GenericTile;
import com.mygdx.pmd.model.Tile.Tile;
import com.mygdx.pmd.utils.*;


public abstract class Pokemon extends DynamicEntity implements Turnbaseable {
    public PAnimation currentAnimation;

    public PokemonName pokemonName;
    public Projectile projectile;

    protected Pokemon(Controller controller, int x, int y, PokemonName pokemonName) {
        super(controller, x, y);
        this.direction = Direction.down;
        this.pokemonName = pokemonName;
        this.setActionState(Action.IDLE);

        this.turnState = Turn.COMPLETE;
        this.isTurnBased = true;
        this.registerObservers();
    }

    @Override
    public boolean isLegalToMoveTo(Tile tile) {
        if (tile == null) return false;

        if(tile.hasDynamicEntity()) {
            for(Entity entity: tile.getEntityList()) {
                if(entity instanceof DynamicEntity) {
                    DynamicEntity dEntity = (DynamicEntity) entity;
                    if (dEntity.aggression == Aggression.aggressive)
                        return false;
                }
            }
        }

        if (!tile.isWalkable)
            return false;
        return true;
    }

    @Override
    public void update(){
        super.update();
        if(hp <= 0) shouldBeDestroyed = true;
        if(shouldBeDestroyed) {
            controller.addToRemoveList(this);
            if (this instanceof PokemonPlayer) controller.controllerScreen.game.dispose();
            System.out.println("WOE IS ME I AM DEAD");
            this.dispose();
        }
    }

    public void attack(Move move){
        this.projectile = new Projectile(this, move);
        controller.directlyAddEntity(this.projectile);
    }

    public boolean isEnemyInSight(){
        if(this.aggression != Aggression.aggressive) return false;
        int rOffset = 0;
        int cOffset = 0;

        switch(this.direction){
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
        for(int i = 1; i< 5; i++){
            //these are the rules for viewing things
            try {
                Tile tile = tileBoard[currentTile.row + i * rOffset][currentTile.col + i * cOffset];
                if(tile instanceof GenericTile) return false;

                if (tile.dynamicEntities.size > 0){
                    if(tile != currentTile && tile.containsAggressionType(Aggression.passive)) {
                        return true;
                    } else return false;
                }
            } catch(ArrayIndexOutOfBoundsException e){}
        }
        return false;
    }

    public void dispose(){
        this.currentTile.removeEntity(this);
    }
}
