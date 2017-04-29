package com.mygdx.pmd.model.Entity.Pokemon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.*;
import com.mygdx.pmd.PMD;
import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.model.Floor.*;
import com.mygdx.pmd.model.components.*;
import com.mygdx.pmd.utils.PAnimation;

/**
 * Created by Cameron on 11/6/2016.
 */
public class PokemonFactory {

    public static Pokemon createPokemonFromJson(Floor floor, JsonValue entity) {
        Pokemon pokemon;
        PokemonName name = Enum.valueOf(PokemonName.class, entity.getString("name"));
        //check for key player
        if (entity.getString("type").contains("player")) {
            //onInit players
            pokemon = new PokemonPlayer(floor, name);

            //check for key mob
        } else if (entity.getString("type").contains("mob")) {
            //onInit mobs
            pokemon = new PokemonMob(floor, name);
        } else {
            pokemon = new PokemonMob(floor, name);
        }

        //add custom moves
        if (entity.hasChild("moves")) {
            JsonValue jMoves = entity.get("moves");
            for (JsonValue jMove : jMoves.iterator()) {
                Move move = Enum.valueOf(Move.class, jMove.asString());
                pokemon.moves.add(move);
            }
        }

        loadAnimations(pokemon, name);
        pokemon.init();
        return pokemon;
    }

    public static Pokemon createPokemon(Floor floor, PokemonName name, Class ident) {
        Pokemon pokemon = null;
        if (ident == PokemonMob.class) {
            pokemon = new PokemonMob(floor, name);
        } else if (ident == PokemonPlayer.class) {
            pokemon = new PokemonPlayer(floor, name);
        }

        pokemon.moves.add(Move.SWIPERNOSWIPING);
        loadAnimations(pokemon, name);
        pokemon.init();
        return pokemon;
    }

    private static void loadAnimations(Pokemon pokemon, PokemonName pokemonName){
        AnimationComponent anc = new AnimationComponent(pokemon);

        JsonReader jsonReader = new JsonReader();
        //if you want to add animations alter the animation storage json
        JsonValue animations = jsonReader.parse(Gdx.files.internal("utils/AnimationStorage.json"));

        for(JsonValue animationInfo: animations.iterator()){
            Array<Sprite> spriteArray = new Array<Sprite>();
            for(JsonValue spriteNames: animationInfo.get("sprites")){
                spriteArray.add(PMD.sprites.get(pokemonName + spriteNames.asString()));
            }
            PAnimation animation = new PAnimation(animationInfo.name, spriteArray,
                    animationInfo.getInt("length"), animationInfo.getBoolean("loop"));

            anc.putAnimation(animationInfo.name, animation);
        }
        pokemon.components.put(AnimationComponent.class, anc);
    }



}
