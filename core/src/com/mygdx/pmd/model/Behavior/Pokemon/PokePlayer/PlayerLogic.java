package com.mygdx.pmd.model.Behavior.Pokemon.PokePlayer;

import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.model.Behavior.Entity.MoveFastBehavior;
import com.mygdx.pmd.model.Behavior.Pokemon.PokemonBehavior;
import com.mygdx.pmd.model.Entity.*;
import com.mygdx.pmd.model.Entity.Pokemon.PokemonPlayer;

/**
 * Created by Cameron on 1/21/2017.
 */
public class PlayerLogic extends PokemonBehavior {
    private PokemonPlayer player;

    public PlayerLogic(PokemonPlayer player) {
        super(player);
        this.player = player;
    }

    @Override
    public void execute() {
        if (player.isTurnWaiting() && player.equals(player.getCurrentTile())) {
            player.handleInput();

            if (player.canAttack()) {
                player.attack(player.move);
                player.move = null;

                player.setActionState(Action.ATTACKING);
                player.setTurnState(Turn.PENDING);

                player.behaviors[2] = player.attackBehavior;
            } else if (player.canMove()) {
                player.setNextTile(player.possibleNextTile);
                player.possibleNextTile = null;

                if (player.getNextTile().hasDynamicEntity()) {
                    for (DynamicEntity dEntity : player.getNextTile().dynamicEntities) {
                        if (dEntity != player) {
                            dEntity.forceMoveToTile(player.getCurrentTile());
                        }
                    }
                }

                player.setTurnState(Turn.COMPLETE);
                player.setActionState(Action.MOVING);

                player.behaviors[2] = player.moveBehavior;

                if (controller.isKeyPressed(Key.s)) {
                    player.setSpeed(5);
                } else player.setSpeed(1);
            }
        }
    }
}
