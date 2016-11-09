package com.mygdx.pmd.Model.Behavior;

import com.mygdx.pmd.Controller.Controller;
import com.mygdx.pmd.Model.Pokemon.Pokemon;
import com.mygdx.pmd.Model.TileType.Tile;

/**
 * Created by Cameron on 11/8/2016.
 */
public abstract class Behavior {
    public static final int INPUT_BEHAVIOR = 0;
    public static final int LOGIC_BEHAVIOR = 1;
    public static final int MOVE_BEHAVIOR = 2;
    public static final int ANIMATION_BEHAVIOR = 3;

    Controller controller;
    Tile[][] tileBoard;
    Pokemon pokemon;

    public Behavior(Pokemon pokemon){
        this.controller = pokemon.controller;
        this.tileBoard = controller.tileBoard;
        this.pokemon = pokemon;
    }

    public abstract void execute();
}
