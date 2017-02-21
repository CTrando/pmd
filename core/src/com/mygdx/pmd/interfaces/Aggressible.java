package com.mygdx.pmd.interfaces;

import com.mygdx.pmd.enumerations.*;

/**
 * Created by Cameron on 2/17/2017.
 */
public interface Aggressible {

    Aggression getAggression();
    boolean isAggressive();

    void setAggression(Aggression aggression);
    boolean canSeeEnemy();
}
