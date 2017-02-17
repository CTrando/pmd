package com.mygdx.pmd.model.Behavior.Pokemon.PokePlayer;

import com.mygdx.pmd.PMD;
import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.model.Behavior.*;
import com.mygdx.pmd.model.Behavior.Entity.*;
import com.mygdx.pmd.model.Entity.*;
import com.mygdx.pmd.model.Entity.Pokemon.PokemonPlayer;

/**
 * Created by Cameron on 1/21/2017.
 */
public class PlayerLogicComponent extends Component {
    private PokemonPlayer player;

    public PlayerLogicComponent(PokemonPlayer player) {
        super(player);
        this.player = player;
    }

    @Override
    public void update() {
        if (player.hp <= 0) {
            player.shouldBeDestroyed = true;
        }

        if (player.isTurnWaiting() && player.equals(player.getCurrentTile())) {
            player.handleInput();

            if (player.canAttack()) {
                player.attack(player.currentMove);
                player.currentMove = null;

                player.setActionState(Action.ATTACKING);
                player.setTurnState(Turn.PENDING);

                //player.behaviors[2] = player.attackBehavior;
            } else if (player.canMove()) {
                player.setNextTile(player.possibleNextTile);
                player.possibleNextTile = null;

                if(player.componentExists(Component.DIRECTION)){
                    DirectionComponent directionComponent = (DirectionComponent) player.getComponent(Component.DIRECTION);
                    directionComponent.setFacingTile(player.getNextTile());
                }

                if (player.getNextTile().hasEntity()) {
                    for (Entity entity : player.getNextTile().entities) {
                        if (entity != player) {
                            if(entity.componentExists(Component.MOVE)){
                                MoveComponent moveComponent = (MoveComponent) entity.getComponent(Component.MOVE);
                                moveComponent.forceMoveToTile(player.getCurrentTile());
                            }
                            entity.direction = player.direction.getOppositeDirection();
                        }
                    }
                }

                player.setTurnState(Turn.COMPLETE);
                player.setActionState(Action.MOVING);

                //player.behaviors[2] = player.moveBehavior;

                if (PMD.isKeyPressed(Key.s)) {
                    player.setSpeed(5);
                } else player.setSpeed(1);
            }

        } else if (player.getActionState() == Action.IDLE) {
            System.out.println(player.getTurnState());
            System.out.println(player.getActionState());
        }
    }
}
