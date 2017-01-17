package com.mygdx.pmd.model.Entity.Pokemon;

import com.mygdx.pmd.controller.Controller;
import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.model.Behavior.*;
import com.mygdx.pmd.model.Behavior.Pokemon.PokeMob.MobPathFindingBehavior;
import com.mygdx.pmd.model.Behavior.Pokemon.PokemonAnimationBehavior;
import com.mygdx.pmd.model.Behavior.Entity.MoveSlowBehavior;
import com.mygdx.pmd.model.Behavior.Pokemon.PokeMob.MobAttackLogicBehavior;
import com.mygdx.pmd.model.Behavior.Pokemon.PokeMob.MobMovementLogicBehavior;
import com.mygdx.pmd.model.Tile.Tile;
import com.mygdx.pmd.utils.observers.MovementObserver;

public class PokemonMob extends Pokemon {

    public PokemonMob(Controller controller, int x, int y, PokemonName pokemonName) {
        super(controller, x, y, pokemonName);
        this.aggression = Aggression.aggressive;

        behaviors[BaseBehavior.PATHFIND_BEHAVIOR] = new MobPathFindingBehavior(this);
        behaviors[BaseBehavior.LOGIC_BEHAVIOR] = new MobMovementLogicBehavior(this);
        behaviors[BaseBehavior.ATTACK_BEHAVIOR] = new MobAttackLogicBehavior(this);
        behaviors[BaseBehavior.MOVE_BEHAVIOR] = new MoveSlowBehavior(this);
        behaviors[BaseBehavior.ANIMATION_BEHAVIOR] = new PokemonAnimationBehavior(this);
    }

    @Override
    public boolean isLegalToMoveTo(Tile tile){
        if (tile == null) return false;

        if (tile.hasDynamicEntity())
            return false;
        if (!tile.isWalkable)
            return false;
        return true;
    }

    @Override
    public void registerObservers() {
        observers[0] = new MovementObserver(this);
    }
}
