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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.pmd.model.components.CameraComponent;
import com.mygdx.pmd.model.components.PositionComponent;
import com.mygdx.pmd.model.components.RenderComponent;
import com.mygdx.pmd.render.Renderer;
import com.mygdx.pmd.utils.Mappers;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RenderSystem extends EntitySystem {
    public static final float PPM = 32;

    private SpriteBatch fBatch;
    private ScreenViewport fViewport;
    private ImmutableArray<Entity> fEntities;
    private ImmutableArray<Entity> fCameraEntities;

    public RenderSystem(SpriteBatch batch) {
        super(3);
        fBatch = batch;
        OrthographicCamera gameCamera = new OrthographicCamera(Gdx.graphics.getWidth() / PPM, Gdx.graphics.getHeight() / PPM);
        gameCamera.zoom -= .5f;
        gameCamera.update();
        fViewport = new ScreenViewport(gameCamera);
        fViewport.setUnitsPerPixel(1 / PPM);
    }

    @Override
    public void addedToEngine(Engine engine) {
        fCameraEntities = engine.getEntitiesFor(
                Family.all(CameraComponent.class, PositionComponent.class).get());
        fEntities = engine.getEntitiesFor(Family.all(RenderComponent.class, PositionComponent.class).get());
    }

    @Override
    public void update(float dt) {
        Gdx.gl.glClearColor(0, 0, 0, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        updateCamera();
        fBatch.setProjectionMatrix(fViewport.getCamera().combined);
        Renderer.getInstance().setProjectionMatrix(fViewport.getCamera().combined);
        Renderer.getInstance().begin(ShapeRenderer.ShapeType.Line);

        fBatch.begin();
        for (Entity entity : orderEntities()) {
            RenderComponent rc = Mappers.Render.get(entity);
            PositionComponent pc = Mappers.Position.get(entity);

            Sprite sprite = rc.getSprite();
            Vector2 pos = pc.getPos();
            float centerX = (pos.x - (sprite.getWidth() / 2)) / PPM;
            float centerY = (pos.y - (sprite.getHeight() / 2)) / PPM;
            fBatch.draw(sprite, centerX, centerY, sprite.getWidth() / PPM, sprite.getHeight() / PPM);
        }
        fBatch.end();
        Renderer.getInstance().end();
        fBatch.flush();
    }

    private void updateCamera() {
        if (fCameraEntities.size() == 0) {
            return;
        }
        Entity cameraEntity = fCameraEntities.first();
        PositionComponent cameraPc = Mappers.Position.get(cameraEntity);
        Vector3 nextPos = (new Vector3(cameraPc.getPos(), 0)).scl(1 / PPM);
        fViewport.getCamera().position.set(nextPos);
        fViewport.getCamera().update();
    }

    private List<Entity> orderEntities() {
        return Arrays.stream(fEntities.toArray())
                .sorted(Comparator.comparingInt(e -> Mappers.Render.get(e).getZIndex()))
                .collect(Collectors.toList());
    }
}
