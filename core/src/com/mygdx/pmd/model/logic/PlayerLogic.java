package com.mygdx.pmd.model.logic;

import com.mygdx.pmd.PMD;
import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.interfaces.Movable;
import com.mygdx.pmd.model.Entity.*;
import com.mygdx.pmd.model.Entity.Pokemon.PokemonPlayer;
import com.mygdx.pmd.model.instructions.*;
import com.mygdx.pmd.utils.PUtils;

/**
 * Created by Cameron on 1/21/2017.
 */
public class PlayerLogic implements Logic {
    private PokemonPlayer player;

    public PlayerLogic(PokemonPlayer player) {
        this.player = player;
    }

    @Override
    public void execute() {
        if (player.getHP() <= 0) {
            player.shouldBeDestroyed = true;
        }

        if (canAct()) {
            player.handleInput();
            player.setFacingTile(player.getDirection());

            if (canAttack()) {
                this.attack();
            } else if (canMove()) {
                this.move();
            }
        }
    }

    private boolean canAttack() {
        return player.attacking;
    }

    private boolean canMove() {
        return player.isLegalToMoveTo(player.possibleNextTile) && player.getActionState() == Action.IDLE;
    }

    private void move() {
        player.setNextTile(player.possibleNextTile);
        player.possibleNextTile = null;

        if (player.getNextTile().hasEntityOfType(Movable.class)) {
            this.forceMove();
        }

        player.instructions.add(new MoveInstruction(player, player.getNextTile()));

        //this is to keep movement smooth
        player.setActionState(Action.MOVING);
        player.setTurnState(Turn.COMPLETE);

        if (PMD.isKeyPressed(Key.s)) {
            player.setSpeed(5);
        } else player.setSpeed(1);
    }

    private void attack(Move move) {
        player.instructions.add(new AttackInstruction(player, move));

        player.setActionState(Action.ATTACKING);
        player.setTurnState(Turn.PENDING);
    }

    private void attack() {
        attack(player.getMove());
    }

    private void forceMove() {
        for (DynamicEntity dEntity : PUtils.getObjectsOfType(DynamicEntity.class, player.getNextTile().getEntityList())) {
            if (dEntity != player) {
                dEntity.forceMoveToTile(player.getCurrentTile(), player.getDirection().getOpposite());
            }
        }
    }

    private boolean canAct(){
        return player.getTurnState() == Turn.WAITING && player.getActionState() == Action.IDLE && player.instructions.isEmpty() && player.currentInstruction == Entity.NO_INSTRUCTION;
    }
}
