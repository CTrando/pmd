package com.mygdx.pmd.Model.Pokemon;

import com.badlogic.gdx.utils.Array;
import com.mygdx.pmd.Controller.Controller;
import com.mygdx.pmd.Enumerations.*;
import com.mygdx.pmd.Model.Behavior.*;
import com.mygdx.pmd.Model.TileType.Tile;
import com.mygdx.pmd.utils.AI.ShortestPath;
import com.mygdx.pmd.utils.Attack;
import com.mygdx.pmd.utils.FastMotionBehavior;
import com.mygdx.pmd.utils.SlowMotionBehavior;

public class PokemonMob extends Pokemon {
    public Pokemon pokemonPlayer;

    private final int RANGE = 2;
    ShortestPath shortestPath;
    Array<Tile> solutionNodeList;
    public Tile playerTile;

    public PokemonMob(Controller controller, int x, int y, PokemonName pokemonName) {
        super(controller, x, y, pokemonName);

        this.aggression = Aggression.aggressive;

        behaviors = new Behavior[4];
        //behaviors[Behavior.INPUT_BEHAVIOR] = new InputBehavior(this);
        behaviors[Behavior.LOGIC_BEHAVIOR] = new MobLogicBehavior(this);
        behaviors[Behavior.MOVE_BEHAVIOR] = new MoveBehavior(this);
        behaviors[Behavior.ANIMATION_BEHAVIOR] = new AnimationBehavior(this);

    }

   /* @Override
    public void updateAnimation() {
        //if (this.isWithinArea(controller.loadedArea)) {
            super.updateAnimation();
      //  }

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

        if (this.turnBehavior.isTurnWaiting() && this.equals(currentTile)) {
            if (solutionNodeList.size > 0) {
                this.setNextTile(solutionNodeList.first());
                this.turnToTile(solutionNodeList.first());
                if (this.isLegalToMoveTo(this.getNextTile())) {

                    if(controller.isKeyPressed(Key.s))
                        this.motionBehavior = new FastMotionBehavior(this);
                    else
                        this.motionBehavior = (new SlowMotionBehavior(this));

                    this.actionState = Action.MOVING;
                    this.turnBehavior.setTurnState(Turn.COMPLETE);
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
        if (this.turnBehavior.isTurnWaiting()) {
            if (this.canAttack() != null && aggression == Aggression.aggressive) {
                this.turnToTile(this.canAttack());
                if(this.directAttack()) {}
                    else this.rangedAttack();

                this.actionState = Action.ATTACKING;
                this.turnBehavior.setTurnState(Turn.PENDING);
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
            if(pokemonPlayer.actionState == Action.IDLE && pokemonPlayer.turnBehavior.isTurnWaiting())
                this.actionState = Action.IDLE;
        }
    }

    public void setAggression(Aggression agression){
        this.aggression = agression;
    }*/
}
