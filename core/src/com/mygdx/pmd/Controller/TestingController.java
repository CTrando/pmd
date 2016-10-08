package com.mygdx.pmd.Controller;


import com.badlogic.gdx.Screen;
import com.mygdx.pmd.Enumerations.PokemonName;
import com.mygdx.pmd.Enumerations.State;
import com.mygdx.pmd.Model.Pokemon.Pokemon;
import com.mygdx.pmd.Model.Pokemon.PokemonMob;
import com.mygdx.pmd.Model.Pokemon.PokemonPlayer;
import com.mygdx.pmd.Screen.DungeonScreen;

public class TestingController extends Controller
{
    public TestingController(DungeonScreen controllerScreen)
    {
        super(controllerScreen);
        Pokemon treeko = new PokemonPlayer(0, 0, this, true, PokemonName.treeko);
        addEntity(treeko);

        Pokemon treek = new PokemonMob(0, 0, this, true, PokemonName.treeko, State.passive);
        addEntity(treek);

        Pokemon treek1 = new PokemonMob(0, 0, this, true, PokemonName.squirtle, State.aggressive);
        addEntity(treek1);

        this.randomizeAllPokemonLocation();
    }


}