import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.pmd.model.Floor;
import com.mygdx.pmd.model.components.*;
import com.mygdx.pmd.model.entity.Entity;
import com.mygdx.pmd.system.MovementSystem;
import com.mygdx.pmd.system.TurnSystem;
import com.mygdx.pmd.system.input.PokemonInputSystem;
import com.mygdx.pmd.utils.Mappers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

public class InputSystemTests {

    private Engine fEngine;
    private Entity fEntity;

    @Before
    public void setup() {
        Floor floor = new Floor();
        fEngine = new Engine();
        fEngine.addSystem(new MovementSystem());
        fEngine.addSystem(new PokemonInputSystem(floor));
        fEngine.addSystem(new TurnSystem());

        fEntity = new Entity();
        fEntity.add(new PositionComponent(new Vector2(0, 0)));
        fEntity.add(new DirectionComponent());
        fEntity.add(new NameComponent("treeko"));
        fEntity.add(new TurnComponent());
        fEngine.addEntity(fEntity);
    }

    @Test
    public void testInputLockAddedDuringMove() {
        fEntity.add(new InputComponent(Collections.singletonList(Input.Keys.RIGHT)));

        for (int i = 0; i < 10; i++) {
            fEngine.update(.16f);
            Assert.assertNotNull(Mappers.InputLock.get(fEntity));
        }

        for (int i = 0; i < 40; i++) {
            fEngine.update(.16f);
        }

        Assert.assertNull(Mappers.InputLock.get(fEntity));
    }

    @Test
    public void testInputLock() {
        fEntity.add(new InputComponent(Collections.singletonList(Input.Keys.RIGHT)));
        fEngine.update(.16f);

        MoveComponent mc = Mappers.Movement.get(fEntity);

        for (int i = 0; i < 30; i++) {
            fEntity.add(new InputComponent(Collections.singletonList(Input.Keys.UP)));
            fEngine.update(.16f);
            MoveComponent newMc = Mappers.Movement.get(fEntity);
            Assert.assertEquals(mc, newMc);
        }
    }
}
