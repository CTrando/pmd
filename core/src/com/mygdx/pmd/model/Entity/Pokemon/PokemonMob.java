package com.mygdx.pmd.model.Entity.Pokemon;

import com.badlogic.gdx.utils.Array;
import com.mygdx.pmd.controller.Controller;
import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.model.Behavior.BaseBehavior;
import com.mygdx.pmd.model.Behavior.Entity.TestMoveSlow;
import com.mygdx.pmd.model.Behavior.Pokemon.PokeMob.MobAttackLogicBehavior;
import com.mygdx.pmd.model.Behavior.Pokemon.PokemonAnimationBehavior;
import com.mygdx.pmd.model.Behavior.Pokemon.MobLogic;
import com.mygdx.pmd.model.Tile.Tile;
import com.mygdx.pmd.utils.AI.PathFind;
import com.mygdx.pmd.utils.AI.Wander;
import com.mygdx.pmd.utils.observers.MovementObserver;

public class PokemonMob extends Pokemon {
    public BaseBehavior moveBehavior;
    public MobAttackLogicBehavior attackBehavior;
    public PathFind pathFind;
    public Array<Tile> path;

    public PokemonMob(Controller controller, int x, int y, PokemonName pokemonName) {
        super(controller, x, y, pokemonName);
        this.aggression = Aggression.aggressive;

        moveBehavior = new TestMoveSlow(this);
        attackBehavior = new MobAttackLogicBehavior(this);

        pathFind = new Wander(this);
        path = new Array<Tile>();

        logic = new MobLogic(this);

        behaviors[0] = logic;
        behaviors[1] = new PokemonAnimationBehavior(this);
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

    public void update() {
        super.update();

        if (getActionState() == Action.IDLE) {
            //behaviors[0] = new MobLogic(this);
            behaviors[2] = this.noBehavior;
        }
    }

    public boolean canAttack(){
        return isEnemyInSight();
    }

    public boolean canMove(){
        return true;
    }

    @Override
    public void registerObservers() {
        observers[0] = new MovementObserver(this);
    }
}
