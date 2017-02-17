/*
package com.mygdx.pmd.model.Entity.Pokemon;

import com.badlogic.gdx.utils.Array;
import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.model.Entity.*;
import com.mygdx.pmd.model.Floor.*;
import com.mygdx.pmd.model.Tile.Tile;
import com.mygdx.pmd.utils.AI.PathFind;
import com.mygdx.pmd.utils.AI.ShortestPath;
import com.mygdx.pmd.utils.AI.Wander;
import com.mygdx.pmd.utils.observers.MovementObserver;

public class PokemonMob extends Pokemon {
    public PathFind pathFind;

    public Wander wander;
    public ShortestPath sPath;
    public Array<Tile> path;

    public PokemonMob(Floor floor, PokemonName name){
        this(floor, 0, 0, name);
    }

    public PokemonMob(Floor floor, int x, int y, PokemonName pokemonName) {
        super(floor, x, y, pokemonName);
        //this.aggression = Aggression.passive;
        this.target = floor.getPlayer();

        wander = new Wander(this);
        sPath = new ShortestPath(this);

        pathFind = wander;
        path = new Array<Tile>();
    }

    public boolean canMove(){
        return true;
    }

    @Override
    public boolean isLegalToMoveTo(Tile tile) {
        if (tile == null) return false;

        if (tile.hasEntity())
            return false;
        if (!tile.isWalkable)
            return false;
        return true;
    }

    public boolean isWithinRange(Entity pokemon) {
        int dR = this.getCurrentTile().row - pokemon.getCurrentTile().row;
        int dC = this.getCurrentTile().col - pokemon.getCurrentTile().col;

        if (dR * dR + dC * dC > 400) return false;
        return true;
    }

    public boolean canAttack() {
        return false;
        //return canSeeEnemy() && aggression == Aggression.aggressive;
    }

    @Override
    public void registerObservers() {
        observers[0] = new MovementObserver(this);
    }

    //need to fix for single responsibility principle
    public void takeDamage(Entity aggressor, int damage) {
        super.takeDamage(aggressor, damage);
        //this.aggression = Aggression.aggressive;
        this.target = aggressor;
    }

    @Override
    public String toString() {
        return super.toString() + " mob";
    }

    @Override
    public void dispose() {
        super.dispose();

        this.setTurnState(Turn.COMPLETE);
        System.out.println("WOE IS ME I AM DEAD");
    }
}
*/
