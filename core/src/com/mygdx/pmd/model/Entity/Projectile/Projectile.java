package com.mygdx.pmd.model.Entity.Projectile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.*;
import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.interfaces.*;
import com.mygdx.pmd.model.Entity.*;
import com.mygdx.pmd.model.components.*;
import com.mygdx.pmd.model.logic.*;
import com.mygdx.pmd.model.Entity.Pokemon.Pokemon;
import com.mygdx.pmd.model.Tile.*;
import com.mygdx.pmd.model.instructions.*;
import com.mygdx.pmd.utils.*;
import javafx.geometry.Pos;

import static com.mygdx.pmd.screens.DungeonScreen.PPM;

/**
 * Created by Cameron on 10/18/2016.
 */
public class Projectile extends Entity {
    public ParticleEffect bs;
    public Pokemon parent;
    private Logic logic;
    
    //instance fields from currentMove
    public Move move;
    public PAnimation animation;

    public ParticleEffect pe;

    public MoveComponent mc;
    public ActionComponent ac;
    public DirectionComponent dc;
    public PositionComponent pc;
    public RenderComponent rc;
    public AnimationComponent anc;
    //TODO make a projectile factory class and fix it up

    public Projectile(Pokemon parent, Move move) {
        // put down location as the parent's facing tile's location
        // set default values
        super(parent.floor, parent.mc.getFacingTile().x, parent.mc.getFacingTile().y);


        this.parent = parent;
        this.move = move;
        // load all the things
        this.loadAnimations();

        components.put(MoveComponent.class, new MoveComponent(this));
        components.put(ActionComponent.class, new ActionComponent(this));
        components.put(DirectionComponent.class, new DirectionComponent(this));
        components.put(RenderComponent.class, new RenderComponent(this));

        this.pc = getComponent(PositionComponent.class);
        this.mc = getComponent(MoveComponent.class);
        this.ac = getComponent(ActionComponent.class);
        this.dc = getComponent(DirectionComponent.class);
        this.rc = getComponent(RenderComponent.class);
        this.anc = getComponent(AnimationComponent.class);

        pc.setCurrentTile(parent.mc.getFacingTile());
        dc.setDirection(parent.dc.getDirection());
        mc.setSpeed(move.speed);
        this.findFutureTile();
        anc.setCurrentAnimation(dc.getDirection().toString());
        if (move.isRanged()) {
            ac.setActionState(Action.MOVING);
            instructions.add(new MoveInstruction(this, mc.getNextTile()));
            instructions.add(new CollideInstruction(this));
        } else {
            instructions.add(new CollideInstruction(this));
        }

        //load particle effects
        bs = new ParticleEffect();
        bs.load(Gdx.files.internal("pokemonassets/energyball"), Gdx.files.internal("pokemonassets"));
        bs.setPosition(pc.x/PPM, pc.y/PPM);
        bs.setDuration(10000000);
        bs.scaleEffect(1/PPM);
        bs.start();

        pe = new ParticleEffect();
        pe.load(Gdx.files.internal("pokemonassets/particles"), Gdx.files.internal("pokemonassets"));
        pe.setPosition(pc.x/PPM, pc.y/PPM);
        pe.scaleEffect(1/PPM);
        pe.start();

        // must be last so has all the other data

        components.put(RenderComponent.class, new ProjectileRenderComponent(this));
        logic = new ProjectileLogic(this);
    }

    private void findFutureTile() {
        int row = pc.getCurrentTile().row;
        int col = pc.getCurrentTile().col;

        switch (dc.getDirection()) {
            case up:
                for (int i = 0; i < move.range; i++) {
                    Tile tile = tileBoard[row + i][col];
                    if (isValidTarget(tile) || i == move.range - 1) {
                        mc.setNextTile(tile);
                        break;
                    }
                }
                break;
            case down:
                for (int i = 0; i < move.range; i++) {
                    Tile tile = tileBoard[row - i][col];
                    if (isValidTarget(tile) || i == move.range - 1) {
                        mc.setNextTile(tile);
                        break;
                    }
                }
                break;
            case left:
                for (int j = 0; j < move.range; j++) {
                    Tile tile = tileBoard[row][col - j];
                    if (isValidTarget(tile) || j == move.range - 1) {
                        mc.setNextTile(tile);
                        break;
                    }
                }
                break;
            case right:
                for (int j = 0; j < move.range; j++) {
                    Tile tile = tileBoard[row][col + j];
                    if (isValidTarget(tile) || j == move.range - 1) {
                        mc.setNextTile(tile);
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
        return tile == null ||
                tile instanceof GenericTile || /* must replace with damageable */
                tile.hasEntityWithComponent(CombatComponent.class);
    }

    /**
     * initialize animations - include adding animationLogic behavior
     */
    private void loadAnimations() {
        AnimationComponent anc = new AnimationComponent(this);
        animation = new PAnimation("attack", move.projectileMovementAnimation, null, 20, true);
        anc.putAnimation("up", animation);
        anc.putAnimation("down", animation);
        anc.putAnimation("right", animation);
        anc.putAnimation("left", animation);

        anc.putAnimation("upidle", animation);
        anc.putAnimation("downidle", animation);
        anc.putAnimation("leftidle", animation);
        anc.putAnimation("rightidle", animation);

        animation = new PAnimation("death", move.projectileCollisionAnimation, null, move.animationLength,
                                   false);
        anc.putAnimation("death", animation);
        components.put(AnimationComponent.class, anc);
    }

    @Override
    public void update() {
        if (parent.anc.isAnimationFinished()) {
            super.update();
            runLogic();
            rc.setSprite(anc.getCurrentSprite());
        }
    }

    @Override
    public void runLogic() {
        logic.execute();
    }

    @Override
    public void dispose() {

    }

    @Override
    public String toString() {
        return "Projectile at " + pc.getCurrentTile().toString();
    }
}

