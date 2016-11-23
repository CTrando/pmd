package com.mygdx.pmd.Model.Entity.Pokemon;

import com.mygdx.pmd.Controller.Controller;
import com.mygdx.pmd.Enumerations.*;
import com.mygdx.pmd.Model.Behavior.*;
import com.mygdx.pmd.Model.Behavior.Entity.AnimationBehavior;
import com.mygdx.pmd.Model.Behavior.Entity.MoveSlowBehavior;
import com.mygdx.pmd.Model.Behavior.Pokemon.PokeMob.MobAttackLogicBehavior;
import com.mygdx.pmd.Model.Behavior.Pokemon.PokeMob.MobMovementLogicBehavior;
import com.mygdx.pmd.Model.Tile.Tile;

public class PokemonMob extends Pokemon {

    public PokemonMob(Controller controller, int x, int y, PokemonName pokemonName) {
        super(controller, x, y, pokemonName);

        this.aggression = Aggression.aggressive;

        behaviors[BaseBehavior.LOGIC_BEHAVIOR] = new MobMovementLogicBehavior(this);
        behaviors[BaseBehavior.ATTACK_BEHAVIOR] = new MobAttackLogicBehavior(this);
        behaviors[BaseBehavior.MOVE_BEHAVIOR] = new MoveSlowBehavior(this);
        behaviors[BaseBehavior.ANIMATION_BEHAVIOR] = new AnimationBehavior(this);

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
