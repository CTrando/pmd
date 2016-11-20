package com.mygdx.pmd.utils;

/**
 * Created by Cameron on 11/19/2016.
 */

public class MathLogic {

    public static double calculateDistance(int x1, int y1, int x2, int y2){
        int xOffset = x2-x1;
        int yOffset = y2-y1;

        return Math.sqrt((xOffset*xOffset) + (yOffset*yOffset));
    }
}
