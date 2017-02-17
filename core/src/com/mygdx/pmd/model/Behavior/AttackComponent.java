package com.mygdx.pmd.model.Behavior;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Array;
import com.mygdx.pmd.enumerations.*;

/**
 * Created by Cameron on 2/17/2017.
 */
public class AttackComponent implements Component {
    public Array<Move> moves;
    public Move currentMove;
}
