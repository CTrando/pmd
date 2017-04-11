package com.mygdx.pmd.model.Entity.Projectile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.*;
import com.mygdx.pmd.PMD;
import com.mygdx.pmd.enumerations.Action;
import com.mygdx.pmd.enumerations.Move;
import com.mygdx.pmd.model.logic.*;
import com.mygdx.pmd.model.Entity.DynamicEntity;
import com.mygdx.pmd.model.Entity.Pokemon.Pokemon;
import com.mygdx.pmd.model.Tile.*;
import com.mygdx.pmd.model.instructions.*;
import com.mygdx.pmd.utils.*;

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
        // TODO what if facing tile is null
        super(parent.floor, parent.facingTile.x, parent.facingTile.y);
        this.animationLogic = new AnimationLogic(this);
        this.parent = parent;
        this.setDirection(parent.getDirection());

        //store currentMove data
        this.move = move;
        this.setSpeed(move.speed);

        // load all the things
        this.loadAnimations();
        this.findFutureTile();
        if (move.isRanged()) {
            this.setActionState(Action.MOVING);
            instructions.add(new MoveInstruction(this, getNextTile()));
            instructions.add(new CollideInstruction(this));
        } else {
            instructions.add(new CollideInstruction(this));
        }

        bs = new ParticleEffect();
        bs.load(Gdx.files.internal("pokemonassets/energyball"), Gdx.files.internal("pokemonassets"));
        bs.setPosition(x, y);
        bs.setDuration(10000000);

        bs.start();

        pe = new ParticleEffect();
        pe.load(Gdx.files.internal("pokemonassets/particles"), Gdx.files.internal("pokemonassets"));
        pe.setPosition(x, y);
        pe.start();
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

    private boolean isValidTarget(Tile tile) {
        if (tile == null ||
                tile instanceof GenericTile || /* must replace with damageable */
                tile.hasMovableEntity()) {
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
            bs.setPosition(x + Constants.TILE_SIZE / 2, y + Constants.TILE_SIZE / 2);
            bs.update(0.06f);
            bs.draw(batch);
        }

        if (getActionState() == Action.COLLISION) {
            pe.setPosition(x + Constants.TILE_SIZE / 2, y + Constants.TILE_SIZE / 2);
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

    public void collide() {
        this.setActionState(Action.COLLISION);

        // play sound effect
        PMD.manager.get("sfx/wallhit.wav", Sound.class).play();

        // ensure that the collision class and movement class don't run anymore
        //this.behaviors[0] = this.noBehavior;
        //this.behaviors[2] = this.noBehavior;
    }

    @Override
    public boolean isLegalToMoveTo(Tile tile) {
        return tile.isWalkable;
    }

    @Override
    public void dispose() {

    }
}

