package com.mygdx.pmd.screens;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.pmd.PMD;
import com.mygdx.pmd.model.Entity.Pokemon.PokemonPlayer;
import com.mygdx.pmd.model.Floor;
import com.mygdx.pmd.system.MovementSystem;
import com.mygdx.pmd.system.PlayerInputSystem;
import com.mygdx.pmd.system.RenderSystem;

public class DungeonScreen implements Screen {
    private final PMD fGame;
    private final SpriteBatch fBatch;
    private final Floor fFloor;
    private final Engine fEngine;
    private float timeStep = 0;

    public DungeonScreen(final PMD game) {
        fGame = game;
        fBatch = new SpriteBatch();
        fFloor = new Floor();
        fEngine = new Engine();

        fEngine.addSystem(new RenderSystem(fBatch));
        fEngine.addSystem(new MovementSystem());
        fEngine.addSystem(new PlayerInputSystem());
        fEngine.addEntity(new PokemonPlayer());
        fFloor.addToEngine(fEngine);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float dt) {
        timeStep += dt;
        if (timeStep >= 1 / 60f) {
            timeStep = 0;
            fEngine.update(dt);
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
