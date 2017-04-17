package com.mygdx.pmd.model.logic;

import com.mygdx.pmd.PMD;
import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.interfaces.*;
import com.mygdx.pmd.model.Entity.*;
import com.mygdx.pmd.model.Entity.Pokemon.PokemonPlayer;
import com.mygdx.pmd.model.Tile.*;
import com.mygdx.pmd.model.components.*;
import com.mygdx.pmd.model.instructions.*;
import com.mygdx.pmd.utils.PUtils;

/**
 * Created by Cameron on 1/21/2017.
 */
public class PlayerLogic extends PokemonLogic {
    private PokemonPlayer player;
    private MoveComponent mc;
    private ActionComponent ac;
    private TurnComponent tc;

    public PlayerLogic(PokemonPlayer player) {
        super(player);
        this.player = player;
        this.mc = player.mc;
        this.ac = player.ac;
        this.tc = player.tc;
    }

    @Override
    public void execute() {
        if (player.getHP() <= 0) {
            player.shouldBeDestroyed = true;
        }

        if (canAct()) {
            player.handleInput();
            mc.setFacingTile(dc.getDirection());

            if (canAttack()) {
                this.attack();
            } else if (canMove()) {
                this.move();
            }
        }
    }

    boolean canAttack() {
        return player.attacking;
    }

    boolean canMove() {
        return player.isLegalToMoveTo(mc.possibleNextTile) && ac.getActionState() == Action.IDLE;
    }

    void move() {
        mc.setNextTile(mc.possibleNextTile);
        mc.possibleNextTile = null;

        if (mc.getNextTile().hasEntityOfType(Movable.class)) {
            this.forceMove();
        }

        player.instructions.add(new MoveInstruction(player, mc.getNextTile()));

        //this is to keep movement smooth
        ac.setActionState(Action.MOVING);
        tc.setTurnState(Turn.COMPLETE);

        if (PMD.isKeyPressed(Key.s)) {
            mc.setSpeed(5);
        } else mc.setSpeed(1);
    }

    private void attack(Move move) {
        player.instructions.add(new AttackInstruction(player, move));

        ac.setActionState(Action.ATTACKING);
        tc.setTurnState(Turn.PENDING);
    }

    void attack() {
        attack(player.getMove());
    }

    @Override
    boolean isEnemyAdjacent() {
        return mc.getFacingTile().hasEntityOfType(Damageable.class);
    }

    @Override
    Tile findEnemyTile() {
        return null;
    }

    private void forceMove() {
        for (DynamicEntity dEntity : PUtils.getObjectsOfType(DynamicEntity.class, mc.getNextTile().getEntityList())) {
            if (dEntity != player) {
                dEntity.forceMoveToTile(mc.getCurrentTile(), dc.getDirection().getOpposite());
            }
        }
    }

    boolean canAct(){
        return tc.getTurnState() == Turn.WAITING && ac.getActionState() == Action.IDLE && player.instructions
                .isEmpty() && player.currentInstruction == Entity.NO_INSTRUCTION;
    }
}
