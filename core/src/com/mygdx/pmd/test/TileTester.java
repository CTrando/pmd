package com.mygdx.pmd.test;

import com.mygdx.pmd.model.Tile.*;

/**
 * Created by Cameron on 1/29/2017.
 */
public class TileTester {

    public static boolean checkCorners(Tile[][] tileBoard){
        for(int i = 0; i< tileBoard.length; i++){
            for(int j = 0; j< tileBoard[0].length; j++){
                if(i == 0 || i == tileBoard.length-1 || j == 0 || j == tileBoard[0].length-1) {
                    if (tileBoard[i][j].isWalkable) return false;
                }
            }
        }
        return true;
    }
}
