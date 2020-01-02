package com.mygdx.pmd.screens;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.pmd.PMD;
import com.mygdx.pmd.model.entity.pokemon.PokemonMob;
import com.mygdx.pmd.model.entity.pokemon.PokemonPlayer;
import com.mygdx.pmd.model.Floor;
import com.mygdx.pmd.system.*;
import com.mygdx.pmd.system.input.PlayerInputSystem;
import com.mygdx.pmd.system.input.PokemonInputSystem;

public class DungeonScreen implements Screen {
    private final PMD fGame;
    private final SpriteBatch fBatch;
    private final Floor fFloor;
    private final Engine fEngine;

    public DungeonScreen(final PMD game) {
        fGame = game;
        fBatch = new SpriteBatch();
        fFloor = new Floor();
        fEngine = new Engine();


        // Add listeners first so they get triggered with adding other entities
        // Add floor first so tiles don't include themselves as entities
        fFloor.addToEngine(fEngine);
        fEngine.addEntityListener(fFloor);

        fEngine.addSystem(new AnimationSystem());
        fEngine.addSystem(new PlayerInputSystem());
        fEngine.addSystem(new PokemonInputSystem(fFloor));
        fEngine.addSystem(new InputSystem());
        fEngine.addSystem(new MovementSystem());
        fEngine.addSystem(new MobSystem());
        fEngine.addSystem(new TurnSystem());
        fEngine.addSystem(new RenderSystem(fBatch));
        fEngine.addSystem(new SequenceSystem());
        fEngine.addEntity(new PokemonPlayer());
        //fEngine.addEntity(new PokemonMob());
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float dt) {
        fEngine.update(dt);
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
