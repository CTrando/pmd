package com.mygdx.pmd.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.pmd.controller.Controller;
import com.mygdx.pmd.enumerations.MenuID;

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
                this.setText("Current hp: " + controller.pokemonPlayer.getHp() + "");
                break;
            case ANIMATION:
                if(controller.pokemonPlayer.currentAnimation != null)
                    this.setText("Current Animation: " + controller.pokemonPlayer.currentAnimation.toString());
                else this.setText("Current Animation: " + "noanimation");
                break;
            case ACTION:
                    this.setText("Turn: " + controller.turnBasedEntities.get(0).turnState.toString() + " State: " + controller.turnBasedEntities.get(0).getActionState().toString() +"\n " + controller.turnBasedEntities.get(0).previousState.toString() + "\n"
                    + "Turn: " + controller.turnBasedEntities.get(1).turnState.toString() + " State: " + controller.turnBasedEntities.get(1).getActionState().toString()+ "\n" +
                            "Turn: " + controller.turnBasedEntities.get(2).turnState.toString() + " State: " + controller.turnBasedEntities.get(2).getActionState().toString()
                    );
                break;
            case STATE:
                this.setText("State: " + controller.pokemonPlayer.getActionState().toString());
                break;
            case ATTACK:
                break;
            case DIRECTION:
                this.setText("Direction: " + controller.pokemonPlayer.direction.toString());
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
                    if(controller.pokemonPlayer.currentAnimation.isFinished())
                        return;
                }
            }
        });
    }


}
