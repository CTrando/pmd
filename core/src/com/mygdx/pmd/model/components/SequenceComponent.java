package com.mygdx.pmd.model.components;


import com.badlogic.ashley.core.Entity;

import java.util.function.Consumer;

public class SequenceComponent extends Component {
    private boolean fStarted;
    private int fIndex;
    private Component[] fComponents;

    public SequenceComponent(Consumer<Component> onRemove, Component... components) {
        super(onRemove);
        fComponents = components;
    }

    public SequenceComponent(Component... components) {
        this(c -> {}, components);
    }

    public Component[] getComponents() {
        return fComponents;
    }

    public Component getCurrentComponent() {
        return fComponents[fIndex];
    }

    public boolean nextComponent() {
        return ++fIndex < fComponents.length;
    }

    public void start() {
        fStarted = true;
    }

    public boolean hasStarted() {
        return fStarted;
    }
}
