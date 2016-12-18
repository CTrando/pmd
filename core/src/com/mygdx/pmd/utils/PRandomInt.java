package com.mygdx.pmd.utils;

/**
 * Created by Cameron on 12/11/2016.
 */
public class PRandomInt {

    public static int random(int floor, int ceiling){
        return (int)(Math.random()*(ceiling+1)) + floor;
    }
}
