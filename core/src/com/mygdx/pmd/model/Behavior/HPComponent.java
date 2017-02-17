package com.mygdx.pmd.model.Behavior;

import com.mygdx.pmd.model.Entity.*;

/**
 * Created by Cameron on 2/16/2017.
 */
public class HPComponent extends Component{
    Entity entity;
    public HPComponent(Entity entity){
        super(entity);
    }

    @Override
    public void update() {

    }

    public int getHp() {
        return entity.hp;
    }

    public void setHp(int hp) {
        entity.hp = hp;
        if (entity.hp <= 0) {
            entity.hp = 0;
        }

        if(entity.hp > 100){
            entity.hp = 100;
        }
    }
}
