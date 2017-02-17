package com.mygdx.pmd.model.Behavior;

import com.badlogic.ashley.core.Component;
import com.mygdx.pmd.enumerations.*;

/**
 * Created by Cameron on 2/17/2017.
 */
public class DirectionComponent implements Component {
    public Direction direction;

    public DirectionComponent(){
        direction = Direction.down;
    }

}
