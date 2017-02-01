package com.mygdx.pmd.model.Entity.Pokemon;

import com.badlogic.gdx.utils.Array;
import com.mygdx.pmd.controller.Controller;
import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.model.Behavior.Pokemon.PokemonAnimationBehavior;
import com.mygdx.pmd.model.Behavior.Pokemon.PokeMob.MobLogic;
import com.mygdx.pmd.model.Entity.*;
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

    public PokemonMob(Controller controller, int x, int y, PokemonName pokemonName) {
        super(controller, x, y, pokemonName);
        this.aggression = Aggression.passive;
        this.target = controller.pokemonPlayer;

        wander = new Wander(this);
        sPath = new ShortestPath(this);
        //pathFind = new Wander(this);
        pathFind = wander;
        path = new Array<Tile>();

        logic = new MobLogic(this);

        behaviors[0] = logic;
    }

    @Override
    public boolean isLegalToMoveTo(Tile tile) {
        if (tile == null) return false;

        if (tile.hasDynamicEntity())
            return false;
        if (!tile.isWalkable)
            return false;
        return true;
    }

    public boolean isWithinRange(Pokemon pokemon){
        int dR = this.currentTile.row - pokemon.currentTile.row;
        int dC = this.currentTile.col - pokemon.currentTile.col;

        if (dR*dR + dC*dC > 400) return false;
        return true;
    }

    public boolean canAttack() {
        return isEnemyInSight() && aggression == Aggression.aggressive;
    }

    public boolean canMove() {
        return true;
    }

    @Override
    public void registerObservers() {
        observers[0] = new MovementObserver(this);
    }

    //need to fix for single responsibility principle
    @Override
    public void takeDamage(DynamicEntity aggressor, int damage){
        super.takeDamage(aggressor, damage);
        this.aggression = Aggression.aggressive;
        this.target = aggressor;
    }
}
