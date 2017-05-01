package com.mygdx.pmd.model.instructions;

import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.model.Entity.*;
import com.mygdx.pmd.model.Entity.Pokemon.*;
import com.mygdx.pmd.model.Entity.Projectile.*;
import com.mygdx.pmd.model.components.*;

/**
 * Created by Cameron on 2/22/2017.
 */
public class AttackInstruction implements Instruction {
    private Pokemon pokemon;
    private Move move;
    private boolean isFinished;

    private ActionComponent ac;
    private TurnComponent tc;
    private DirectionComponent dc;
    private AnimationComponent anc;

    public AttackInstruction(Pokemon pokemon, Move move) {
        this.pokemon = pokemon;
        this.move = move;
        if(this.move == null){
            this.move = pokemon.getRandomMove();
        }
        dc = pokemon.dc;
        ac = pokemon.ac;
        tc = pokemon.tc;
        anc = pokemon.anc;
    }

    @Override
    public void onInit() {
        ac.setActionState(Action.ATTACKING);

        Projectile projectile = ProjectileFactory.createProjectile(pokemon, move);
        pokemon.children.add(projectile);
    }

    @Override
    public void onFinish() {
        tc.setTurnState(Turn.COMPLETE);
        ac.setActionState(Action.IDLE);
        anc.getCurrentAnimation().clear();
        anc.setCurrentAnimation(dc.getDirection().toString()+"idle");

        pokemon.attacking = false;
        pokemon.children.clear();
    }

    @Override
    public void execute() {
        if(anc.isAnimationFinished()) {
            for (Entity entity : pokemon.children) {
                if (entity.shouldBeDestroyed) {
                    isFinished = true;
                }
            }
        }
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }
}
