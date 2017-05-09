package com.mygdx.pmd.ui;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.utils.StringBuilder;
import com.badlogic.gdx.utils.viewport.*;
import com.mygdx.pmd.PMD;
import com.mygdx.pmd.controller.Controller;
import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.model.Entity.Pokemon.*;
import com.mygdx.pmd.screens.DungeonScreen;

/**
 * Created by Cameron on 11/26/2016.
 */
public class Hud {
    public Stage stage;
    public ScreenViewport viewport;

    public OrthographicCamera hudCam;
    private StringBuilder inputText;

    private Skin skin;
    private Console console;
    private Table rootTable;

    private Label floorLabel;
    private Label healthLabel;
    private Label turnLabel;
    private Label textLabel;
    private Label fpsCounter;
    private Label tilePos;

    BitmapFont customFont;
    GlyphLayout gLayout;
    DungeonScreen screen;

    private PokemonPlayer player;

    public Hud(final DungeonScreen screen, SpriteBatch batch) {
        //TODO organize this badly
        this.screen = screen;
        this.player = (PokemonPlayer) screen.controller.pokemonPlayer;
        customFont = new BitmapFont(Gdx.files.internal("ui/myCustomFont.fnt"));
        gLayout = new GlyphLayout(customFont, "");
        inputText = new StringBuilder();

        hudCam = new OrthographicCamera();

        viewport = new ScreenViewport(hudCam);
        stage = new Stage(viewport, batch);

        Table onScreenController = new Table();
        onScreenController.center();
        onScreenController.top();
        onScreenController.setFillParent(true);
        this.skin = new Skin(Gdx.files.internal("ui/skin/flat-earth-ui.json"));

        console = new Console(this);


        healthLabel = new Label("HP: " + player.cc.getHp(), skin);
        floorLabel = new Label("Floor: " + Controller.floorCount, skin);
        turnLabel = new Label("Turns left: " + Controller.turns, skin);
        textLabel = new Label(inputText.toString(), skin);
        fpsCounter = new Label("FPS: " + Gdx.graphics.getFramesPerSecond(), skin);
        tilePos = new Label("row: " + player.pc.getCurrentTile().row + ", col: " +
                                    player.pc.getCurrentTile().col, skin);

        healthLabel.setAlignment(Align.center);
        floorLabel.setAlignment(Align.center);
        turnLabel.setAlignment(Align.center);
        textLabel.setAlignment(Align.center);
        fpsCounter.setAlignment(Align.center);
        tilePos.setAlignment(Align.center);
        stage.setDebugAll(true);

        TextButton attackText = new TextButton("Swiper no Swiping", skin);
        attackText.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                screen.controller.pokemonPlayer.setMove(Move.SWIPERNOSWIPING);
            }
        });

        TextButton test = new TextButton("INSTANT KILLER", skin);
        test.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                screen.controller.pokemonPlayer.setMove(Move.INSTANT_KILLER);
            }
        });

        Image upImg = new Image(PMD.sprites.get("uparrow"));
        upImg.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                PMD.keys.get(Input.Keys.UP).set(true);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                PMD.keys.get(Input.Keys.UP).set(false);
            }
        });

        Image downImg = new Image(PMD.sprites.get("downarrow"));
        downImg.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                PMD.keys.get(Input.Keys.DOWN).set(true);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                PMD.keys.get(Input.Keys.DOWN).set(false);
            }
        });

        Image leftImg = new Image(PMD.sprites.get("leftarrow"));
        leftImg.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                PMD.keys.get(Input.Keys.LEFT).set(true);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                PMD.keys.get(Input.Keys.LEFT).set(false);
            }
        });

        Image rightImg = new Image(PMD.sprites.get("rightarrow"));
        rightImg.setWidth(rightImg.getWidth() / 2);
        rightImg.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                PMD.keys.get(Input.Keys.RIGHT).set(true);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                PMD.keys.get(Input.Keys.RIGHT).set(false);
            }
        });

        Image attackImg = new Image(PMD.sprites.get("attackimage"));
        attackImg.setWidth(attackImg.getWidth() / 2);
        attackImg.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                PMD.keys.get(Input.Keys.B).set(true);
                PMD.keys.get(Input.Keys.T).set(true);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                PMD.keys.get(Input.Keys.B).set(false);
                PMD.keys.get(Input.Keys.T).set(false);
            }
        });

        //TODO fix the controller so it is its own thing also fix for being able to press when off screen
        onScreenController.debug();

        onScreenController.add();
        onScreenController.add(upImg).pad(15, 15, 15, 15);
        onScreenController.row();
        onScreenController.add(leftImg).pad(15, 15, 15, 15);
        onScreenController.add(attackImg).pad(15, 15, 15, 15);
        onScreenController.add(rightImg).pad(15, 15, 15, 15);
        onScreenController.row();
        onScreenController.add();
        onScreenController.add(downImg).pad(15, 15, 15, 15);

        rootTable = new Table();
        rootTable.setFillParent(true);

        Table testTable = new Table();

        Table hudTable = new Table();
        hudTable.add(tilePos).fill().row();
        hudTable.add(healthLabel).fill();
        hudTable.row();
        hudTable.add(floorLabel).fill();
        hudTable.row();
        hudTable.add(turnLabel).fill();
        hudTable.row();
        hudTable.add(fpsCounter).fill();
        hudTable.row();
        loadAttackTextButtons(hudTable);
        hudTable.pack();

        testTable.add(hudTable)
                 .expand()
                 .align(Align.bottomLeft);

        testTable.add(console)
                 .expand()
                 .align(Align.bottomRight)
                 .pad(5f);


        testTable.pack();

        Table textTable = new Table();
        textTable.add(textLabel);
        textTable.row();
        textTable.pack();

        rootTable.add(textTable)
                 .expand()
                 .align(Align.topRight)
                 .row();

        rootTable.add(testTable)
                 .expand()
                 .fill();

        rootTable.pack();

        onScreenController.right().padRight(10).bottom();

        if (Gdx.app.getType() == Application.ApplicationType.Android) {
            stage.addActor(onScreenController);
        }

        stage.addActor(rootTable);
    }

    public void update(float dt) {
        player = (PokemonPlayer) screen.controller.pokemonPlayer;
        stage.act();
        console.update(dt);
        //reason why not appearing is because did not include : in bit map font
        healthLabel.setText("HP: " + screen.controller.pokemonPlayer.cc.getHp());
        floorLabel.setText("Floor: " + Controller.floorCount);
        if (Controller.turnsPaused) {
            customFont.setColor(Color.BLUE);
        }

        fpsCounter.setText("FPS: " + Gdx.graphics.getFramesPerSecond());
        turnLabel.setText("Turns left: " + Controller.turns);
        tilePos.setText("row: " + player.pc.getCurrentTile().row + ", col: " +
                                    player.pc.getCurrentTile().col);

        String currentText = inputText.toString();
        gLayout.setText(customFont, inputText);
        if (gLayout.width < Gdx.graphics.getWidth()) {
            textLabel.setText(currentText);
        } else {
            inputText.setLength(0);
        }
    }

    public void addText(String str) {
        inputText.append(str);
    }

    public void reset() {
        inputText.setLength(0);
    }

    public void loadAttackTextButtons(Table table) {
        JsonReader reader = new JsonReader();
        JsonValue value = reader.parse(Gdx.files.internal("ui/menu.json"));
        JsonValue moves = value.get("moves");

        for (final JsonValue move : moves.iterator()) {
            TextButton test = new TextButton(move.asString().toLowerCase(), skin);
            test.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    screen.controller.pokemonPlayer.setMove(Enum.valueOf(Move.class, move.asString()));
                    screen.toggleHub();
                }
            });
            table.add(test).fill();
            table.row();
        }
    }

    public void requestFocus(Actor actor) {
        stage.setKeyboardFocus(actor);
    }

    public Skin getSkin() {
        return skin;
    }

    public void setVisible(boolean visible) {
        rootTable.setVisible(visible);
    }

    public boolean isVisible() {
        return rootTable.isVisible();
    }

    public Console getConsole() {
        return console;
    }

    public Controller getController() {
        return screen.controller;
    }
}
