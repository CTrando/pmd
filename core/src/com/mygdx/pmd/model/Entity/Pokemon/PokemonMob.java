package com.mygdx.pmd.model.Entity.Pokemon;

import com.badlogic.gdx.utils.Array;
import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.model.components.*;
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
    }

    @Override
    public void init(){
        super.init();
        //components.put(RenderComponent.class, new DebugRenderComponent(this));
        components.put(CombatComponent.class, cc);
        this.cc = getComponent(CombatComponent.class);
        this.cc.setAggressionState(Aggression.passive);

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
                tile.isWalkable &&
                !tile.hasEntityWithComponent(MoveComponent.class));
    }

    public boolean isWithinRange(Entity entity) {
        int curRow = pc.getCurrentTile().row;
        int curCol = pc.getCurrentTile().col;

        PositionComponent otherPC = entity.getComponent(PositionComponent.class);

        int dR = curRow - otherPC.getCurrentTile().row;
        int dC = curCol - otherPC.getCurrentTile().col;

        return dR * dR + dC * dC <= 400;
    }

    @Override
    public String toString() {
        return super.toString() + " mob";
    }

    @Override
    public void dispose() {
        super.dispose();

        tc.setTurnState(Turn.COMPLETE);
        floor.getController().screen.getHud().getConsole().writeLine(toString() + " died at " + pc.getCurrentTile());
    }
}
