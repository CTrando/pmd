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
    public ParticleEffect pe;

    public MoveComponent mc;
    public ActionComponent ac;
    public DirectionComponent dc;
    public PositionComponent pc;
    public RenderComponent rc;
    public AnimationComponent anc;

    Projectile(Pokemon parent, Move move) {
        super(parent.floor, parent.mc.getFacingTile().x, parent.mc.getFacingTile().y);

        this.parent = parent;
        this.move = move;
    }

    public void init(){
        this.pc = getComponent(PositionComponent.class);
        this.mc = getComponent(MoveComponent.class);
        this.ac = getComponent(ActionComponent.class);
        this.dc = getComponent(DirectionComponent.class);
        this.rc = getComponent(RenderComponent.class);
        this.anc = getComponent(AnimationComponent.class);
        anc.setCurrentAnimation(dc.getDirection().toString());


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

        //components.put(RenderComponent.class, new ProjectileRenderComponent(this));
        logic = new ProjectileLogic(this);
    }

    /**
     * Uses the rules pattern
     * See if an attack should stop at a tile or not
     * @param tile The working tile - as in working memory
     * @return if attack should stop
     */
    boolean isValidTarget(Tile tile) {
        return tile == null ||
                tile instanceof GenericTile || /* must replace with damageable */
                tile.hasEntityWithComponent(CombatComponent.class);
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

