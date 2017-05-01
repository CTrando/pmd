package com.mygdx.pmd.model.logic;

import com.mygdx.pmd.PMD;
import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.interfaces.*;
import com.mygdx.pmd.model.Entity.*;
import com.mygdx.pmd.model.Entity.Pokemon.PokemonPlayer;
import com.mygdx.pmd.model.Tile.*;
import com.mygdx.pmd.model.components.*;
import com.mygdx.pmd.model.instructions.*;
import com.mygdx.pmd.utils.Constants;

/**
 * Created by Cameron on 1/21/2017.
 */
public class PlayerLogic extends PokemonLogic {
    private PokemonPlayer player;

    public PlayerLogic(PokemonPlayer player) {
        super(player);
        this.player = player;
    }

    @Override
    public void execute() {

        if (canAct()) {
            if(!player.children.contains(player.pc.getCurrentTile(), true)) {
                player.children.add(player.pc.getCurrentTile());
            }
            if (player.cc.getHp() <= 0) {
                player.shouldBeDestroyed = true;
            }
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

        if (mc.getNextTile().hasEntityWithComponent(MoveComponent.class)) {
            this.forceMove();
        }

        player.instructions.add(new MoveInstruction(player, mc.getNextTile()));
      //  player.instructions.add(new AnimateInstruction(player, "upattack"));

        //this is to keep movement smooth
        anc.setCurrentAnimation(dc.getDirection().toString());
        ac.setActionState(Action.MOVING);
        tc.setTurnState(Turn.COMPLETE);

        if (PMD.isKeyPressed(Key.s)) {
            mc.setSpeed(Constants.TILE_SIZE/4);
        } else mc.setSpeed(1);
    }

    private void attack(Move move) {
        player.instructions.add(new AttackInstruction(player, move));

        anc.setCurrentAnimation(dc.getDirection().toString() + "attack");
        ac.setActionState(Action.ATTACKING);
        tc.setTurnState(Turn.PENDING);
    }

    void attack() {
        attack(player.getMove());
    }

    @Override
    boolean isEnemyAdjacent() {
        return mc.getFacingTile().hasEntityWithComponent(CombatComponent.class);
    }

    @Override
    Tile findEnemyTile() {
        return null;
    }

    private void forceMove() {
        for (Entity entity : player.mc.getNextTile().getEntityList()) {
            if (entity != player && entity.hasComponent(MoveComponent.class)) {
                MoveComponent entityMC = entity.getComponent(MoveComponent.class);
                entityMC.forceMoveToTile(pc.getCurrentTile(), dc.getDirection().getOpposite());
            }
        }
    }

    @Override
    boolean canAct(){
        return tc.getTurnState() == Turn.WAITING && ac.getActionState() == Action.IDLE && player.instructions
                .isEmpty() && player.currentInstruction == Entity.NO_INSTRUCTION;
    }
}
