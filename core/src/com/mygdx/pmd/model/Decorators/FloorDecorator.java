package com.mygdx.pmd.model.Decorators;

import com.mygdx.pmd.PMD;
import com.mygdx.pmd.model.Entity.Item.ItemFactory;
import com.mygdx.pmd.model.Floor.Floor;
import com.mygdx.pmd.model.Tile.*;

/**
 * Created by Cameron on 12/23/2016.
 */
public class FloorDecorator {

    public static Floor placeItems(Floor floor) {
        ItemFactory.placeItems(floor);
        return floor;
    }

    public static Floor skinTiles(Floor floor) {
        //check neighbors and set spriteValue
        for (int i = 0; i < floor.tileBoard.length; i++) {
            for (int j = 0; j < floor.tileBoard[0].length; j++) {
                Tile tile = floor.tileBoard[i][j];

                if (FloorDecorator.isWithinBounds(i + 1, j,
                                                  floor.tileBoard) && floor.tileBoard[i + 1][j] instanceof RoomTile) {
                    floor.tileBoard[i][j].spriteValue += 1;
                }
                if (FloorDecorator.isWithinBounds(i + 1, j + 1,
                                                  floor.tileBoard) && floor.tileBoard[i + 1][j + 1] instanceof RoomTile) {
                    floor.tileBoard[i][j].spriteValue += 2;
                }
                if (FloorDecorator.isWithinBounds(i, j + 1,
                                                  floor.tileBoard) && floor.tileBoard[i][j + 1] instanceof RoomTile) {
                    floor.tileBoard[i][j].spriteValue += 4;
                }
                if (FloorDecorator.isWithinBounds(i - 1, j + 1,
                                                  floor.tileBoard) && floor.tileBoard[i - 1][j + 1] instanceof RoomTile) {
                    floor.tileBoard[i][j].spriteValue += 8;
                }
                if (FloorDecorator.isWithinBounds(i - 1, j,
                                                  floor.tileBoard) && floor.tileBoard[i - 1][j] instanceof RoomTile) {
                    floor.tileBoard[i][j].spriteValue += 16;
                }
                if (FloorDecorator.isWithinBounds(i - 1, j - 1,
                                                  floor.tileBoard) && floor.tileBoard[i - 1][j - 1] instanceof RoomTile) {
                    floor.tileBoard[i][j].spriteValue += 32;
                }
                if (FloorDecorator.isWithinBounds(i, j - 1,
                                                  floor.tileBoard) && floor.tileBoard[i][j - 1] instanceof RoomTile) {
                    floor.tileBoard[i][j].spriteValue += 64;
                }
                if (FloorDecorator.isWithinBounds(i + 1, j - 1,
                                                  floor.tileBoard) && floor.tileBoard[i + 1][j - 1] instanceof RoomTile) {
                    floor.tileBoard[i][j].spriteValue += 128;
                }
            }
        }
        return floor;
    }


    public static boolean isWithinBounds(int row, int col, Tile[][] tileBoard) {
        if (row >= tileBoard.length || row < 0) {
            return false;
        }
        return !(col >= tileBoard[0].length || col < 0);
    }

    public static Floor placeEventTiles(Floor floor) {
        Tile tile = floor.chooseUnoccupiedTile();
        while (tile.spriteValue <= 250 /*|| tile.hasEntityOfType(Movable.class)*/) {
            tile = floor.chooseUnoccupiedTile();
        }
        floor.tileBoard[tile.row][tile.col] = new StairTile(tile.row, tile.col, floor);

        for (int i = 0; i < 4*Math.random(); i++) {
            tile = floor.chooseUnoccupiedTile();
            floor.tileBoard[tile.row][tile.col] = new FireTile(tile.row, tile.col, floor);
        }

        //trying to place water tiles here
      /*  Tile waterCenter = floor.chooseRandomTile();
        floor.tileBoard[waterCenter.row][waterCenter.col] = new WaterTile(waterCenter.row, waterCenter.col, floor);
        floor.tileBoard[waterCenter.row + 1][waterCenter.col] = new WaterTile(waterCenter.row + 1, waterCenter.col, floor);
        floor.tileBoard[waterCenter.row + 1][waterCenter.col + 1] = new WaterTile(waterCenter.row + 1, waterCenter.col + 1, floor);
        floor.tileBoard[waterCenter.row][waterCenter.col + 1] = new WaterTile(waterCenter.row, waterCenter.col + 1, floor);*/

        return floor;
    }

}
