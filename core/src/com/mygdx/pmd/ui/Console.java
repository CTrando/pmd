package com.mygdx.pmd.ui;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.mygdx.pmd.controller.Controller;
import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.model.Entity.Pokemon.*;

/**
 * Created by Cameron on 5/7/2017.
 */
public class Console extends Table {
    private Label label;
    private TextField commandEnter;
    private StringBuilder log;
    private Hud hud;
    private Controller controller;

    public Console(final Hud hud) {
        super(hud.getSkin());
        this.hud = hud;
        this.controller = hud.getController();
        log = new StringBuilder();

        label = new Label("Hello world", getSkin());
        label.setAlignment(Align.bottomLeft);
        add(label)
                .fill()
                .width(500)
                .row();

        commandEnter = new TextField("Enter your command here", getSkin());
        commandEnter.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hud.requestFocus(commandEnter);
            }
        });

        commandEnter.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (event.getKeyCode() == Input.Keys.ENTER) {
                    handleInput(commandEnter.getText());
                    commandEnter.setText("");
                }
                return false;
            }
        });

        label.setWrap(true);
        add(commandEnter).fill();
    }

    public void update(float dt) {

    }

    private void handleInput(String command) {
        if (log.length() != 0) {
            log.append("\n").append(command);
        } else {
            log.append(command);
        }
        String[] components = command.split(" ");


        /*if (components[0].equals("/spawn")) {
            int row = Integer.parseInt(components[1]);
            int col = Integer.parseInt(components[2]);

            controller.toBeAdded(PokemonFactory.createPokemon(controller.floor,
                                                              PokemonName.squirtle,
                                                              PokemonMob.class));
            log.append("\n").append("Spawn was successful!");
        }
*/
        label.setText(log.toString());

    }


}
