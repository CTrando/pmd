package com.mygdx.pmd.Model.TileType;


import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.pmd.Model.FloorComponent.Floor;
import com.mygdx.pmd.Screen.DungeonScreen;

/**
 * Created by Cameron on 7/4/2016.
 */
public abstract class PathTile extends Tile {

    private Sprite sprite = DungeonScreen.sprites.get("vpathtilesprite");
    private Tile[][] tileBoard;

    private int windowRows;
    private int windowCols;

    public PathTile(Floor floor, int row, int col)
    {
        super(floor, row,col);
        this.setWalkable(true);
        this.setSprite(sprite);
        super.row = row;
        super.col = col;
        this.tileBoard = this.getTileBoard();
        windowRows = this.getWindowRows();
        windowCols = this.getWindowCols();
    }

    @Override
    public abstract boolean isLegal();

    public int getNumRoomTilesAroundIgnoreSameRow(int row, int col)
    {
        int count = 0;
        for (int r = -1; r < 2; r++) {
            for (int c = -1; c < 2; c++) {
                if (tileExists(tileBoard, row + r, col + c)) {
                        if(row != row+r) {
                            if (tileBoard[row + r][col + c] instanceof RoomTile) {
                                count++;
                            }
                        }
                }
            }
        }
        return count;
    }

    public int getNumRoomTilesAroundIgnoreSameCol(int row, int col)
    {
        int count = 0;
        for (int r = -1; r < 2; r++) {
            for (int c = -1; c < 2; c++) {
                if (tileExists(tileBoard, row + r, col + c)) {
                    if(col != col+c) {
                        if (tileBoard[row + r][col + c] instanceof RoomTile) {
                            count++;
                        }
                    }
                }
            }
        }
        return count;
    }

       /* int row = this.row;
        int col = this.col;

        int counter = 0;

        if(tileExists(tileBoard, row-1, col-1))
        {
            if((tileBoard[row-1][col-1] instanceof RoomTile))
            {
                counter++;
            }
        }

        if(tileExists(tileBoard, row-1, col+1))
        {
            if((tileBoard[row-1][col+1] instanceof RoomTile))
            {
                counter++;
            }
        }

        if(tileExists(tileBoard, row+1, col+1))
        {
            if((tileBoard[row+1][col+1] instanceof RoomTile))
            {
                counter++;
            }
        }

        if(tileExists(tileBoard, row+1, col-1))
        {
            if((tileBoard[row+1][col-1] instanceof RoomTile))
            {
                counter++;
            }
        }

       return counter>0? false: true;*/


}
