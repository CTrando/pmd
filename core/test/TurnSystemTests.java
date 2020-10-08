import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.pmd.model.Floor;
import com.mygdx.pmd.model.components.*;
import com.mygdx.pmd.system.MovementSystem;
import com.mygdx.pmd.system.TurnSystem;
import com.mygdx.pmd.system.input.PokemonInputSystem;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TurnSystemTests {

    private Engine fEngine;
    private Entity fEntity;
    private Entity fMob;

    @Before
    public void setup() {
        Floor floor = new Floor();

        fEngine = new Engine();
        fEngine.addSystem(new PokemonInputSystem(floor));
        fEngine.addSystem(new MovementSystem());
        fEngine.addSystem(new TurnSystem());

        fEntity = new Entity();
        fEntity.add(new PositionComponent(new Vector2(0, 0)));
        fEntity.add(new DirectionComponent());
        fEntity.add(new NameComponent("treeko"));
        fEntity.add(new TurnComponent());
        fEngine.addEntity(fEntity);

        fMob = new Entity();
        fMob.add(new PositionComponent(new Vector2(1, 1)));
        fMob.add(new DirectionComponent());
        fMob.add(new NameComponent("treeko"));
        fMob.add(new TurnComponent());
        fEngine.addEntity(fMob);
    }

    @Test
    public void testTurnSwitchesOnMove() {
        // Will assign the turn component first
        fEngine.update(.16f);

        fEntity.add(new InputComponent(Input.Keys.RIGHT));
        fMob.add(new InputComponent(Input.Keys.RIGHT));

        fEngine.update(.16f);

        // After first update, first entity made its move and ended its turn
        TurnComponent tc = fEntity.getComponent(TurnComponent.class);
        InputLockComponent ilc = fEntity.getComponent(InputLockComponent.class);
        Assert.assertNotNull(ilc);
        Assert.assertTrue(tc.turnEnded());

        TurnComponent mobTc = fMob.getComponent(TurnComponent.class);
        InputLockComponent mobIlc = fMob.getComponent(InputLockComponent.class);
        Assert.assertNull(mobIlc);
        Assert.assertFalse(mobTc.turnEnded());

        fEngine.update(.16f);

        // After second update, second entity made its move now they are both moving
        tc = fEntity.getComponent(TurnComponent.class);
        ilc = fEntity.getComponent(InputLockComponent.class);
        Assert.assertNotNull(ilc);
        Assert.assertFalse(tc.turnEnded());

        mobTc = fMob.getComponent(TurnComponent.class);
        mobIlc = fMob.getComponent(InputLockComponent.class);
        Assert.assertNotNull(mobIlc);
        Assert.assertTrue(mobTc.turnEnded());
    }
}
