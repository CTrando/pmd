package com.mygdx.pmd.model;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.pmd.enums.Direction;
import com.mygdx.pmd.model.entity.tile.Tile;
import com.mygdx.pmd.system.RenderSystem;
import com.mygdx.pmd.utils.Constants;

public class Floor {

    private Tile[][] tileBoard;

    public Floor() {
        initTileBoard();
    }

    private void initTileBoard() {
        tileBoard = new Tile[Constants.TILE_SIZE][Constants.TILE_SIZE];
        for (int i = 0; i < tileBoard.length; i++) {
            for (int j = 0; j < tileBoard.length; j++) {
                tileBoard[i][j] = new Tile(i, j);
            }
        }
    }

    public void addToEngine(Engine engine) {
        for (Tile[] row : tileBoard) {
            for (Tile tile : row) {
                engine.addEntity(tile);
            }
        }
    }

    public Tile getTile(Vector2 pos) {
        Vector2 normalizePos = new Vector2(pos);
        try {
            return tileBoard[(int) normalizePos.x][(int) normalizePos.y];
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Invalid position");
        }
    }

    public Tile getRelative(Tile tile, Direction dir) {
        try {
            switch (dir) {
                case up:
                    return tileBoard[tile.getX()][tile.getY() + 1];
                case down:
                    return tileBoard[tile.getX()][tile.getY() - 1];
                case left:
                    return tileBoard[tile.getX() - 1][tile.getY()];
                case right:
                    return tileBoard[tile.getX() + 1][tile.getY()];
                default:
                    return null;
            }
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }
}
