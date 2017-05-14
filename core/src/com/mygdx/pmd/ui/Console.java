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

        commandEnter = new TextField("Enter your command here.", getSkin());
        commandEnter.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                requestFocus();
            }
        });

        commandEnter.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                switch (event.getKeyCode()) {
                    case Input.Keys.ENTER:
                        handleInput(commandEnter.getText());
                        commandEnter.setText("");
                        break;
                    case Input.Keys.ESCAPE:
                        //TODO the escapes are conflicting with each other fix it
                        cancelFocus();
                        break;
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
        String[] components = command.split(" ");
        if (command.equals("")) {
            return;
        }
        writeLine(command);

        if (components[0].equals("/invincible")) {
            controller.pokemonPlayer.cc.setInvincible(true);
            writeLine("You are now invincible. This is not undoable");
        }

        if (components[0].equals("/add")) {
            try {
                String operation = components[1];
                if (operation.equals("turn")) {
                    int addTurns = Integer.parseInt(components[2]);
                    Controller.turns += addTurns;
                    writeLine("Added " + addTurns + " turn(s) successfully!");
                } else if (operation.equals("health")){
                    int addHealth = Integer.parseInt(components[2]);
                    controller.pokemonPlayer.cc.addHP(addHealth);
                    writeLine("Added " + addHealth + " HP successfully!");
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                writeLine("Invalid parameters, try adding a correct operation.");
            } catch (NumberFormatException e) {
                writeLine("Invalid parameter, must have number after operation.");
            }
        }

        if (components[0].equals("/spawn")) {
            try {
                int row = Integer.parseInt(components[1]);
                int col = Integer.parseInt(components[2]);

                PokemonConfig config = new PokemonConfig();
                config.currentTile = controller.floor.tileBoard[row][col];
                config.name = PokemonName.squirtle;
                config.type = PokemonMob.class;
                config.floor = controller.floor;

                Pokemon pokemon = PokemonFactory.createPokemon(config);

                controller.toBeAdded(pokemon);
                writeLine("Spawn was successful!");

            } catch (NumberFormatException e) {
                writeLine("Incorrect parameter types, try again.");
            } catch (ArrayIndexOutOfBoundsException e) {
                writeLine("Incorrect number of parameters, try again.");
            }
        }
    }

    public void writeLine(String command) {
        if (log.length() != 0) {
            log.append("\n").append(command);
        } else {
            log.append(command);
        }
        label.setText(log.toString());
    }

    public void requestFocus() {
        commandEnter.setText("");
        hud.requestFocus(commandEnter);
    }

    public void cancelFocus() {
        hud.stage.setKeyboardFocus(null);
        commandEnter.setText("Enter your command here.");
    }

}
