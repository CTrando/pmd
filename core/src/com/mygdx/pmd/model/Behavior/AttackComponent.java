package com.mygdx.pmd.model.Behavior;

import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.model.Behavior.Projectile.*;
import com.mygdx.pmd.model.Entity.*;
import com.mygdx.pmd.model.Entity.Pokemon.*;
import com.mygdx.pmd.model.Entity.Projectile.*;

/**
 * Created by Cameron on 2/16/2017.
 */
public class AttackComponent extends Component{
    Pokemon pokemon;

    public AttackComponent(Entity entity){
        super(entity);
        try {
            this.pokemon = (Pokemon) entity;
        } catch(ClassCastException e) {
            System.out.println("Cannot give entity attack component");
        }
    }

    @Override
    public void update() {
        if (pokemon.getActionState() == Action.ATTACKING &&
                pokemon.children.size == 0 && pokemon.currentAnimation.isFinished()) {
            pokemon.setTurnState(Turn.COMPLETE);
            pokemon.setActionState(Action.IDLE);
            pokemon.currentAnimation.clear();
        }
    }

    public void attack() {
        pokemon.currentMove = pokemon.moves.random();
        attack(pokemon.currentMove);
    }

    public void attack(Move move) {
        pokemon.currentMove = move;
        Projectile projectile = new Projectile(pokemon);
        projectile.addComponent(ANIMATION, new ProjectileAnimationComponent(projectile));

        pokemon.children.add(projectile);
    }

    protected boolean canSeeEnemy() {
        /*if (this.aggression != Aggression.aggressive) return false;
        int rOffset = 0;
        int cOffset = 0;

        switch (this.direction) {
            case down:
                rOffset = -1;
                break;
            case up:
                rOffset = 1;
                break;
            case right:
                cOffset = 1;
                break;
            case left:
                cOffset = -1;
        }
        for (int i = 1; i < Constants.VISIBILITY_RANGE; i++) {
            //these are the rules for viewing things
            try {
                Tile tile = tileBoard[getCurrentTile().row + i * rOffset][getCurrentTile().col + i * cOffset];
                if (tile instanceof GenericTile) return false;

                if(tile == getCurrentTile()) return false;

                for(DynamicEntity entity: tile.dynamicEntities){
                    if(entity == target){
                        return true;
                    }
                }

            } catch (ArrayIndexOutOfBoundsException e) {
            }
        }*/
        return false;
    }
}
