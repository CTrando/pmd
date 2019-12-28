import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.pmd.enums.Direction;
import com.mygdx.pmd.model.Entity.Entity;
import com.mygdx.pmd.model.Floor;
import com.mygdx.pmd.model.components.*;
import com.mygdx.pmd.system.InputSystem;
import com.mygdx.pmd.system.MovementSystem;
import com.mygdx.pmd.system.PlayerInputSystem;
import com.mygdx.pmd.utils.KeyInput;
import com.mygdx.pmd.utils.Mappers;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doReturn;

public class MovementSystemTests {

    @Test
    public void solidAnimationOnContinuousRightInput() {
        Floor floor = new Floor();
        Engine engine = new Engine();
        KeyInput mockInput = Mockito.mock(KeyInput.class);
        doReturn(false).when(mockInput).pressed(anyInt());
        doReturn(true).when(mockInput).pressed(Input.Keys.RIGHT);

        engine.addSystem(new InputSystem(mockInput));
        engine.addSystem(new MovementSystem());
        engine.addSystem(new PlayerInputSystem(floor));

        Entity entity = new Entity();
        entity.add(new InputControlledComponent());
        entity.add(new PositionComponent(new Vector2(0, 0)));
        entity.add(new DirectionComponent(Direction.right));
        entity.add(new NameComponent("treeko"));
        entity.add(new InputComponent(Collections.singletonList(Input.Keys.RIGHT)));
        engine.addEntity(entity);

        for(int i = 0; i < 120; i++) {
            engine.update(.16f);

            AnimationComponent ac = Mappers.Animation.get(entity);
            Assert.assertNotNull(ac);
            Assert.assertEquals("walkRight", ac.getAnimationName());
        }
    }
}
