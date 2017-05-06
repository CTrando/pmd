package com.mygdx.pmd.model.Tile;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import com.mygdx.pmd.PMD;
import com.mygdx.pmd.model.Entity.*;
import com.mygdx.pmd.model.Entity.Pokemon.*;
import com.mygdx.pmd.model.Floor.*;
import com.mygdx.pmd.model.components.*;
import com.mygdx.pmd.model.instructions.*;
import com.mygdx.pmd.utils.PAnimation;

/**
 * Created by Cameron on 4/25/2017.
 */
public class FireTile extends TrapTile {

    public FireTile(int r, int c, Floor floor) {
        super(r, c, floor, "FIRE");
        this.loadAnimations();

        this.isWalkable = true;
        this.anc = getComponent(AnimationComponent.class);
        this.backgroundSprite = PMD.sprites.get("bricktile");
        rc.setSprite(backgroundSprite, RenderComponent.BACKGROUND);
    }

    @Override
    public void loadAnimations() {
        AnimationComponent anc = new AnimationComponent(this);
        Array<Sprite> sprites = new Array<Sprite>();
        sprites.add(PMD.sprites.get("fire1"));
        sprites.add(PMD.sprites.get("fire2"));
        sprites.add(PMD.sprites.get("fire3"));
        sprites.add(PMD.sprites.get("fire4"));
        sprites.add(PMD.sprites.get("fire5"));
        sprites.add(PMD.sprites.get("fire6"));
        sprites.add(PMD.sprites.get("fire7"));
        sprites.add(PMD.sprites.get("fire8"));

        PAnimation animation = new PAnimation("fire", sprites, 120, true);
        anc.putAnimation("fire", animation);
        components.put(AnimationComponent.class, anc);
    }

    @Override
    public void update() {
        super.update();
        rc.setSprite(anc.getCurrentSprite());

        if(receiver != null) {
            //hasTriggered becomes redundant now but whatever
            if ((currentInstruction == NO_INSTRUCTION || receiver.currentInstruction == NO_INSTRUCTION) &&
                    hasTriggered) {
            }
        }
    }

    @Override
    public void runLogic() {

    }

    @Override
    public void playEvents(Entity receiver){
        if(receiver instanceof PokemonPlayer && !hasTriggered) {
            this.receiver = receiver;
            hasTriggered = true;
            instructions.add(new AnimateInstruction(this, "fire"));
            receiver.instructions.add(new AnimateInstruction(receiver, "pain"));
            receiver.getComponent(CombatComponent.class).takeDamage(this, 50);
            System.out.println(receiver.toString() + " has hit fire!");
        }
    }
}
