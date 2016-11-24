package com.mygdx.pmd.Exceptions;

import com.mygdx.pmd.utils.AI.PathFind;

/**
 * Created by Cameron on 11/23/2016.
 */
public class PathFindFailureException extends Exception {

    public PathFindFailureException(String message){
        super(message);
    }
}
