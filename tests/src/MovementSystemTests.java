import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.pmd.enums.Direction;
import com.mygdx.pmd.model.entity.Entity;
import com.mygdx.pmd.model.Floor;
import com.mygdx.pmd.model.components.*;
import com.mygdx.pmd.model.entity.pokemon.PokemonPlayer;
import com.mygdx.pmd.system.InputSystem;
import com.mygdx.pmd.system.MobSystem;
import com.mygdx.pmd.system.MovementSystem;
import com.mygdx.pmd.system.TurnSystem;
import com.mygdx.pmd.system.input.PlayerInputSystem;
import com.mygdx.pmd.system.input.PokemonInputSystem;
import com.mygdx.pmd.utils.KeyInput;
import com.mygdx.pmd.utils.Mappers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doReturn;

public class MovementSystemTests {

    private Engine fEngine;
    private Entity fEntity;

    @Before
    public void setup() {
        fEngine = new Engine();

        fEntity = new Entity();
        fEntity.add(new PositionComponent(new Vector2(0, 0)));
        fEntity.add(new NameComponent("treeko"));
        fEntity.add(new TurnComponent());
        fEngine.addEntity(fEntity);

        fEngine.addSystem(new MovementSystem());
        fEngine.addSystem(new TurnSystem());
    }

    @Test
    public void solidAnimationOnContinuousRightInput() {
        Floor floor = new Floor();
        KeyInput mockInput = Mockito.mock(KeyInput.class);
        doReturn(false).when(mockInput).pressed(anyInt());
        doReturn(true).when(mockInput).pressed(Input.Keys.RIGHT);

        fEngine.addSystem(new InputSystem(mockInput));
        fEngine.addSystem(new PlayerInputSystem());
        fEngine.addSystem(new PokemonInputSystem(floor));

        fEntity.add(new InputControlledComponent());
        fEntity.add(new DirectionComponent(Direction.right));

        for (int i = 0; i < 120; i++) {
            fEngine.update(.16f);

            AnimationComponent ac = Mappers.Animation.get(fEntity);
            Assert.assertNotNull(ac);
            Assert.assertEquals("walkRight", ac.getAnimationName());
        }
    }

    @Test
    public void solidAnimationOnContinuousRightInputMob() {
        Floor floor = new Floor();

        fEngine.addSystem(new MobSystem());
        fEngine.addSystem(new PokemonInputSystem(floor));

        fEntity.add(new MobComponent());
        fEntity.add(new DirectionComponent(Direction.right));

        for (int i = 0; i < 120; i++) {
            fEngine.update(.16f);

            AnimationComponent ac = Mappers.Animation.get(fEntity);
            Assert.assertNotNull(ac);
            Assert.assertEquals("walkRight", ac.getAnimationName());
        }
    }

    @Test
    public void moveIsSeamless() {
        Floor floor = new Floor();
        KeyInput mockInput = Mockito.mock(KeyInput.class);
        doReturn(false).when(mockInput).pressed(anyInt());
        doReturn(true).when(mockInput).pressed(Input.Keys.RIGHT);

        fEngine.addSystem(new InputSystem(mockInput));
        fEngine.addSystem(new PlayerInputSystem());
        fEngine.addSystem(new PokemonInputSystem(floor));

        fEntity.add(new InputControlledComponent());
        fEntity.add(new DirectionComponent(Direction.right));

        Vector2 prev = new Vector2(Mappers.Position.get(fEntity).getPos());

        // Just to get the movement component on
        fEngine.update(.16f);
        for (int i = 0; i < 64; i++) {
            fEngine.update(.16f);

            Vector2 cur = new Vector2(Mappers.Position.get(fEntity).getPos());
            float dist = prev.dst(cur);
            Assert.assertTrue(String.format("Distance between prev: %s and cur %s was %s", prev, cur, dist), dist > 0);
            prev = cur;
        }
    }

    @Test
    public void updatePositionOnMove() {
        PositionComponent pc = Mappers.Position.get(fEntity);
        Vector2 beforePos = new Vector2(pc.getPos());
        fEntity.add(new MoveComponent(Direction.right, pc.getPos()));

        for (int i = 0; i < 120; i++) {
            fEngine.update(.16f);
        }

        Vector2 expected = beforePos.add(1, 0);
        Assert.assertEquals(expected, pc.getPos());
    }
}
