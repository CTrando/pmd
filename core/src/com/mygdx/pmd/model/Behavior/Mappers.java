package com.mygdx.pmd.model.Behavior;

import com.badlogic.ashley.core.ComponentMapper;
import com.mygdx.pmd.enumerations.*;

/**
 * Created by Cameron on 2/17/2017.
 */
public class Mappers {

    public static final ComponentMapper<TurnComponent> tm = ComponentMapper.getFor(TurnComponent.class);
    public static final ComponentMapper<AttackComponent> atm = ComponentMapper.getFor(AttackComponent.class);
    public static final ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    public static final ComponentMapper<ActionComponent> am = ComponentMapper.getFor(ActionComponent.class);
    public static final ComponentMapper<RenderComponent> rm = ComponentMapper.getFor(RenderComponent.class);
    public static final ComponentMapper<HPComponent> hp = ComponentMapper.getFor(HPComponent.class);
    public static final ComponentMapper<DirectionComponent> dm = ComponentMapper.getFor(DirectionComponent.class);

    /*public static final ComponentMapper<TurnComponent> tm = ComponentMapper.getFor(TurnComponent.class);
    public static final ComponentMapper<TurnComponent> tm = ComponentMapper.getFor(TurnComponent.class);
    public static final ComponentMapper<TurnComponent> tm = ComponentMapper.getFor(TurnComponent.class);*/
}
