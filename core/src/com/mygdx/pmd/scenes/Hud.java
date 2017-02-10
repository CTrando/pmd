package com.mygdx.pmd.scenes;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.*;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.utils.StringBuilder;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.pmd.PMD;
import com.mygdx.pmd.controller.Controller;
import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.screens.DungeonScreen;

import javax.xml.soap.Text;

/**
 * Created by Cameron on 11/26/2016.
 */
public class Hud {
    public Stage stage;
    public Viewport viewport;
    private float accumTime = 0;

    StringBuilder inputText;

    Skin skin;

    Label timeLabel;
    Label floorLabel;
    Label testLabel;
    Label turnLabel;
    Label textLabel;

    BitmapFont customFont;
    GlyphLayout gLayout;
    DungeonScreen screen;

    public Hud(final DungeonScreen screen, SpriteBatch batch) {
        //TODO organize this badly
        this.screen = screen;
        customFont = new BitmapFont(Gdx.files.internal("ui/myCustomFont.fnt"));
        gLayout = new GlyphLayout(customFont, "");
        inputText = new StringBuilder();

        OrthographicCamera hudCam = new OrthographicCamera();

        viewport = new ScreenViewport(hudCam);
        stage = new Stage(viewport, batch);

        Table onScreenController = new Table();
        onScreenController.center();
        onScreenController.top();
        onScreenController.setFillParent(true);
        skin = new Skin(Gdx.files.internal("ui/test.json"));

        testLabel = new Label("HP: " + screen.controller.pokemonPlayer.hp, skin);
        floorLabel = new Label("Floor: " + Controller.floorCount, skin);
        turnLabel = new Label("Turns left: " + Controller.turns, skin);
        textLabel = new Label(inputText.toString(), skin);

        TextButton attackText = new TextButton("Swiper no Swiping", skin);
        attackText.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                screen.controller.pokemonPlayer.move = Move.SWIPERNOSWIPING;
            }
        });

        TextButton test = new TextButton("INSTANT KILLER", skin);
        test.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                screen.controller.pokemonPlayer.move = Move.INSTANT_KILLER;
            }
        });


        Image upImg = new Image(PMD.sprites.get("uparrow"));
        //upImg.setScale(.5f);
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
        //downImg.setScale(.5f);
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
        //leftImg.setScale(.5f);
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
        //rightImg.setScale(.5f);
        rightImg.setWidth(rightImg.getWidth()/2);
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
        //attackImg.setScale(.5f);
        attackImg.setWidth(attackImg.getWidth()/2);
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

        //TODO fix the controller so it is its own thing
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

        Table temp = new Table();
        temp.left().bottom();
        temp.debug();
        temp.add(testLabel).fill();
        temp.row();
        temp.add(floorLabel).fill();
        temp.row();
        temp.add(turnLabel).fill();
        temp.row();
        loadAttackTextButtons(temp);
       /* temp.add(attackText).fill();
        temp.row();
        temp.add(test).fill();*/
        temp.setBackground(new SpriteDrawable(PMD.sprites.get("rightarrow")));
        temp.pack();

        Table textTable = new Table();
        textTable.setFillParent(true);
        textTable.right().top();
        textTable.debug();
        textTable.add(textLabel);
        textTable.row();

        onScreenController.right().padRight(10).bottom();

        if (Gdx.app.getType() == Application.ApplicationType.Android)
            stage.addActor(onScreenController);
        
        stage.addActor(textTable);
        stage.addActor(temp);
    }

    public void update(float dt) {
        //reason why not appearing is because did not include : in bit map font
        testLabel.setText("HP: " + screen.controller.pokemonPlayer.hp);
        floorLabel.setText("Floor: " + Controller.floorCount);
        if(Controller.turnsPaused){
            customFont.setColor(Color.BLUE);
        }

        turnLabel.setText("Turns left: " + Controller.turns);

        String currentText = inputText.toString();
        gLayout.setText(customFont, inputText);
        if(gLayout.width < Gdx.graphics.getWidth())
            textLabel.setText(currentText);
        else inputText.setLength(0);
    }

    public void addText(String str){
        inputText.append(str);
    }

    public void reset(){
        inputText.setLength(0);
    }

    public void loadAttackTextButtons(Table table){
        JsonReader reader = new JsonReader();
        JsonValue value = reader.parse(Gdx.files.internal("ui/menu.json"));
        JsonValue moves = value.get("moves");

        for(final JsonValue move: moves.iterator()){
            TextButton test = new TextButton(move.asString().toLowerCase(), skin);
            test.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    screen.controller.pokemonPlayer.move = Enum.valueOf(Move.class, move.asString());
                    screen.toggleHub();
                }
            });
            table.add(test).fill();
            table.row();
        }
    }
}
