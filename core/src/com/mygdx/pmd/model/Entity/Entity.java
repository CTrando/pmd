package com.mygdx.pmd.model.Entity;

import com.badlogic.gdx.utils.*;
import com.mygdx.pmd.interfaces.Updatable;
import com.mygdx.pmd.model.Floor.*;
import com.mygdx.pmd.model.Tile.*;
import com.mygdx.pmd.model.components.*;
import com.mygdx.pmd.model.instructions.*;

import java.util.*;

/**
 * Created by Cameron on 10/18/2016.
 */
public abstract class Entity implements Updatable, Disposable {
    public LinkedList<Instruction> instructions;
    public Instruction currentInstruction;
    public static final Instruction NO_INSTRUCTION = new NoInstruction();

    public Array<Entity> children;

    /******************************************/
    //Inherited variables from interfaces

    public HashMap<Class, Component> components;
    public boolean shouldBeDestroyed;
    /******************************************/

    /*-----------------------------------------*/
    //Position variables
    public Floor floor;
    public Tile[][] tileBoard;
    /*******************************************/
    //Render variables
    /********************************************/

    public Entity() {
        components = new HashMap<Class, Component>();
        instructions = new LinkedList<Instruction>();
        currentInstruction = NO_INSTRUCTION;
    }

    /**
     * current tile is defined by the initial x and y
     */
    public Entity(Floor floor, int x, int y) {
        components = new HashMap<Class, Component>();
        this.shouldBeDestroyed = false;

        this.floor = floor;
        this.tileBoard = floor.tileBoard;
        this.children = new Array<Entity>();

        instructions = new LinkedList<Instruction>();
        currentInstruction = NO_INSTRUCTION;

        components.put(PositionComponent.class, new PositionComponent(this, x,y));
    }

    public void init(){

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

    public abstract void runLogic();

    public boolean equals(Tile tile) {
        PositionComponent pc = this.getComponent(PositionComponent.class);
        return tile != null && (tile.x == pc.x && tile.y == pc.y);
    }

    public boolean hasComponent(Class type){
        return components.get(type) != null;
    }

    public <T extends Component> T getComponent(Class<T> type){
        return type.cast(components.get(type));
    }

    @Override
    public void dispose() {
    }

    public void reset() {
        instructions.clear();
        currentInstruction = NO_INSTRUCTION;
    }

    public abstract String toString();

    public boolean hasChildren() {
        return children.size > 0;
    }
}
