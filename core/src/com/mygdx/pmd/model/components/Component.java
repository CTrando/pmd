package com.mygdx.pmd.model.components;

/**
 * Created by Cameron on 4/16/2017.
 */
public interface Component {
   /* public static final byte MOVE = 00000000;
    public static final byte ACTION = 00000001;
    public static final byte COMBAT = 00000010;
    public static final byte DIRECTION = 00000011;
    public static final byte POSITION = 00000100;
    public static final byte TURN = 00000101;*/

   public static final String MOVE = "move";
   public static final String ACTION = "action";
   public static final String COMBAT = "combat";
   public static final String DIRECTION = "direction";
   public static final String POSITION = "position";
   public static final String TURN = "turn";

    //TODO Make damageable component or fighting component or attack component or something of the sort
}
