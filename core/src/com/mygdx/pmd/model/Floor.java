package com.mygdx.pmd.model;

import com.badlogic.ashley.core.Engine;
import com.mygdx.pmd.model.Entity.tile.Tile;
import com.mygdx.pmd.utils.Constants;

public class Floor {

    private Tile[][] tileBoard;

    public Floor() {
        initTileBoard();
    }

    private void initTileBoard() {
        tileBoard = new Tile[Constants.TILE_SIZE][Constants.TILE_SIZE];
        for(int i = 0; i < tileBoard.length; i++) {
            for(int j = 0 ;j < tileBoard.length; j++) {
                tileBoard[i][j] = new Tile(i, j);
            }
        }
    }

    public void addToEngine(Engine engine) {
        for(Tile[] row : tileBoard) {
            for(Tile tile: row) {
                engine.addEntity(tile);
            }
        }
    }
}
