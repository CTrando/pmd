package com.mygdx.pmd.model.Entity.Pokemon;

import com.badlogic.gdx.utils.Array;
import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.model.logic.MobLogic;
import com.mygdx.pmd.model.Entity.*;
import com.mygdx.pmd.model.Floor.*;
import com.mygdx.pmd.model.Tile.Tile;
import com.mygdx.pmd.utils.AI.PathFind;
import com.mygdx.pmd.utils.AI.ShortestPath;
import com.mygdx.pmd.utils.AI.Wander;

public class PokemonMob extends Pokemon {
    public PathFind pathFind;

    public Wander wander;
    public ShortestPath sPath;
    public Array<Tile> path;

    PokemonMob(Floor floor, PokemonName name) {
        this(floor, 0, 0, name);
    }

    PokemonMob(Floor floor, int x, int y, PokemonName pokemonName) {
        super(floor, x, y, pokemonName);
        this.aggression = Aggression.passive;
        this.target = floor.getPlayer();

        //pathfinding objects
        wander = new Wander(this);
        sPath = new ShortestPath(this);
        pathFind = wander;

        path = new Array<Tile>();
        logic = new MobLogic(this);
    }

    @Override
    public boolean isLegalToMoveTo(Tile tile) {
        return (tile != null &&
                tile.isWalkable);
    }

    public boolean isWithinRange(DynamicEntity pokemon) {
        int curRow = pc.getCurrentTile().row;
        int curCol = pc.getCurrentTile().col;

        int dR = curRow - pokemon.pc.getCurrentTile().row;
        int dC = curCol - pokemon.pc.getCurrentTile().col;

        if (dR * dR + dC * dC > 400) {
            return false;
        }
        return true;
    }

    @Override
    public void takeDamage(Pokemon parent, int damage) {
        super.takeDamage(parent, damage);
        this.aggression = Aggression.aggressive;
        this.target = parent;
    }

    @Override
    public String toString() {
        return super.toString() + " mob";
    }

    @Override
    public void dispose() {
        super.dispose();

        tc.setTurnState(Turn.COMPLETE);
        System.out.println("WOE IS ME I AM DEAD");
    }
}
