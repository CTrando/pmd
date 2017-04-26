package com.mygdx.pmd.model.logic;

import com.mygdx.pmd.model.Entity.Pokemon.*;
import com.mygdx.pmd.model.Tile.*;
import com.mygdx.pmd.model.components.*;
import com.mygdx.pmd.utils.Constants;

/**
 * Created by Cameron on 4/15/2017.
 */
public abstract class PokemonLogic implements Logic {
    Pokemon pokemon;
    DirectionComponent dc;
    MoveComponent mc;
    ActionComponent ac;
    TurnComponent tc;
    PositionComponent pc;
    AnimationComponent anc;

    protected PokemonLogic(Pokemon pokemon) {
        this.pokemon = pokemon;

        this.dc = pokemon.dc;
        this.mc = pokemon.mc;
        this.ac = pokemon.ac;
        this.tc = pokemon.tc;
        this.pc = pokemon.pc;
        this.anc = pokemon.anc;
    }

    abstract boolean canAct();
    abstract boolean canMove();
    abstract boolean canAttack();

    abstract void move();
    abstract void attack();

    boolean isEnemyAdjacent() {
        return mc.getFacingTile().hasEntityWithComponent(CombatComponent.class);
    }

    Tile findEnemyTile() {
        Tile[][] tileBoard = pokemon.tileBoard;

        int row = pc.getCurrentTile().row;
        int col = pc.getCurrentTile().col;

        switch (dc.getDirection()) {
            case up:
                for (int i = 1; i < Constants.VISIBILITY_RANGE; i++) {
                    Tile tile = tileBoard[row + i][col];
                    if (isValidTarget(tile) || i == Constants.VISIBILITY_RANGE - 1) {
                        return tile;
                    }
                }
                break;
            case down:
                for (int i = 1; i < Constants.VISIBILITY_RANGE; i++) {
                    Tile tile = tileBoard[row - i][col];
                    if (isValidTarget(tile) || i == Constants.VISIBILITY_RANGE - 1) {
                        return tile;
                    }
                }
                break;
            case left:
                for (int j = 1; j < Constants.VISIBILITY_RANGE; j++) {
                    Tile tile = tileBoard[row][col - j];
                    if (isValidTarget(tile) || j == Constants.VISIBILITY_RANGE - 1) {
                        return tile;
                    }
                }
                break;
            case right:
                for (int j = 1; j < Constants.VISIBILITY_RANGE; j++) {
                    Tile tile = tileBoard[row][col + j];
                    if (isValidTarget(tile) || j == Constants.VISIBILITY_RANGE - 1) {
                        return tile;
                    }
                }
                break;
        }
        return null;
    }

    /**
     * Uses the rules pattern
     * See if an attack should stop at a tile or not
     * @param tile The working tile - as in working memory
     * @return if attack should stop
     */
    private boolean isValidTarget(Tile tile) {
        return tile == null ||
                tile instanceof GenericTile || /* must replace with damageable */
                tile.hasEntityWithComponent(CombatComponent.class);
    }
}
