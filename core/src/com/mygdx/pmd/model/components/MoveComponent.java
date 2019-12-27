package com.mygdx.pmd.model.components;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.pmd.enums.Direction;
import com.mygdx.pmd.utils.Constants;

import java.util.function.Consumer;


/**
 * Created by Cameron on 4/16/2017.
 */
public class MoveComponent extends Component {

    private Vector2 fDest;
    private Direction fDirection;

    public MoveComponent(Direction direction, Vector2 curPos) {
        this(direction, curPos, c -> {});
    }

    public MoveComponent(Direction direction, Vector2 curPos, Consumer<Component> onRemove) {
        super(onRemove);

        fDirection = direction;
        fDest = new Vector2(curPos);

        switch (fDirection) {
            case up:
                fDest.add(0, Constants.TILE_SIZE);
                break;
            case down:
                fDest.add(0, -Constants.TILE_SIZE);
                break;
            case left:
                fDest.add(-Constants.TILE_SIZE, 0);
                break;
            case right:
                fDest.add(Constants.TILE_SIZE, 0);
                break;
            default:
                break;
        }
    }

    public Direction getDirection() {
        return fDirection;
    }

    public Vector2 getDest() {
        return fDest;
    }
}
