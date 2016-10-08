package com.mygdx.pmd.Model.Pokemon;

import com.mygdx.pmd.Controller.Controller;
import com.mygdx.pmd.Enumerations.Action;
import com.mygdx.pmd.Enumerations.Turn;
import com.mygdx.pmd.Enumerations.PokemonName;
import com.mygdx.pmd.Enumerations.State;
import com.mygdx.pmd.Model.TileType.Tile;

import java.util.ArrayList;

public class PokemonMob extends Pokemon {
    public PokemonPlayer pokemonPlayer;

    ArrayList<Tile> openNodeList;
    ArrayList<Tile> closedNodeList;

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

    public void updateLogic() {
        this.shortestPath();
        if(this.getTurnState() == Turn.WAITING) {
            if (solutionNodeList.size() > 0) {
                this.setNextTile(solutionNodeList.get(0));
                if (this.isLegalToMoveTo(this.getNextTile())) {
                    this.actionState = Action.MOVING;
                    this.setTurnState(Turn.PENDING);
                    this.setCurrentTile(this.getNextTile());
                } else {
                    solutionNodeList = new ArrayList<Tile>();
                    this.setNextTile(null);
                    return;
                }
            } else {
                this.turnToTile(this.playerTile);
                this.actionState = Action.ATTACKING;
                this.setTurnState(Turn.COMPLETE);
            }
        }

        switch(this.actionState) {
            case MOVING:
                this.updatePosition();
                break;
            case ATTACKING:
                this.attack();
                break;
        }
    }

    @Override
    public void updatePosition() {
        if (!this.equals(this.getCurrentTile()) && this.getTurnState() == Turn.PENDING)
            this.movementSpeedLogic(); //explanatory
        else if (this.equals(this.getCurrentTile())) {
            solutionNodeList.remove(this.getCurrentTile());
            this.setTurnState(Turn.COMPLETE);
            this.actionState = Action.IDLE;
            this.setNextTile(null);
            this.setFacingTile(this.direction);
        }
    }

    public void movementSpeedLogic() {
        if (controller.isSPressed()) {
            this.updateAnimation();
            this.moveFast();
        }
        else
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
        Tile backTrack = tileBoard[destination.getRow()][destination.getCol()];

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
            if (tileBoard[tile.getRow() + 1][tile.getCol()].isWalkable() && !openNodeList.contains(tileBoard[tile.getRow() + 1][tile.getCol()])) {
                tileBoard[tile.getRow() + 1][tile.getCol()].setParent(tile);
                openNodeList.add(tileBoard[tile.getRow() + 1][tile.getCol()]);
            }
        } catch (ArrayIndexOutOfBoundsException s) {
        }

        try {
            if (tileBoard[tile.getRow() - 1][tile.getCol()].isWalkable() && !openNodeList.contains(tileBoard[tile.getRow() - 1][tile.getCol()])) {
                openNodeList.add(tileBoard[tile.getRow() - 1][tile.getCol()]);
                tileBoard[tile.getRow() - 1][tile.getCol()].setParent(tile);
            }
        } catch (ArrayIndexOutOfBoundsException s) {
        }

        try {
            if (tileBoard[tile.getRow()][tile.getCol() + 1].isWalkable() && !openNodeList.contains(tileBoard[tile.getRow()][tile.getCol() + 1])) {
                openNodeList.add(tileBoard[tile.getRow()][tile.getCol() + 1]);
                tileBoard[tile.getRow()][tile.getCol() + 1].setParent(tile);
            }
        } catch (ArrayIndexOutOfBoundsException s) {
        }

        try {
            if (tileBoard[tile.getRow()][tile.getCol() - 1].isWalkable() && !openNodeList.contains(tileBoard[tile.getRow()][tile.getCol() - 1])) {
                openNodeList.add(tileBoard[tile.getRow()][tile.getCol() - 1]);
                tileBoard[tile.getRow()][tile.getCol() - 1].setParent(tile);
            }
        } catch (ArrayIndexOutOfBoundsException s) {
        }

        openNodeList.remove(tile);
        if (!closedNodeList.contains(tile))
            closedNodeList.add(tile);
    }
}
