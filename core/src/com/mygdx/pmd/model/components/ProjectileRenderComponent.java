package com.mygdx.pmd.model.components;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g3d.particles.ParticleChannels;
import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.model.Entity.*;
import com.mygdx.pmd.model.Entity.Projectile.*;
import com.mygdx.pmd.utils.Constants;


/**
 * Created by Cameron on 4/23/2017.
 */
/*
public class ProjectileRenderComponent extends RenderComponent {

    private Projectile projectile;
    private ActionComponent ac;

    public ProjectileRenderComponent(Projectile projectile) {
        super(projectile);
        this.projectile = projectile;
        this.ac = projectile.ac;
    }

    @Override
    public void render(SpriteBatch batch){
        ParticleEffect bs = projectile.bs;
        ParticleEffect pe = projectile.pe;

        if (ac.getActionState() == Action.MOVING && projectile.parent.anc.isAnimationFinished()) {
            bs.setPosition((pc.x + Constants.TILE_SIZE / 2)/PPM, (pc.y + Constants.TILE_SIZE / 2)/PPM);
            bs.update(0.06f);
            bs.draw(batch);
        }

        if (ac.getActionState() == Action.COLLISION) {
            pe.setPosition((pc.x + Constants.TILE_SIZE / 2)/PPM, (pc.y + Constants.TILE_SIZE / 2)/PPM);
            pe.update(0.06f);
            pe.draw(batch);
        }
    }
}
*/
