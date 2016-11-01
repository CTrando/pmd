package com.mygdx.pmd.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.pmd.Controller.Controller;
import com.mygdx.pmd.Enumerations.MenuID;
import com.mygdx.pmd.Model.Pokemon.Pokemon;

/**
 * Created by Cameron on 9/11/2016.
 */
public class Button extends TextButton {
    Table parent;
    public MenuID classifier;
    public Controller controller;
    public String nextMenu;

    public Button(String text, String classifier, Skin skin, Table parent, String nextMenu, Controller controller)// Table nextTable)
    {
        super(text, skin);
        this.classifier = Enum.valueOf(MenuID.class, classifier);
        this.changeEvent();
        this.parent = parent;
        this.controller = controller;
        this.nextMenu = nextMenu;
    }

    public void update()
    {
        switch(this.classifier) {
            case PPHP:
                this.setText("Current hp: " + controller.getPokemonPlayer().getHp() + "");
                break;
            case ANIMATION:
                if(controller.getPokemonPlayer().currentAnimation != null)
                    this.setText("Current Animation: " + controller.getPokemonPlayer().currentAnimation.toString());
                else this.setText("Current Animation: " + "noanimation");
                break;
            case ACTION:
                    this.setText("Turn: " + controller.pokemonList.get(0).turnState.toString() + "\n"
                    + "Turn: " + controller.pokemonList.get(1).turnState.toString() + "\n" +
                            "Turn: " + controller.pokemonList.get(2).turnState.toString());
                break;
            case STATE:
                this.setText("State: " + controller.getPokemonPlayer().actionState.toString());
                break;
            case ATTACK:
                break;
            case DIRECTION:
                this.setText("Direction: " + controller.getPokemonPlayer().direction.toString());
                break;
        }
    }

    public void changeEvent()
    {
        this.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("I have been clicked");
                if(nextMenu.length() > 0)
                    controller.controllerScreen.switchMenus(nextMenu);

                if(classifier == MenuID.ATTACK){
                    if(controller.getPokemonPlayer().currentAnimation.isFinished())
                        return;
                }

            }
        });
    }


}
