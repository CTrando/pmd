package com.mygdx.pmd.model.Factory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.*;
import com.mygdx.pmd.PMD;
import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.model.Behavior.*;
import com.mygdx.pmd.model.Behavior.Entity.*;
import com.mygdx.pmd.model.Behavior.Pokemon.*;
//import com.mygdx.pmd.model.Behavior.Pokemon.PokeMob.*;
import com.mygdx.pmd.model.Behavior.Pokemon.PokePlayer.*;
import com.mygdx.pmd.model.Entity.Pokemon.Pokemon;
import com.mygdx.pmd.model.Entity.Pokemon.PokemonPlayer;
import com.mygdx.pmd.model.Floor.*;
import com.mygdx.pmd.utils.PAnimation;

/**
 * Created by Cameron on 11/6/2016.
 */
public class PokemonFactory {

    public static Pokemon createPokemonFromJson(Floor floor, JsonValue entity) {
        Pokemon pokemon = null;
        PokemonName name = Enum.valueOf(PokemonName.class, entity.getString("name"));
        //check for key player
        if (entity.getString("type").contains("player")) {
            //init players
            pokemon = new PokemonPlayer(floor, name);
            pokemon.add(new PlayerControlledComponent());
            //check for key mob
        } /*else if (entity.getString("type").contains("mob")) {
            //init mobs
            pokemon = new PokemonMob(floor, name);
            pokemon.add(new MobLogicComponent());

        } else {
            pokemon = new PokemonMob(floor, name);

        }*/

        //add custom moves
      /*  if (entity.hasChild("moves")) {
            JsonValue jMoves = entity.get("moves");
            for (JsonValue jMove : jMoves.iterator()) {
                Move move = Enum.valueOf(Move.class, jMove.asString());
                pokemon.moves.add(move);
            }
        }*/
        pokemon.add(new RenderComponent());


        loadAnimations(pokemon, name);

        pokemon.add(new PlayerControlledComponent());
        pokemon.add(new ActionComponent());
        pokemon.add(new PositionComponent(floor, 0, 0));
        pokemon.add(new DirectionComponent());
        pokemon.add(new TurnComponent());
        pokemon.add(new HPComponent());

        return pokemon;
    }

    public static Pokemon createPokemon(Floor floor, PokemonName name, Class ident) {
        Pokemon pokemon;
//        if (ident == PokemonMob.class) {
//            pokemon = new PokemonMob(floor, name);
//            pokemon.add(new PlayerControlledComponent());
//            //pokemon.addComponent(Component.LOGIC, new MobLogicSystem((PokemonMob) pokemon));
//        } else if (ident == PokemonPlayer.class) {
            pokemon = new PokemonPlayer(floor, name);
            pokemon.add(new MobLogicComponent());
      /*  } else pokemon = new PokemonMob(floor, name);*/

        pokemon.add(new RenderComponent());


        loadAnimations(pokemon, name);

        pokemon.add(new PositionComponent(floor, 0, 0));
        pokemon.add(new DirectionComponent());
        pokemon.add(new TurnComponent());

        return pokemon;
    }


    public static void loadAnimations(Pokemon pokemon, PokemonName pokemonName) {
        JsonReader jsonReader = new JsonReader();
        JsonValue animations = jsonReader.parse(Gdx.files.internal("utils/AnimationStorage.json"));

        for (JsonValue animationInfo : animations.iterator()) {
            Array<Sprite> spriteArray = new Array<Sprite>();
            for (JsonValue spriteNames : animationInfo.get("sprites")) {
                spriteArray.add(PMD.sprites.get(pokemonName + spriteNames.asString()));
            }
            Sprite finalSprite = PMD.sprites.get(pokemonName + animationInfo.get("finalSprite").asString());
            PAnimation animation = new PAnimation(animationInfo.name, spriteArray, finalSprite,
                    animationInfo.getInt("length"), animationInfo.getBoolean("loop"));

            RenderComponent rm = Mappers.rm.get(pokemon);
            rm.animationMap.put(animationInfo.name, animation);
        }
    }

}
