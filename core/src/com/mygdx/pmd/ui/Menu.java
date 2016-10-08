package com.mygdx.pmd.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.mygdx.pmd.Enumerations.MenuID;

/**
 * Created by Cameron on 9/23/2016.
 */
public class Menu extends Table {

    public Array<Button> updateButtonList;

    public Menu()
    {
        updateButtonList = new Array<Button>();
    }

    public void addButton(Button button)
    {
        this.add(button);
        if(button.classifier != MenuID.DEFAULT)
            updateButtonList.add(button);
    }

}
