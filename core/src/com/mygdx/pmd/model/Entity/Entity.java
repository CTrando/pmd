package com.mygdx.pmd.model.Entity;

import com.badlogic.ashley.core.Component;

public class Entity extends com.badlogic.ashley.core.Entity {

    @Override
    public Component remove(Class<? extends Component> componentClass) {
        Component removed = super.remove(componentClass);
        if (removed instanceof com.mygdx.pmd.model.components.Component) {
            ((com.mygdx.pmd.model.components.Component) removed).remove();
        }
        return removed;
    }
}
