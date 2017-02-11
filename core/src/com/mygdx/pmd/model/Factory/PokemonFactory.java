package com.mygdx.pmd.model.Factory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.*;
import com.mygdx.pmd.controller.Controller;
import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.model.Entity.Pokemon.Pokemon;
import com.mygdx.pmd.model.Entity.Pokemon.PokemonMob;
import com.mygdx.pmd.model.Entity.Pokemon.PokemonPlayer;

/**
 * Created by Cameron on 11/6/2016.
 */
public class PokemonFactory {

    public static Pokemon createPokemonFromJson(Controller controller, JsonValue entity) {
        Pokemon pokemon;
        PokemonName name = Enum.valueOf(PokemonName.class, entity.getString("name"));
        //check for key player
        if (entity.getString("type").contains("player")) {
            //init players
            pokemon = new PokemonPlayer(controller, 0, 0, name);
            //check for key mob
        } else if (entity.getString("type").contains("mob")) {
            //init mobs
            pokemon = new PokemonMob(controller, 0, 0, name);
        } else {
            pokemon = new PokemonMob(controller, 0, 0, name);
        }

        //add custom moves
        if (entity.hasChild("moves")) {
            JsonValue jMoves = entity.get("moves");
            for (JsonValue jMove : jMoves.iterator()) {
                Move move = Enum.valueOf(Move.class, jMove.asString());
                pokemon.moves.add(move);
            }
        }

        return pokemon;
    }

    public static Pokemon createPokemon(Controller controller, PokemonName name, Class ident) {
        Pokemon pokemon = null;
        if (ident == PokemonMob.class) {
            pokemon = new PokemonMob(controller, 0, 0, name);
        } else if (ident == PokemonPlayer.class) {
            pokemon = new PokemonPlayer(controller, 0, 0, name);
        }
        return pokemon;
    }

}
