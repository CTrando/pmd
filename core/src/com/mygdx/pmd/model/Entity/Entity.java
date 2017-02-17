/*
package com.mygdx.pmd.model.Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.*;
import com.mygdx.pmd.PMD;
import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.interfaces.Renderable;
import com.mygdx.pmd.interfaces.Updatable;
import com.mygdx.pmd.model.Behavior.*;
import com.mygdx.pmd.model.Behavior.Entity.*;
import com.mygdx.pmd.model.Floor.Floor;
import com.mygdx.pmd.model.Tile.Tile;
import com.mygdx.pmd.utils.*;
import com.mygdx.pmd.utils.observers.NoObserver;
import com.mygdx.pmd.utils.observers.Observable;
import com.mygdx.pmd.utils.observers.Observer;

import java.util.HashMap;

*/
/**
 * Created by Cameron on 10/18/2016.
 *//*

public abstract class Entity implements Renderable, Updatable, Observable {
    public Array<Component> components;
    public Array<PSystem> systems;

    public int speed = 1;

    public PAnimation currentAnimation;

    public boolean shouldBeDestroyed;


    public Sprite currentSprite;
    public HashMap<String, PAnimation> animationMap;
    protected Observer[] observers;


    public Entity() {
        initComponents();
        systems = new Array<PSystem>();
    }

    */
/**
     * current tile is defined by the initial x and y
     *//*

    public Entity(Floor floor, int x, int y) {
        systems = new Array<PSystem>();
        animationMap = new HashMap<String, PAnimation>();

        //initialize behaviors array
        initObservers();
        initComponents();
    }

    public void initComponents() {
        components = new Array<Component>();
        for (int i = 0; i < 10; i++) {
            components.add(Component.NOCOMPONENT);
        }
    }

    public boolean componentExists(int index) {
        return components.get(index) != null;
    }

    public void addComponent(int index, Component component) {
        components.set(index, component);
    }

    public void removeComponent(int index) {
        components.removeIndex(index);
    }

    public Component getComponent(int index) {
        return components.get(index);
    }

    private void initObservers() {
        observers = new Observer[10];
        for (int i = 0; i < observers.length; i++) {
            observers[i] = new NoObserver(this);
        }
    }

    @Override
    public void update() {
        for (PSystem system : systems) {
            system.update();
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        if (currentSprite != null) {
            batch.draw(currentSprite, x, y, currentSprite.getWidth(), currentSprite.getHeight());
        }
    }

    public void notifyObservers() {
        for (int i = 0; i < observers.length; i++) {
            observers[i].update();
        }
    }
*/
/*
    public void randomizeLocation() {
        Tile random = floor.chooseUnoccupiedTile();

        if (random.isWalkable) {
            this.setNextTile(random);
            this.setCurrentTile(random);
            this.possibleNextTile = null;
        } else randomizeLocation();

        this.setActionState(Action.IDLE);
        this.setTurnState(Turn.COMPLETE);
    }*//*






    public abstract void dispose();



    public void setSpeed(int speed) {
        this.speed = speed;
    }

}
*/
