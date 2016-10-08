package com.mygdx.pmd.Model.TileType;


import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.pmd.Model.FloorComponent.Floor;
import com.mygdx.pmd.Screen.DungeonScreen;

/**
 * Created by Cameron on 7/15/2016.
 */
public class PathTileVertical extends PathTile {

    private Sprite sprite = DungeonScreen.sprites.get("vpathtilesprite");

    private Tile[][] tileBoard;

    private int row;
    private int col;

    public PathTileVertical(Floor floor, int row, int col) {
        super(floor, row, col);
        this.setSprite(sprite);
        this.tileBoard = floor.getTileBoard();

        this.row = row;
        this.col = col;
    }

    @Override
    public boolean isLegal() {
        Tile currentTile = this;
        try {
            if ((tileBoard[row][col - 1] instanceof PathTileVertical)) {
                return false;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        }
      /*  int count = this.getNumRoomTilesAroundIgnoreSameCol(row, col);

        if (count >= 3)
            return false;
*/
        try {
            if ((tileBoard[row][col + 1] instanceof PathTileVertical)) {
                return false;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        }

        return true;
    }
}
