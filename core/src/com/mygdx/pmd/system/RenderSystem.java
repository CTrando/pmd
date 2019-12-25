package com.mygdx.pmd.system;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.pmd.model.components.PositionComponent;
import com.mygdx.pmd.model.components.RenderComponent;
import com.mygdx.pmd.render.Renderer;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RenderSystem extends EntitySystem {
    private static final float PPM = 32;

    private SpriteBatch fBatch;
    private ScreenViewport fViewport;
    private ComponentMapper<RenderComponent> fRm = ComponentMapper.getFor(RenderComponent.class);
    private ComponentMapper<PositionComponent> fPm = ComponentMapper.getFor(PositionComponent.class);
    private ImmutableArray<Entity> fEntities;

    public RenderSystem(SpriteBatch batch) {
        fBatch = batch;
        Camera gameCamera = new OrthographicCamera(Gdx.graphics.getWidth() / PPM, Gdx.graphics.getHeight() / PPM);
        fViewport = new ScreenViewport(gameCamera);
        fViewport.setUnitsPerPixel(1 / PPM);
    }

    @Override
    public void addedToEngine(Engine engine) {
        fEntities = engine.getEntitiesFor(Family.all(RenderComponent.class, PositionComponent.class).get());
    }

    @Override
    public void update(float dt) {
        Gdx.gl.glClearColor(0, 0, 0, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glEnable(GL20.GL_BLEND);

        fBatch.setProjectionMatrix(fViewport.getCamera().combined);
        Renderer.getInstance().setProjectionMatrix(fViewport.getCamera().combined);
        Renderer.getInstance().begin(ShapeRenderer.ShapeType.Line);

        fBatch.begin();
        for (Entity entity : orderEntities()) {
            RenderComponent rc = fRm.get(entity);
            PositionComponent pc = fPm.get(entity);

            Sprite sprite = rc.getSprite();
            fBatch.draw(sprite, pc.getX() / PPM, pc.getY() / PPM, sprite.getWidth() / PPM, sprite.getHeight() / PPM);
        }
        fBatch.end();
        Renderer.getInstance().end();
        // fBatch.setProjectionMatrix(hud.stage.getCamera().combined);
        fBatch.flush();
    }

    private List<Entity> orderEntities() {
        return Arrays.stream(fEntities.toArray())
                .sorted(Comparator.comparingInt(e -> fRm.get(e).getZIndex()))
                .collect(Collectors.toList());
    }
}
