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
    private int fSpeed;

    public MoveComponent(Direction direction, Vector2 curPos) {
        this(direction, curPos, c -> {
        });
    }

    public MoveComponent(Direction direction, Vector2 curPos, Consumer<Component> onRemove) {
        this(direction, curPos, 1, onRemove);
    }

    public MoveComponent(Direction direction, Vector2 curPos, int speed, Consumer<Component> onRemove) {
        super(onRemove);
        fDirection = direction;
        fDest = new Vector2(curPos);
        fSpeed = speed;

        Vector2 x = new Vector2(1, 0);
        Vector2 y = new Vector2(0, 1);

        switch (fDirection) {
            case up:
                fDest.add(y);
                break;
            case down:
                fDest.sub(y);
                break;
            case left:
                fDest.sub(x);
                break;
            case right:
                fDest.add(x);
                break;
            default:
                break;
        }
    }

    public MoveComponent(Vector2 dest) {
        this(Direction.none, dest, c -> {
        });
    }

    public Direction getDirection() {
        return fDirection;
    }

    public Vector2 getDest() {
        return fDest;
    }

    public int getSpeed() {
        return fSpeed;
    }
}
