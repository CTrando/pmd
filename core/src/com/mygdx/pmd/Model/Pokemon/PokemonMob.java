package com.mygdx.pmd.Model.Pokemon;

import com.badlogic.gdx.utils.Array;
import com.mygdx.pmd.Controller.Controller;
import com.mygdx.pmd.Enumerations.*;
import com.mygdx.pmd.Model.TileType.Tile;
import com.mygdx.pmd.utils.AI.ShortestPath;
import com.mygdx.pmd.utils.Attack;

import java.util.ArrayList;

public class PokemonMob extends Pokemon {
    public PokemonPlayer pokemonPlayer;

    private final int RANGE = 5;

    ShortestPath shortestPath;

    Array<Tile> solutionNodeList;

    public Tile playerTile;

    public PokemonMob(int x, int y, Controller controller, boolean move, PokemonName pokemonName, AggressionState agressionState) {
        super(controller, x, y, move, pokemonName);
        super.aggressionState = agressionState;

        this.pokemonPlayer = controller.getPokemonPlayer();
        playerTile = pokemonPlayer.getCurrentTile();

        solutionNodeList = new Array<Tile>();
        shortestPath = new ShortestPath(this, tileBoard);
    }

    @Override
    public void updateAnimation() {
        if (this.isWithinArea(controller.loadedArea)) {
            super.updateAnimation();
        }

        if (this.getNextTile() == null)
            return;

        if (this.isToLeft(this.getNextTile()))
            this.direction = Direction.right;
        else if (this.isToRight(this.getNextTile()))
            this.direction = Direction.left;
        else if (this.isAbove(this.getNextTile()))
            this.direction = Direction.down;
        else if (this.isBelow(this.getNextTile()))
            this.direction = Direction.up;
    }

    public boolean pathFind() {
        solutionNodeList = shortestPath.findShortestPath(pokemonPlayer.currentTile);

        if (this.turnState == Turn.WAITING) {
            if (solutionNodeList.size > 0) {
                this.setNextTile(solutionNodeList.first());
                this.turnToTile(solutionNodeList.first());
                if (this.isLegalToMoveTo(this.getNextTile())) {
                    this.actionState = Action.MOVING;
                    this.turnState =Turn.COMPLETE;
                    this.setCurrentTile(this.getNextTile());
                } else {
                    solutionNodeList = new Array<Tile>();
                    this.setNextTile(null);
                    return false;
                }
            } else {
                return false;
            }
        } else return false;
        return true;
    }

    public void updateLogic() {
        if (this.turnState == Turn.WAITING) {
            if (this.canAttack() != null) {
                this.turnToTile(this.canAttack());
                if(this.directAttack()) {}
                    else this.rangedAttack();

                this.actionState = Action.ATTACKING;
                this.turnState =Turn.PENDING;
            }else {
                this.pathFind();
            }
        }
    }

    public boolean rangedAttack() {
        currentAttack = new Attack(this, AttackType.RANGED);
        return true;
    }

    public boolean directAttack() { //TODO Fix the facingtile logic
        if(this.facingTile != null && this.facingTile.hasAPokemon() && this.facingTile.getCurrentPokemon() != this) {
            currentAttack = new Attack(this, AttackType.DIRECT);
            return true;
        }
        return false;
    }

    public Tile canAttack() {
        for(int i = -1*RANGE; i< RANGE; i++) {
            try {
                Tile tile = tileBoard[getCurrentTile().row+i][getCurrentTile().col];
                if(tile != this.getCurrentTile())
                    if(tile.hasAPokemon()) {
                        return tile;
                    }
            } catch (ArrayIndexOutOfBoundsException e){
            }
        }

        for(int j = -1*RANGE; j< RANGE; j++) {
            try {
                Tile tile = tileBoard[getCurrentTile().row][getCurrentTile().col+j];
                if(tile != this.getCurrentTile())
                    if(tile.hasAPokemon()) {
                        return tile;
                    }
            } catch (ArrayIndexOutOfBoundsException e){
            }
        }
        return null;
    }

    @Override
    public void updatePosition() {
        super.updatePosition();

        if (this.equals(this.getCurrentTile()) && actionState == Action.MOVING) {
            solutionNodeList.removeValue(this.getCurrentTile(), true);
            if(pokemonPlayer.actionState == Action.IDLE)
                this.actionState = Action.IDLE;
        }
    }
}
