package com.mygdx.pmd.model.components;

import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.model.Entity.*;

/**
 * Created by Cameron on 4/18/2017.
 */
public class CombatComponent implements Component {
    private Aggression aggressionState;
    private int hp;

    private Entity entity;

    public CombatComponent(Entity entity){
        this.entity = entity;

        this.hp = 100;
        this.aggressionState = Aggression.passive;
    }

    public boolean isAggressive() {
        return this.aggressionState == Aggression.aggressive;
    }

    public boolean isFriendly(){
        return this.aggressionState == Aggression.friendly;
    }

    public Aggression getAggressionState() {
        return aggressionState;
    }

    public void setAggressionState(Aggression aggressionState) {
        this.aggressionState = aggressionState;
    }

    public void takeDamage(Entity damager, int damage){
        setHp(hp - damage);
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
        if (this.hp <= 0) {
            this.hp = 0;
        }
    }
}
