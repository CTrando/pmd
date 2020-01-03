package com.mygdx.pmd.utils;

import com.badlogic.gdx.utils.Array;

/**
 * Created by Cameron on 2/20/2017.
 */
public class PUtils {

    public static <T> Array<T> getObjectsOfType(Class<T> type, Array list){
        Array retArray = new Array<T>();
        for(Object o: list){
            if(type.isInstance(o)){
                retArray.add(type.cast(o));
            }
        }
        return retArray;
    }
}
