package com.mygdx.pmd.interfaces;

import com.mygdx.pmd.enumerations.*;

/**
 * Created by Cameron on 2/17/2017.
 */
public interface Aggressible {

    public Aggression getAggression();
    public void setAggression(Aggression aggression);
    public boolean canSeeEnemy();
}
