package com.mygdx.pmd.model.components;

import com.badlogic.ashley.core.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class InputComponent implements Component {

    private Set<Integer> fKeysPressed;

    public InputComponent(int key) {
        this(Collections.singletonList(key));
    }

    public InputComponent(Collection<Integer> keysPressed) {
        fKeysPressed = new HashSet<>(keysPressed);
    }

    public Set<Integer> getKeysPressed() {
        return fKeysPressed;
    }

    public boolean pressed(int key) {
        return fKeysPressed.contains(key);
    }
}
