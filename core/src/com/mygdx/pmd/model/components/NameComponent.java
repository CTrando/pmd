package com.mygdx.pmd.model.components;

import com.badlogic.ashley.core.Component;

public class NameComponent implements Component {
    private String fName;

    public NameComponent(String name) {
        fName = name;
    }

    public String getName() {
        return fName;
    }
}
