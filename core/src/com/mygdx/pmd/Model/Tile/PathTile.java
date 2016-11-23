package com.mygdx.pmd.Model.Tile;


import com.badlogic.gdx.utils.Array;
import com.mygdx.pmd.Model.FloorComponent.Floor;
import com.mygdx.pmd.Screen.DungeonScreen;

/**
 * Created by Cameron on 7/4/2016.
 */
public abstract class PathTile extends Tile {

    public PathTile(int row, int col, Floor floor, String classifier) {
        super(row, col, floor, classifier);
        this.isWalkable = true;
        this.sprite = DungeonScreen.sprites.get("vpathtilesprite");
    }

    @Override
    public boolean isLegal(){
        int gTally = 0;
        int rTally = 0;
        int pTally = 0;

        Array<Tile> tileArray = Tile.getTilesAroundTile(tileBoard, this);
        for(Tile t: tileArray){
            if(t instanceof GenericTile) gTally++;
            if(t instanceof RoomTile) rTally++;
        }

        if(gTally > 3) return false;
        if(rTally > 3) return false;
        return true;
    }
}
