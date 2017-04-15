package com.mygdx.pmd.model.Entity.Projectile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.*;
import com.mygdx.pmd.enumerations.Action;
import com.mygdx.pmd.enumerations.Move;
import com.mygdx.pmd.interfaces.Movable;
import com.mygdx.pmd.model.logic.*;
import com.mygdx.pmd.model.Entity.DynamicEntity;
import com.mygdx.pmd.model.Entity.Pokemon.Pokemon;
import com.mygdx.pmd.model.Tile.*;
import com.mygdx.pmd.model.instructions.*;
import com.mygdx.pmd.utils.*;

import static com.mygdx.pmd.screens.DungeonScreen.PPM;

/**
 * Created by Cameron on 10/18/2016.
 */
public class Projectile extends DynamicEntity {
    private ParticleEffect bs;
    public Pokemon parent;
    private Logic logic;

    //TODO fix up this class man

    public static final String MOVE_CLASSIFIER = "movement";

    //instance fields from currentMove
    public Move move;
    public PAnimation animation;

    private ParticleEffect pe;

    public Projectile(Pokemon parent, Move move) {
        // put down location as the parent's facing tile's location
        // set default values
        super(parent.floor, parent.facingTile.x, parent.facingTile.y);
        this.animationLogic = new AnimationLogic(this);
        this.parent = parent;
        this.move = move;

        this.setDirection(parent.getDirection());
        this.setSpeed(move.speed);
        this.findFutureTile();

        // load all the things
        this.loadAnimations();
        if (move.isRanged()) {
            this.setActionState(Action.MOVING);
            instructions.add(new MoveInstruction(this, getNextTile()));
            instructions.add(new CollideInstruction(this));
        } else {
            instructions.add(new CollideInstruction(this));
        }

        //load particle effects
        bs = new ParticleEffect();
        bs.load(Gdx.files.internal("pokemonassets/energyball"), Gdx.files.internal("pokemonassets"));
        bs.setPosition(x/PPM, y/PPM);
        bs.setDuration(10000000);
        bs.scaleEffect(1/PPM);
        bs.start();

        pe = new ParticleEffect();
        pe.load(Gdx.files.internal("pokemonassets/particles"), Gdx.files.internal("pokemonassets"));
        pe.setPosition(x/PPM, y/PPM);
        pe.scaleEffect(1/PPM);
        pe.start();

        // must be last so has all the other data
        logic = new ProjectileLogic(this);
    }

    private void findFutureTile() {
        int row = getCurrentTile().row;
        int col = getCurrentTile().col;

        switch (getDirection()) {
            case up:
                for (int i = 0; i < move.range; i++) {
                    Tile tile = tileBoard[row + i][col];
                    if (isValidTarget(tile) || i == move.range - 1) {
                        setNextTile(tile);
                        break;
                    }
                }
                break;
            case down:
                for (int i = 0; i < move.range; i++) {
                    Tile tile = tileBoard[row - i][col];
                    if (isValidTarget(tile) || i == move.range - 1) {
                        setNextTile(tile);
                        break;
                    }
                }
                break;
            case left:
                for (int j = 0; j < move.range; j++) {
                    Tile tile = tileBoard[row][col - j];
                    if (isValidTarget(tile) || j == move.range - 1) {
                        setNextTile(tile);
                        break;
                    }
                }
                break;
            case right:
                for (int j = 0; j < move.range; j++) {
                    Tile tile = tileBoard[row][col + j];
                    if (isValidTarget(tile) || j == move.range - 1) {
                        setNextTile(tile);
                        break;
                    }
                }
                break;
        }
    }

    /**
     * Uses the rules pattern
     * See if an attack should stop at a tile or not
     * @param tile The working tile - as in working memory
     * @return if attack should stop
     */
    private boolean isValidTarget(Tile tile) {
        if (tile == null ||
                tile instanceof GenericTile || /* must replace with damageable */
                tile.hasEntityOfType(Movable.class)) {
            return true;
        }
        return false;
    }

    /**
     * initialize animations - include adding animationLogic behavior
     */
    private void loadAnimations() {
        animation = new PAnimation("attack", move.projectileMovementAnimation, null, 20, true);
        animationMap.put("up", animation);
        animationMap.put("down", animation);
        animationMap.put("right", animation);
        animationMap.put("left", animation);

        animationMap.put("upidle", animation);
        animationMap.put("downidle", animation);
        animationMap.put("leftidle", animation);
        animationMap.put("rightidle", animation);

        animation = new PAnimation("death", move.projectileCollisionAnimation, null, move.animationLength,
                                   false);
        animationMap.put("death", animation);
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
        if (getActionState() == Action.MOVING && parent.currentAnimation.isFinished()) {
            bs.setPosition((x + Constants.TILE_SIZE / 2)/PPM, (y + Constants.TILE_SIZE / 2)/PPM);
            bs.update(0.06f);
            bs.draw(batch);
        }

        if (getActionState() == Action.COLLISION) {
            pe.setPosition((x + Constants.TILE_SIZE / 2)/PPM, (y + Constants.TILE_SIZE / 2)/PPM);
            pe.update(0.06f);
            pe.draw(batch);
        }
    }

    @Override
    public void update() {
        if (parent.currentAnimation.isFinished()) {
            super.update();
            runLogic();
        }
    }

    @Override
    public void runLogic() {
        logic.execute();
    }

    @Override
    public boolean isLegalToMoveTo(Tile tile) {
        return tile.isWalkable;
    }

    @Override
    public void dispose() {

    }

    @Override
    public String toString() {
        return "Projectile at " + getCurrentTile().toString();
    }
}

