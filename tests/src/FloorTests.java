import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.pmd.model.Floor;
import com.mygdx.pmd.model.components.NameComponent;
import com.mygdx.pmd.model.components.PositionComponent;
import com.mygdx.pmd.model.entity.tile.Tile;
import com.mygdx.pmd.utils.Mappers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

public class FloorTests {

    private Engine fEngine;
    private Entity fEntity;
    private Floor fFloor;

    @Before
    public void setup() {
        fEngine = new Engine();
        fFloor = new Floor();

        fFloor.addToEngine(fEngine);
        fEngine.addEntityListener(fFloor);

        fEntity = new Entity();
        fEntity.add(new PositionComponent(new Vector2(0, 0)));
        fEntity.add(new NameComponent("treeko"));
        fEngine.addEntity(fEntity);
    }

    @Test
    public void testEntityCreationAddedToTile() {
        PositionComponent pc = Mappers.Position.get(fEntity);
        Tile tile = fFloor.getTile(pc.getPos());
        Assert.assertNotNull(tile);
        Assert.assertEquals(Collections.singleton(fEntity), tile.getEntities());
    }
}
