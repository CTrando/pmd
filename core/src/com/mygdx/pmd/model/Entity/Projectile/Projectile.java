package com.mygdx.pmd.model.Entity.Projectile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.*;
import com.mygdx.pmd.PMD;
import com.mygdx.pmd.enumerations.Action;
import com.mygdx.pmd.enumerations.Move;
import com.mygdx.pmd.interfaces.Damageable;
import com.mygdx.pmd.model.Behavior.Pokemon.*;
import com.mygdx.pmd.model.Behavior.Projectile.ProjectileLogic;
import com.mygdx.pmd.model.Behavior.Projectile.ProjectileMoveInstruction;
import com.mygdx.pmd.model.Entity.DynamicEntity;
import com.mygdx.pmd.model.Entity.Pokemon.Pokemon;
import com.mygdx.pmd.model.Tile.Tile;
import com.mygdx.pmd.utils.*;

/**
 * Created by Cameron on 10/18/2016.
 */
public class Projectile extends DynamicEntity {
    private ParticleEffect bs;
    public Pokemon parent;

    //TODO fix up this class man

    public static final String MOVE_CLASSIFIER = "movement";

    //instance fields from currentMove
    public Move move;
    private boolean isRanged;
    private int damage;
    public int speed;
    private PAnimation projectileAnimation;

    private ParticleEffect pe;

    public Projectile(Pokemon parent, Move move) {
        // put down location as the parent's facing tile's location
        // set default values
        // TODO what if facing tile is null
        super(parent.floor, parent.facingTile.x, parent.facingTile.y);
        this.parent = parent;
        this.setDirection(parent.getDirection());

        //store currentMove data
        this.move = move;
        this.damage = move.damage;
        this.speed = move.speed;
        this.isRanged = move.isRanged();

        // load all the things
        this.loadAnimations();
        if (move.isRanged()) {
            this.setActionState(Action.MOVING);
            this.loadMovementLogic();
            this.loadCollisionLogic();
        } else {
            this.collide();
        }
        
        bs = new ParticleEffect();
        bs.load(Gdx.files.internal("pokemonassets/energyball"), Gdx.files.internal("pokemonassets"));
        bs.setPosition(x,y);
        bs.setDuration(10000000);
        switch(getDirection()){
            case up:
                for(ParticleEmitter particleEmitter: bs.getEmitters()){
                    particleEmitter.getAngle().setHigh(90);
                    particleEmitter.getAngle().setLow(90);
                    particleEmitter.getXOffsetValue().setLow(-5);
                }
                break;
        }

        bs.start();
        
        pe = new ParticleEffect();
        pe.load(Gdx.files.internal("pokemonassets/particles"), Gdx.files.internal("pokemonassets"));
        pe.setPosition(x, y);
        pe.start();
    }

    /**
     * set a behavior that will allow for movement
     */
    private void loadMovementLogic() {
        //behaviors[2] = new ProjectileMoveInstruction(this);
    }

    /**
     * set collision logic
     */
    private void loadCollisionLogic() {
       // behaviors[0] = new ProjectileLogic(this);
    }

    /**
     * initialize animations - include adding animation behavior
     */
    private void loadAnimations() {
        projectileAnimation = new PAnimation("attack", move.projectileMovementAnimation, null, 20, true);
        animationMap.put("movement", projectileAnimation);

        projectileAnimation = new PAnimation("death", move.projectileCollisionAnimation, null, move.animationLength, false);
        animationMap.put("death", projectileAnimation);

       // behaviors[1] = new AnimationBehavior(this);
    }

    @Override
    public void render(SpriteBatch batch){
        super.render(batch);
        if(getActionState() == Action.MOVING && parent.currentAnimation.isFinished()) {
            bs.setPosition(x + Constants.TILE_SIZE/2, y + Constants.TILE_SIZE/2);
            bs.update(0.06f);
            bs.draw(batch);
        }

        if(getActionState() == Action.COLLISION) {
            pe.setPosition(x + Constants.TILE_SIZE/2, y + Constants.TILE_SIZE/2);
            pe.update(0.06f);
            pe.draw(batch);
        }
    }

    @Override
    public void update() {
        // only update the projectile when the parent's attack animation has finished
        if (parent.currentAnimation.isFinished()) {
            super.update();
        }

        if (projectileAnimation.isFinished() && this.getActionState() == Action.COLLISION) {
            for (Damageable damageable : PUtils.getObjectsOfType(Damageable.class, getCurrentTile().getEntityList())) {
                damageable.takeDamage(parent, move.damage);
            }

            if(move.equals(Move.INSTANT_KILLER)){
                System.out.println("RKO OUT OF NOWHERE");
            }

            //setting this to null so parent will know that the attack has finished
            this.shouldBeDestroyed = true;
        }
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

