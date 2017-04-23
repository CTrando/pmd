package com.mygdx.pmd.model.Decorators;

import com.mygdx.pmd.PMD;
import com.mygdx.pmd.model.Entity.Item.ItemFactory;
import com.mygdx.pmd.model.Floor.Floor;
import com.mygdx.pmd.model.Tile.GenericTile;
import com.mygdx.pmd.model.Tile.RoomTile;
import com.mygdx.pmd.model.Tile.StairTile;
import com.mygdx.pmd.model.Tile.Tile;

/**
 * Created by Cameron on 12/23/2016.
 */
public class FloorDecorator {
    
    public static Floor placeItems(Floor floor){
        ItemFactory.placeItems(floor);
        return floor;
    }

    public static Floor skinTiles(Floor floor){
        //check neighbors and set spriteValue
        for(int i = 0; i< floor.tileBoard.length; i++){
            for(int j = 0; j< floor.tileBoard[0].length; j++) {
                Tile tile = floor.tileBoard[i][j];

                if (FloorDecorator.isWithinBounds(i + 1, j, floor.tileBoard) && floor.tileBoard[i + 1][j] instanceof RoomTile)
                    floor.tileBoard[i][j].spriteValue += 1;
                if (FloorDecorator.isWithinBounds(i + 1, j + 1, floor.tileBoard) && floor.tileBoard[i + 1][j + 1] instanceof RoomTile)
                    floor.tileBoard[i][j].spriteValue += 2;
                if (FloorDecorator.isWithinBounds(i, j + 1, floor.tileBoard) && floor.tileBoard[i][j + 1] instanceof RoomTile)
                    floor.tileBoard[i][j].spriteValue += 4;
                if (FloorDecorator.isWithinBounds(i - 1, j + 1, floor.tileBoard) && floor.tileBoard[i - 1][j + 1] instanceof RoomTile)
                    floor.tileBoard[i][j].spriteValue += 8;
                if (FloorDecorator.isWithinBounds(i - 1, j, floor.tileBoard) && floor.tileBoard[i - 1][j] instanceof RoomTile)
                    floor.tileBoard[i][j].spriteValue += 16;
                if (FloorDecorator.isWithinBounds(i - 1, j - 1, floor.tileBoard) && floor.tileBoard[i - 1][j - 1] instanceof RoomTile)
                    floor.tileBoard[i][j].spriteValue += 32;
                if (FloorDecorator.isWithinBounds(i, j - 1, floor.tileBoard) && floor.tileBoard[i][j - 1] instanceof RoomTile)
                    floor.tileBoard[i][j].spriteValue += 64;
                if (FloorDecorator.isWithinBounds(i + 1, j - 1, floor.tileBoard) && floor.tileBoard[i + 1][j - 1] instanceof RoomTile)
                    floor.tileBoard[i][j].spriteValue += 128;

                if (tile instanceof GenericTile) {
                    tile.sprite = null;/*
                    switch (tile.spriteValue) {
                        case 0:
                            tile.sprite = PMD.sprites.get("blacktilesprite");
                            break;
                        case 112:
                            tile.sprite = PMD.sprites.get("toprighttilesprite");
                            break;
                        case 193:
                            tile.sprite = PMD.sprites.get("bottomrightcornertilesprite");
                            break;
                        case 28:
                            tile.sprite = PMD.sprites.get("toplefttilesprite");
                            break;
                        case 7:
                            tile.sprite = PMD.sprites.get("bottomlefttilesprite");
                            break;
                        case 224:
                            tile.sprite = PMD.sprites.get("rightbarriersprite");
                            break;
                        case 14:
                            tile.sprite = PMD.sprites.get("leftbarriersprite");
                            break;
                        case 56:
                            tile.sprite = PMD.sprites.get("upbarriersprite");
                            break;
                        case 131:
                            tile.sprite = PMD.sprites.get("downbarriersprite");
                            break;
                        case 238:
                            tile.sprite = PMD.sprites.get("leftrightbarriersprite");
                            break;
                        case 187:
                            tile.sprite = PMD.sprites.get("updownbarriersprite");
                            break;
                        case 62:
                            tile.sprite = PMD.sprites.get("topleftbarriersprite");
                            break;
                        case 143:
                            tile.sprite = PMD.sprites.get("bottomleftbarriersprite");
                            break;
                        case 227:
                            tile.sprite = PMD.sprites.get("bottomrightbarriersprite");
                            break;
                        case 248:
                            tile.sprite = PMD.sprites.get("toprightbarriersprite");
                    }
*/                }
            }
        }
        return floor;
    }


    public static boolean isWithinBounds(int row, int col, Tile[][] tileBoard){
        if(row >= tileBoard.length || row < 0) return false;
        return !(col >= tileBoard[0].length || col < 0);
    }

    public static Floor placeEventTiles(Floor floor){
        Tile tile = floor.chooseUnoccupiedTile();
        while(tile.spriteValue <= 250 /*|| tile.hasEntityOfType(Movable.class)*/) {
            tile = floor.chooseUnoccupiedTile();
        }
        floor.tileBoard[tile.row][tile.col] = new StairTile(tile.row, tile.col, floor);
        return floor;
    }

}
