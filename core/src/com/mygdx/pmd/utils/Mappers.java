package com.mygdx.pmd.utils;

import com.badlogic.ashley.core.ComponentMapper;
import com.mygdx.pmd.model.components.*;

public class Mappers {
    public static ComponentMapper<RenderComponent> Render = ComponentMapper.getFor(RenderComponent.class);
    public static ComponentMapper<NameComponent> Name = ComponentMapper.getFor(NameComponent.class);
    public static ComponentMapper<DirectionComponent> Direction = ComponentMapper.getFor(DirectionComponent.class);
    public static ComponentMapper<PositionComponent> Position = ComponentMapper.getFor(PositionComponent.class);
    public static ComponentMapper<MoveComponent> Movement = ComponentMapper.getFor(MoveComponent.class);
    public static ComponentMapper<AnimationComponent> Animation = ComponentMapper.getFor(AnimationComponent.class);
}
