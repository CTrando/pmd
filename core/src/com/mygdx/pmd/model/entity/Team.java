//package com.mygdx.pmd.model.Entity;
//
//import com.badlogic.gdx.utils.Array;
//import com.mygdx.pmd.model.components.*;
//
///**
// * Created by Cameron on 5/16/2017.
// */
//public class Team extends TestEntity {
//    private Array<TestEntity> entities;
//    private MoveComponent mc;
//    public Team(){
//        entities = new Array<TestEntity>();
//    }
//
//    @Override
//    public void init(){
//        components.put(MoveComponent.class, new TeamMoveComponent(this));
//        mc = getComponent(MoveComponent.class);
//    }
//
//    @Override
//    public void runLogic() {
//
//    }
//
//    public void addMember(TestEntity entity){
//        entities.add(entity);
//    }
//
//    public void addMember(Array<TestEntity> entities){
//        this.entities.addAll(entities);
//    }
//
//    public Array<TestEntity> getEntities() {
//        return entities;
//    }
//
//    @Override
//    public String toString() {
//        return null;
//    }
//}
