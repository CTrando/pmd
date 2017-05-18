package com.mygdx.pmd.model.Entity;

import com.badlogic.gdx.utils.Array;
import com.mygdx.pmd.model.components.*;

/**
 * Created by Cameron on 5/16/2017.
 */
public class Team extends Entity {
    private Array<Entity> entities;
    private MoveComponent mc;
    public Team(){
        entities = new Array<Entity>();
    }

    @Override
    public void init(){
        components.put(MoveComponent.class, new TeamMoveComponent(this));
        mc = getComponent(MoveComponent.class);
    }

    @Override
    public void runLogic() {

    }

    public void addMember(Entity entity){
        entities.add(entity);
    }

    public void addMember(Array<Entity> entities){
        this.entities.addAll(entities);
    }

    public Array<Entity> getEntities() {
        return entities;
    }

    @Override
    public String toString() {
        return null;
    }
}
