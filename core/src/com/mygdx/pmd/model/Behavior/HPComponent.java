package com.mygdx.pmd.model.Behavior;

import com.badlogic.ashley.core.Component;

/**
 * Created by Cameron on 2/17/2017.
 */
public class HPComponent implements Component {
    public int hp = 100;
    public boolean shouldBeDestroyed = false;

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
        if (hp <= 0) {
            this.hp = 0;
        }

        if(hp > 100){
            this.hp = 100;
        }
    }

}
