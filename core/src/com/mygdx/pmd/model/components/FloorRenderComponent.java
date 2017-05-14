package com.mygdx.pmd.model.components;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.pmd.model.Floor.*;
import com.mygdx.pmd.model.Tile.*;
import com.mygdx.pmd.screens.DungeonScreen;

import static com.mygdx.pmd.screens.DungeonScreen.PPM;

/**
 * Created by Cameron on 4/23/2017.
 */
public class FloorRenderComponent extends RenderComponent {

    //TODO find out if this is overkill
    private Floor floor;
    private Tile[][] tileBoard;

    public FloorRenderComponent(Floor floor){
        super(floor);
        this.floor = floor;
        this.tileBoard = floor.tileBoard;
    }

    @Override
    public void render(SpriteBatch batch){
        for (int i = 0; i < tileBoard.length; i++) {
            for (int j = 0; j < tileBoard[0].length; j++) {
                Tile tile = tileBoard[i][j];
                RenderComponent rc = tile.getComponent(RenderComponent.class);
                PositionComponent tPc = tile.getComponent(PositionComponent.class);
                if(DungeonScreen.cameraBounds.contains(tPc.x/PPM, tPc.y/PPM)) {
                    rc.render(batch);
                }
                //drawing strings like this is very costly performance wise and causes stuttering
                //bFont.draw(batch, tile.spriteValue+"", tile.x + 5, tile.y+25/2);
            }
        }
    }

}
