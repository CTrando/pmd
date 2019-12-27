package com.mygdx.pmd.model.components;

import java.util.function.Consumer;

public class Component implements com.badlogic.ashley.core.Component {
    private boolean fRemoved;
    private Consumer<Component> fOnRemove;

    public Component() {
        fOnRemove = c -> {};
    }

    public Component(Consumer<Component> onRemove) {
        fOnRemove = onRemove;
    }

    public void remove() {
        fRemoved = true;
        fOnRemove.accept(this);
    }

    public boolean isRemoved() {
        return fRemoved;
    }
}
