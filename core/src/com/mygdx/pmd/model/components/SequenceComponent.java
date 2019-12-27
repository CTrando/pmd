package com.mygdx.pmd.model.components;


public class SequenceComponent extends Component {
    private boolean fStarted;
    private int fIndex;
    private Component[] fComponents;

    public SequenceComponent(Component... components) {
        fComponents = components;
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
