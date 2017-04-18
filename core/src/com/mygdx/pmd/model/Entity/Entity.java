package com.mygdx.pmd.model.Entity;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.interfaces.*;
import com.mygdx.pmd.model.components.*;
import com.mygdx.pmd.model.logic.AnimationLogic;
import com.mygdx.pmd.model.Floor.*;
import com.mygdx.pmd.model.Tile.*;
import com.mygdx.pmd.model.instructions.*;
import com.mygdx.pmd.utils.*;

import java.util.*;

import static com.mygdx.pmd.screens.DungeonScreen.PPM;

/**
 * Created by Cameron on 10/18/2016.
 */
public abstract class Entity implements Renderable, Updatable, Disposable {
    public LinkedList<Instruction> instructions;
    public Instruction currentInstruction;
    public static final Instruction NO_INSTRUCTION = new NoInstruction();

    /******************************************/
    //Inherited variables from interfaces
    public ActionComponent ac;
    public TurnComponent tc;
    public DirectionComponent dc;
    public MoveComponent mc;
    public PositionComponent pc;

    protected int hp;

    public boolean shouldBeDestroyed;
    /******************************************/

    /*-----------------------------------------*/
    //Position variables
    public Floor floor;
    public Tile[][] tileBoard;
    /*******************************************/
    //Render variables
    public Sprite currentSprite;

    public HashMap<String, PAnimation> animationMap;
    public PAnimation currentAnimation;
    public AnimationLogic animationLogic;
    /********************************************/

    public Entity() {
        instructions = new LinkedList<Instruction>();
        currentInstruction = NO_INSTRUCTION;
    }

    /**
     * current tile is defined by the initial x and y
     */
    public Entity(Floor floor, int x, int y) {

        this.shouldBeDestroyed = false;

        this.floor = floor;
        this.tileBoard = floor.tileBoard;

        animationMap = new HashMap<String, PAnimation>();
        instructions = new LinkedList<Instruction>();
        currentInstruction = NO_INSTRUCTION;

        this.pc = new PositionComponent(this, x,y);
    }

    @Override
    public void update() {
        currentInstruction.execute();
        if (currentInstruction.isFinished()) {
            currentInstruction.onFinish();
            if (instructions.size() > 0) {
                currentInstruction = instructions.poll();
            } else {
                currentInstruction = NO_INSTRUCTION;
            }
            currentInstruction.onInit();
        }

    }

    @Override
    public void render(SpriteBatch batch) {
        if (currentSprite != null) {
            batch.draw(currentSprite, pc.x/PPM, pc.y/PPM, currentSprite.getWidth()/PPM, currentSprite.getHeight()
                    /PPM);
        }
    }

    public abstract void runLogic();

    public boolean equals(Tile tile) {
        return tile != null && (tile.x == pc.x && tile.y == pc.y);
    }

    @Override
    public void dispose() {
    }

    public void reset() {
        instructions.clear();
        currentInstruction = NO_INSTRUCTION;
    }

    public abstract String toString();

}
