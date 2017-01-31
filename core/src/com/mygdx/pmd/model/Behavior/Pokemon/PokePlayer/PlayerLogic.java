package com.mygdx.pmd.model.Behavior.Pokemon.PokePlayer;

import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.model.Behavior.Entity.MoveFastBehavior;
import com.mygdx.pmd.model.Behavior.Pokemon.PokemonBehavior;
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
        if (player.isTurnWaiting() && player.equals(player.currentTile)) {
            player.handleInput();

            if (player.canAttack()) {
                if (controller.isKeyPressed(Key.b) && controller.isKeyPressed(Key.t)) {
                    player.attack(Move.SWIPERNOSWIPING);
                } else if(controller.isKeyPressed(Key.IK)){
                    player.attack(Move.INSTANT_KILLER);
                }

                player.setActionState(Action.ATTACKING);
                player.setTurnState(Turn.PENDING);

                player.behaviors[2] = player.attackBehavior;
            } else if (player.canMove()) {
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
