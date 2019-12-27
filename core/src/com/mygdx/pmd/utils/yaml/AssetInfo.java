package com.mygdx.pmd.utils.yaml;

import java.util.Map;

public class AssetInfo {
    private String fName;
    private Map<String, AnimationInfo> fAnimations;

    public Map<String, AnimationInfo> getAnimations() {
        return fAnimations;
    }

    public void setAnimations(Map<String, AnimationInfo> info) {
        fAnimations = info;
    }

    public String getName() {
        return fName;
    }

    public void setName(String name) {
        fName = name;
    }
}
