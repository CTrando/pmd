package com.mygdx.pmd.model.Decorators;

import com.mygdx.pmd.PMD;
import com.mygdx.pmd.model.Factory.FloorFactory;
import com.mygdx.pmd.model.Factory.ItemFactory;
import com.mygdx.pmd.model.Tile.RoomTile;
import com.mygdx.pmd.model.Tile.Tile;

/**
 * Created by Cameron on 12/23/2016.
 */
public class FloorDecorator {
    
    public static Tile[][] placeItems(Tile[][] tileBoard){
        ItemFactory.placeItems(tileBoard);
        return tileBoard;
    }

    public static Tile[][] skinTiles(Tile[][] tileBoard){
        //check neighbors and set spriteValue
        for(int i = 0; i< tileBoard.length; i++){
            for(int j = 0; j< tileBoard[0].length; j++){
                Tile tile = tileBoard[i][j];

                if(FloorDecorator.isWithinBounds(i+1,j, tileBoard) && tileBoard[i+1][j] instanceof RoomTile) tileBoard[i][j].spriteValue+=1;
                if(FloorDecorator.isWithinBounds(i+1, j+1, tileBoard) && tileBoard[i+1][j+1] instanceof RoomTile) tileBoard[i][j].spriteValue+=2;
                if(FloorDecorator.isWithinBounds(i,j+1, tileBoard) && tileBoard[i][j+1] instanceof RoomTile) tileBoard[i][j].spriteValue+=4;
                if(FloorDecorator.isWithinBounds(i-1, j+1, tileBoard) && tileBoard[i-1][j+1] instanceof RoomTile) tileBoard[i][j].spriteValue+=8;
                if(FloorDecorator.isWithinBounds(i-1,j, tileBoard) && tileBoard[i-1][j] instanceof RoomTile) tileBoard[i][j].spriteValue+=16;
                if(FloorDecorator.isWithinBounds(i-1, j-1, tileBoard) && tileBoard[i-1][j-1] instanceof RoomTile) tileBoard[i][j].spriteValue+=32;
                if(FloorDecorator.isWithinBounds(i,j-1, tileBoard) && tileBoard[i][j-1] instanceof RoomTile) tileBoard[i][j].spriteValue+=64;
                if(FloorDecorator.isWithinBounds(i+1, j-1, tileBoard) && tileBoard[i+1][j-1] instanceof RoomTile) tileBoard[i][j].spriteValue+=128;

                if (tile.spriteValue == 0)
                    tile.sprite = PMD.sprites.get("blacktilesprite");
                else if (tile.spriteValue == 112)
                    tile.sprite = PMD.sprites.get("toprighttilesprite");
                else if (tile.spriteValue == 193)
                    tile.sprite = PMD.sprites.get("bottomrightcornertilesprite");
                else if (tile.spriteValue == 28)
                    tile.sprite = PMD.sprites.get("toplefttilesprite");
                else if (tile.spriteValue == 7)
                    tile.sprite = PMD.sprites.get("bottomlefttilesprite");
            }
        }
        return tileBoard;
    }


    public static boolean isWithinBounds(int row, int col, Tile[][] tileBoard){
        if(row >= tileBoard.length || row < 0) return false;
        if(col >= tileBoard[0].length || col < 0) return false;
        return true;
    }

}
