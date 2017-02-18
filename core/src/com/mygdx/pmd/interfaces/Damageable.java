package com.mygdx.pmd.interfaces;

import com.mygdx.pmd.model.Entity.Pokemon.*;

/**
 * Created by Cameron on 12/22/2016.
 */
public interface Damageable {
    void takeDamage(Pokemon parent, int damage);

    public int getHP();
    public void setHP(int HP);
}
