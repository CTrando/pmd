package com.mygdx.pmd.Model.TileType;


import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.pmd.Model.FloorComponent.Floor;
import com.mygdx.pmd.Screen.DungeonScreen;

/**
 * Created by Cameron on 8/15/2016.
 */
public class StairTile extends Tile{

    Floor floor;
    private Sprite sprite = DungeonScreen.sprites.get("stairtilesprite");


    public StairTile(Floor floor, int r, int c)
    {
        super(floor, r, c);
        this.floor = floor;

        this.setWalkable(true);
        this.setSprite(sprite);
    }


    @Override
    public boolean isLegal() {
        return false;
    }

    @Override
    public void playEvents()
    {
        floor.getFloorGenerator().generateFloor();
        floor.getFloorGenerator().controller.randomizeAllPokemonLocation();
    }
}
