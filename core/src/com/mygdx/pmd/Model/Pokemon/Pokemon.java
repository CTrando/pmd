package com.mygdx.pmd.Model.Pokemon;


import com.mygdx.pmd.Controller.Controller;
import com.mygdx.pmd.Enumerations.*;
import com.mygdx.pmd.Model.TileType.Tile;
import com.mygdx.pmd.utils.*;


public abstract class Pokemon extends Entity {

    public Direction direction = Direction.down;
    public Tile facingTile;
    public Attack currentAttack;

    public PAnimation currentAnimation;

    public Tile[][] tileBoard;

    public TurnBehavior turnBehavior;
    public Action actionState = Action.IDLE;
    public Aggression aggression;

    public PokemonName pokemonName;

    protected Pokemon(Controller controller, int x, int y, PokemonName pokemonName) {
        super(controller, x, y);
        turnBehavior = new TurnBehavior(this);
        this.pokemonName = pokemonName;

        if (controller.dungeonMode) {
            tileBoard = controller.getCurrentFloor().getTileBoard();
            this.setCurrentTile(tileBoard[this.y/Constants.TILE_SIZE][this.x/Constants.TILE_SIZE]);
        }
    }

    @Override
    public void update() {
        this.updateAnimation();
        this.updateLogic();
        this.updatePosition();
        this.updateFacingTile();
        this.updateAttack();
    }

    @Override
    public void updateAnimation() {
        switch(actionState) {
            case MOVING:
                currentAnimation = animationMap.get(direction.toString());
                currentSprite = animationMap.get(direction.toString()).getCurrentSprite();
                break;
            case ATTACKING:
                currentAnimation = animationMap.get(direction.toString() + "attack");
                currentSprite = animationMap.get(direction.toString() + "attack").getCurrentSprite();
                break;
            case IDLE:
                currentAnimation = animationMap.get("noanimation");
                currentSprite = animationMap.get(direction.toString()).finalSprite;
                animationMap.get(direction.toString()).clear();
        }
    }

    @Override
    public void updatePosition(){
        if (!this.equals(this.getCurrentTile()))
            motionBehavior.move(); //explanatory
    }

    public void updateFacingTile() {
        try {
            switch (this.direction) {
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

    public void updateAttack() {
        if (currentAttack == null) return;
        currentAttack.update();
        if(currentAttack.isFinished())
            this.actionState = Action.IDLE;

        if (currentAttack.isFinished() && controller.projectiles.size == 0) {
            this.actionState = Action.IDLE;
            this.turnBehavior.setTurnState(Turn.COMPLETE);
            this.currentAttack = null;
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
        if (tile.getCurrentPokemon() != null && tile.getCurrentPokemon() != this)
            return false;
        if (!tile.isWalkable())
            return false;
        return true;
    }

    @Override
    public void randomizeLocation() {
        super.randomizeLocation();
        currentTile.addEntity(this);
    }
}
