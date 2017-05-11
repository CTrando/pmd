package com.mygdx.pmd.model.components;

import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.model.Entity.*;
import com.mygdx.pmd.model.instructions.*;

/**
 * Created by Cameron on 4/18/2017.
 */
public class CombatComponent implements Component {
    private Entity target;
    private Aggression aggressionState;
    private DirectionComponent dc;
    private int hp;

    private Entity entity;

    public CombatComponent(Entity entity){
        this.entity = entity;

        this.dc = entity.getComponent(DirectionComponent.class);
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
        setTarget(damager);
        setAggressionState(Aggression.aggressive);
        PositionComponent ePc = damager.getComponent(PositionComponent.class);
        dc.setDirection(ePc.getCurrentTile());
        entity.instructions.add(new AnimateInstruction(entity, "pain", dc.getDirection().toString()+"idle"));
    }

    public Entity getTarget() {
        return target;
    }

    public void setTarget(Entity target) {
        this.target = target;
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
