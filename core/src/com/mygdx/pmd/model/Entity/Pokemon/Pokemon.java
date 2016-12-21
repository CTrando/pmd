package com.mygdx.pmd.model.Entity.Pokemon;


import com.mygdx.pmd.controller.Controller;
import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.model.Entity.Entity;
import com.mygdx.pmd.model.Entity.Projectile.Projectile;
import com.mygdx.pmd.model.Tile.Tile;
import com.mygdx.pmd.utils.*;


public abstract class Pokemon extends Entity {
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

    @Override
    public void update(){
        super.update();
        if(hp <= 0) shouldBeDestroyed = true;
        if(shouldBeDestroyed) {
            controller.removeEntity(this);
            if (this instanceof PokemonPlayer) controller.controllerScreen.game.dispose();
            System.out.println("WOE IS ME I AM DEAD");
            this.dispose();
        }
    }

    public void attack(Move move){
        this.projectile = new Projectile(this, move);
        controller.addEntity(this.projectile);
    }

    public boolean isVisible(){
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
            try {
                Tile tile = tileBoard[currentTile.row + i * rOffset][currentTile.col + i * cOffset];

                if (tile.hasEntity() && tile != currentTile) {
                    return true;
                }
            } catch(ArrayIndexOutOfBoundsException e){}
        }
        return false;
    }

    public void dispose(){
        this.currentTile.removeEntity(this);
    }
}
