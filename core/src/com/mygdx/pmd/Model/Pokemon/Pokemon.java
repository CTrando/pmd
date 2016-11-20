package com.mygdx.pmd.Model.Pokemon;


import com.mygdx.pmd.Controller.Controller;
import com.mygdx.pmd.Enumerations.*;
import com.mygdx.pmd.Model.Behavior.Behavior;
import com.mygdx.pmd.Model.TileType.Tile;
import com.mygdx.pmd.utils.*;


public abstract class Pokemon extends Entity {
    public Tile facingTile;

    public Turn turnState = Turn.COMPLETE;

    public PAnimation currentAnimation;

    public Tile[][] tileBoard;

    public TurnBehavior turnBehavior;
    public Action actionState = Action.IDLE;

    public PokemonName pokemonName;

    public Projectile projectile;

    protected Pokemon(Controller controller, int x, int y, PokemonName pokemonName) {
        super(controller, x, y);
        direction = Direction.down;
        turnBehavior = new TurnBehavior(this);
        this.pokemonName = pokemonName;

        if (controller.dungeonMode) {
            tileBoard = controller.getCurrentFloor().getTileBoard();
            this.setCurrentTile(tileBoard[this.y / Constants.TILE_SIZE][this.x / Constants.TILE_SIZE]);
        }
    }

    @Override
    public void update() {
        for (int i = 0; i < behaviors.length; i++) {
            behaviors[i].execute();
        }
    }

    public void setBehavior(Behavior behavior, int index) {
        behaviors[index] = behavior;
    }


    public void setFacingTileBasedOnDirection(Direction direction) {
        try {
            switch (direction) {
                case up:
                    facingTile = tileBoard[currentTile.row + 1][currentTile.col];
                    break;
                case down:
                    facingTile = tileBoard[currentTile.row - 1][currentTile.col];
                    break;
                case right:
                    facingTile = tileBoard[currentTile.row][currentTile.col + 1];
                    break;
                case left:
                    facingTile = tileBoard[currentTile.row][currentTile.col - 1];
                    break;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        }
    }

    public void turnToTile(Tile tile) {
        if (tile.isAbove(currentTile))
            this.direction = Direction.up;
        if (tile.isBelows(currentTile))
            this.direction = Direction.down;
        if (tile.isToLeft(currentTile))
            this.direction = Direction.left;
        if (tile.isToRight(currentTile))
            this.direction = Direction.right;
    }

    @Override
    public boolean isLegalToMoveTo(Tile tile) {
        if (tile == null) return false;

        if(tile.hasEntity()) {
            for(Entity entity: tile.getEntityList()) {
                if (entity.aggression == Aggression.aggressive)
                    return false;
            }
        }

        if (!tile.isWalkable)
            return false;
        return true;
    }

    @Override
    public void randomizeLocation() {
        super.randomizeLocation();
    }
}
