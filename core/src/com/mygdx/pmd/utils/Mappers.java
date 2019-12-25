package com.mygdx.pmd.utils;

import com.badlogic.ashley.core.ComponentMapper;
import com.mygdx.pmd.model.components.MoveComponent;
import com.mygdx.pmd.model.components.PositionComponent;
import com.mygdx.pmd.model.components.RenderComponent;

public class Mappers {
    public static ComponentMapper<RenderComponent> Render = ComponentMapper.getFor(RenderComponent.class);
    public static ComponentMapper<PositionComponent> Position = ComponentMapper.getFor(PositionComponent.class);
    public static ComponentMapper<MoveComponent> Movement = ComponentMapper.getFor(MoveComponent.class);
}
