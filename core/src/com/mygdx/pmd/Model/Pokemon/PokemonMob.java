package com.mygdx.pmd.Model.Pokemon;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Array;
import com.mygdx.pmd.Controller.Controller;
import com.mygdx.pmd.Enumerations.*;
import com.mygdx.pmd.Model.TileType.Tile;
import com.mygdx.pmd.utils.Attack;
import com.mygdx.pmd.utils.Projectile;

import java.util.ArrayList;

public class PokemonMob extends Pokemon {
    public PokemonPlayer pokemonPlayer;

    ArrayList<Tile> openNodeList;
    ArrayList<Tile> closedNodeList;

    private final int RANGE = 5;

    private boolean hasReached = false;

    ArrayList<Tile> solutionNodeList;

    public Tile playerTile;

    public PokemonMob(int x, int y, Controller controller, boolean move, PokemonName pokemonName, State state) {
        super(controller, x, y, move, pokemonName);
        super.state = state;

        this.pokemonPlayer = controller.getPokemonPlayer();
        playerTile = pokemonPlayer.getCurrentTile();

        solutionNodeList = new ArrayList<Tile>();
    }

    @Override
    public void updateAnimation() {
        if (this.isWithinArea(controller.loadedArea)) {
            super.updateAnimation();
        }

        if (this.getNextTile() == null)
            return;

        if (this.isToLeft(this.getNextTile()))
            this.right();
        else if (this.isToRight(this.getNextTile()))
            this.left();
        else if (this.isAbove(this.getNextTile()))
            this.down();
        else if (this.isBelow(this.getNextTile()))
            this.up();
    }

    public boolean pathFind() {
        this.shortestPath();
        if (this.getTurnState() == Turn.WAITING) {
            if (solutionNodeList.size() > 0) {
                this.setNextTile(solutionNodeList.get(0));
                if (this.isLegalToMoveTo(this.getNextTile())) {
                    this.actionState = Action.MOVING;
                    this.setTurnState(Turn.COMPLETE);
                    this.setFacingTile(this.direction);
                    this.setCurrentTile(this.getNextTile());
                } else {
                    solutionNodeList = new ArrayList<Tile>();
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
        if (this.getTurnState() == Turn.WAITING) {
            if (this.canAttack()) {
                if(this.directAttack()) {}
                    else this.rangedAttack();

                this.actionState = Action.ATTACKING;
                this.setTurnState(Turn.PENDING);
            }else {
                boolean canPathFind = this.pathFind();

                if (!canPathFind) {
                    this.turnToTile(this.playerTile);
                    this.actionState = Action.ATTACKING;
                    this.setTurnState(Turn.PENDING);
                    currentAttack = new Attack(this, AttackType.DIRECT);

                    this.setCurrentAnimation(currentAttack.animation);
                }
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

    public boolean canAttack() {
        for(int i = -1*RANGE; i< RANGE; i++) {
            try {
                Tile tile = tileBoard[getCurrentTile().row+i][getCurrentTile().col];
                if(tile != this.getCurrentTile())
                    if(tile.hasAPokemon()) {
                        return true;
                    }
            } catch (ArrayIndexOutOfBoundsException e){
                e.printStackTrace();
            }
        }

        for(int j = -1*RANGE; j< RANGE; j++) {
            try {
                Tile tile = tileBoard[getCurrentTile().row][getCurrentTile().col+j];
                if(tile != this.getCurrentTile())
                    if(tile.hasAPokemon()) {
                        return true;
                    }
            } catch (ArrayIndexOutOfBoundsException e){
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public void updatePosition() {
        if (this.equals(this.getCurrentTile())) {
            solutionNodeList.remove(this.getCurrentTile());
            return;
        } else
            this.movementSpeedLogic(); //explanatory
    }


    public void movementSpeedLogic() {
        if (controller.isSPressed()) {
            this.updateAnimation();
            this.moveFast();
        } else
            this.moveSlow();
    }

    public void shortestPath() {
        if (playerTile == null)
            return;

        if (playerTile != pokemonPlayer.getCurrentTile()) {
            playerTile = pokemonPlayer.getCurrentTile();
            this.createPathTo(playerTile);
        }
    }

    public ArrayList<Tile> createPathTo(Tile dest) {
        hasReached = false;

        Tile.resetTileArrayParents(tileBoard);

        Tile destination = dest;
        Tile currentTile = this.getCurrentTile();

        openNodeList = new ArrayList<Tile>();
        closedNodeList = new ArrayList<Tile>();
        solutionNodeList = new ArrayList<Tile>();

        openNodeList.add(currentTile);

        while (!hasReached) {
            Tile tile = openNodeList.get(0);
            this.evaluateTile(tile, destination);
        }

        currentTile.setParent(null);
        Tile backTrack = tileBoard[destination.row][destination.col];

        while (backTrack.getParent() != null) {
            backTrack = backTrack.getParent();
            solutionNodeList.add(0, backTrack);
        }
        solutionNodeList.remove(this.getCurrentTile());
        return solutionNodeList;
    }

    public void evaluateTile(Tile tile, Tile dest) {
        if (tile == dest) {
            openNodeList = new ArrayList<Tile>();
            closedNodeList.add(tile);
            hasReached = true;
            return;
        }

        try {
            if (tileBoard[tile.row + 1][tile.col].isWalkable() && !openNodeList.contains(tileBoard[tile.row + 1][tile.col])) {
                tileBoard[tile.row + 1][tile.col].setParent(tile);
                openNodeList.add(tileBoard[tile.row + 1][tile.col]);
            }
        } catch (ArrayIndexOutOfBoundsException s) {
        }

        try {
            if (tileBoard[tile.row - 1][tile.col].isWalkable() && !openNodeList.contains(tileBoard[tile.row - 1][tile.col])) {
                openNodeList.add(tileBoard[tile.row - 1][tile.col]);
                tileBoard[tile.row - 1][tile.col].setParent(tile);
            }
        } catch (ArrayIndexOutOfBoundsException s) {
        }

        try {
            if (tileBoard[tile.row][tile.col + 1].isWalkable() && !openNodeList.contains(tileBoard[tile.row][tile.col + 1])) {
                openNodeList.add(tileBoard[tile.row][tile.col + 1]);
                tileBoard[tile.row][tile.col + 1].setParent(tile);
            }
        } catch (ArrayIndexOutOfBoundsException s) {
        }

        try {
            if (tileBoard[tile.row][tile.col - 1].isWalkable() && !openNodeList.contains(tileBoard[tile.row][tile.col - 1])) {
                openNodeList.add(tileBoard[tile.row][tile.col - 1]);
                tileBoard[tile.row][tile.col - 1].setParent(tile);
            }
        } catch (ArrayIndexOutOfBoundsException s) {
        }

        openNodeList.remove(tile);
        if (!closedNodeList.contains(tile))
            closedNodeList.add(tile);
    }
}
