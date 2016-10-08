package com.mygdx.pmd.Model.TileType;


import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.pmd.Model.FloorComponent.Floor;
import com.mygdx.pmd.Model.FloorComponent.Room;
import com.mygdx.pmd.Screen.DungeonScreen;

import java.awt.*;

/**
 * Created by Cameron on 6/25/2016.
 */
public class RoomTile extends Tile {

    private String classifier;

    private Color color;

    private Sprite sprite = DungeonScreen.sprites.get("roomtilesprite");

    private Tile[][] tileBoard;

    private int windowRows;

    private int windowCols;

    private Room currentRoom;


    public RoomTile(Floor floor, int r, int c, String classifier, Color color, Room room)
    {
        super(floor, r,c);

        this.classifier = classifier;
        this.setClassifier(classifier);

        this.color = color;
        this.setColor(color);
        this.setWalkable(true);
        this.setSprite(sprite);

        this.tileBoard = floor.getTileBoard();
        this.windowRows = this.getWindowRows();
        this.windowCols = this.getWindowCols();

        this.currentRoom = room;
    }

    public boolean belongsTo(Room other)
    {
        if(other.getRoomConstraints().contains(this))
        {
            return true;
        }
        else return false;
    }

    public boolean isLegal()
    {
        int counter = 0;
        int row = this.getRow();
        int col = this.getCol();

        if(tileExists(tileBoard, row-1, col))
        {
            if((tileBoard[row-1][col] instanceof PathTile))
            {
                counter++;
            }
        }

        if(tileExists(tileBoard, row, col-1))
        {
            if((tileBoard[row][col-1] instanceof PathTile))
            {
                counter++;
            }
        }

        if(tileExists(tileBoard, row, col+1))
        {
            if((tileBoard[row][col+1] instanceof PathTile))
            {
                counter++;
            }
        }

        if(tileExists(tileBoard, row+1, col))
        {
            if((tileBoard[row+1][col] instanceof PathTile))
            {
                counter++;
            }
        }

        if(counter == 4)
            return false;
        else return true;
    }

    public String getClassifier()
    {
        return classifier;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }
}
