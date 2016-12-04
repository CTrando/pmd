package com.mygdx.pmd.utils.observers;

/**
 * Created by Cameron on 12/4/2016.
 */
public interface Observable {
    void registerObservers();
    void notifyObservers();
}
