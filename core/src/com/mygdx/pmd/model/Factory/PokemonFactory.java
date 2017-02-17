package com.mygdx.pmd.model.Factory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.*;
import com.mygdx.pmd.PMD;
import com.mygdx.pmd.controller.Controller;
import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.model.Behavior.*;
import com.mygdx.pmd.model.Behavior.Entity.*;
import com.mygdx.pmd.model.Behavior.Pokemon.*;
import com.mygdx.pmd.model.Behavior.Pokemon.PokeMob.*;
import com.mygdx.pmd.model.Behavior.Pokemon.PokePlayer.*;
import com.mygdx.pmd.model.Entity.Pokemon.Pokemon;
import com.mygdx.pmd.model.Entity.Pokemon.PokemonMob;
import com.mygdx.pmd.model.Entity.Pokemon.PokemonPlayer;
import com.mygdx.pmd.model.Floor.*;
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
            //init players
            pokemon = new PokemonPlayer(floor, name);
            pokemon.addComponent(Component.LOGIC, new PlayerLogicComponent((PokemonPlayer) pokemon));
            //check for key mob
        } else if (entity.getString("type").contains("mob")) {
            //init mobs
            pokemon = new PokemonMob(floor, name);
            pokemon.addComponent(Component.LOGIC, new MobLogicComponent((PokemonMob) pokemon));

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

        pokemon.addComponent(Component.MOVE, new MoveComponent(pokemon));
        pokemon.addComponent(Component.ATTACK, new AttackComponent(pokemon));
        pokemon.addComponent(Component.ANIMATION, new PokemonAnimationComponent(pokemon));
        pokemon.addComponent(Component.DIRECTION, new DirectionComponent(pokemon));

        return pokemon;
    }

    public static Pokemon createPokemon(Floor floor, PokemonName name, Class ident) {
        Pokemon pokemon;
        if (ident == PokemonMob.class) {
            pokemon = new PokemonMob(floor, name);
            pokemon.addComponent(Component.LOGIC, new MobLogicComponent((PokemonMob) pokemon));
        } else if (ident == PokemonPlayer.class) {
            pokemon = new PokemonPlayer(floor, name);
            pokemon.addComponent(Component.LOGIC, new PlayerLogicComponent((PokemonPlayer) pokemon));
        } else pokemon = new PokemonMob(floor, name);

        loadAnimations(pokemon, name);


        pokemon.addComponent(Component.MOVE, new MoveComponent(pokemon));
        pokemon.addComponent(Component.ATTACK, new AttackComponent(pokemon));
        pokemon.addComponent(Component.ANIMATION, new PokemonAnimationComponent(pokemon));
        pokemon.addComponent(Component.DIRECTION, new DirectionComponent(pokemon));

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

            pokemon.animationMap.put(animationInfo.name, animation);
        }
    }

}
