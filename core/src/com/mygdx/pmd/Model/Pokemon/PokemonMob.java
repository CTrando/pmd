package com.mygdx.pmd.Model.Pokemon;

import com.badlogic.gdx.utils.Array;
import com.mygdx.pmd.Controller.Controller;
import com.mygdx.pmd.Enumerations.*;
import com.mygdx.pmd.Model.Behavior.*;
import com.mygdx.pmd.Model.TileType.Tile;
import com.mygdx.pmd.utils.AI.ShortestPath;

public class PokemonMob extends Pokemon {

    public PokemonMob(Controller controller, int x, int y, PokemonName pokemonName) {
        super(controller, x, y, pokemonName);

        this.aggression = Aggression.aggressive;

        behaviors[Behavior.LOGIC_BEHAVIOR] = new MobMovementLogicBehavior(this);
        behaviors[Behavior.ATTACK_BEHAVIOR] = new MobAttackLogicBehavior(this);
        behaviors[Behavior.MOVE_BEHAVIOR] = new MoveSlowBehavior(this);
        behaviors[Behavior.ANIMATION_BEHAVIOR] = new AnimationBehavior(this);

    }

    @Override
    public boolean isLegalToMoveTo(Tile tile){
        if (tile == null) return false;

        if (tile.hasEntity())
            return false;
        if (!tile.isWalkable)
            return false;
        return true;
    }
}
