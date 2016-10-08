package com.mygdx.pmd.Model.TileType;


import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.pmd.Model.FloorComponent.Floor;
import com.mygdx.pmd.Model.FloorComponent.Room;
import com.mygdx.pmd.Screen.DungeonScreen;

import java.awt.*;

/**
 * Created by Cameron on 6/27/2016.
 */

public class DoorTile extends RoomTile {

    private Sprite sprite = DungeonScreen.sprites.get("doortilesprite");

    public DoorTile(Floor floor, int row, int col, Room room)
    {
        super(floor, row, col, "Door", Color.gray, room);
        this.setColor(Color.gray);
        this.setSprite(sprite);
    }

    public String getClassifier() {
        return "Door";
    }

    @Override
    public void playEvents()
    {

    }

}
