package com.mygdx.pmd.model.components;

/**
 * Created by Cameron on 4/16/2017.
 */
/*
public class MoveComponent {
    private TestEntity entity;
    private Tile[][] tileBoard;

    private boolean forcedMove;
    private int speed;

    private PositionComponent pc;
    private DirectionComponent dc;

    private Tile nextTile;
    //perhaps have facingTile calculated dynamically each time to avoid faulty variable storing
    private Tile facingTile;
    public Tile possibleNextTile;

    public MoveComponent(TestEntity entity) {
        this.entity = entity;
        this.pc = entity.getComponent(PositionComponent.class);
        this.dc = entity.getComponent(DirectionComponent.class);

        tileBoard = entity.tileBoard;
    }

    public void move(int dx, int dy) {
        pc.x += dx;
        pc.y += dy;
    }

    public void forceMoveToTile(Tile nextTile, Direction direction) {
        this.setNextTile(nextTile);
        this.setFacingTile(nextTile);

        entity.instructions.add(new MoveInstruction(entity, nextTile));

        dc.setDirection(direction);
        forcedMove = true;
    }

    public void moveToTile(Tile nextTile, int speed) {
        if (nextTile == null || entity.equals(nextTile)) {
            return;
        }
        
        int y = pc.y;
        int x = pc.x;
                
        if (y > nextTile.y && x > nextTile.x) {
            move(-speed, -speed);
        } else if (y < nextTile.y && x > nextTile.x) {
            move(-speed, speed);
        } else if (y < nextTile.y && x < nextTile.x) {
            move(speed, speed);
        } else if (y > nextTile.y && x < nextTile.x) {
            move(speed, -speed);
        } else if (y > nextTile.y) {
            move(0, -speed);
        } else if (y < nextTile.y) {
            move(0, speed);
        } else if (x < nextTile.x) {
            move(speed, 0);
        } else if (x > nextTile.x) {
            move(-speed, 0);
        }
    }

    public void setNextTile(Tile tile) {
        if (tile == null) return;
        this.nextTile = tile;
    }

    public void setFacingTile(Direction d) {
        try {
            Tile currentTile = pc.getCurrentTile();
            int curRow = currentTile.row;
            int curCol = currentTile.col;

            switch (d) {
                case up:
                    facingTile = tileBoard[curRow + 1][curCol];
                    break;
                case down:
                    facingTile = tileBoard[curRow - 1][curCol];
                    break;
                case right:
                    facingTile = tileBoard[curRow][curCol + 1];
                    break;
                case left:
                    facingTile = tileBoard[curRow][curCol - 1];
                    break;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        }
    }

    public void randomizeLocation() {
        Tile random = entity.floor.chooseUnoccupiedTile();

        if (random.isLegalToMoveTo(entity)) {
            placeAt(random);
        } else randomizeLocation();
    }

    public void placeAt(Tile tile){
        setNextTile(tile);

        pc.removeFromCurrentTile();
        addToTile(tile);
        setFacingTile(dc.getDirection());

        pc.setCurrentTile(tile);

        possibleNextTile = null;
    }

    public void setFacingTile(Tile tile) {
        facingTile = tile;
    }

    public void setSpeed(int speed){
        this.speed = speed;
    }

    public Tile getNextTile(){
        return nextTile;
    }
    
    public Tile getFacingTile() {
        return facingTile;
    }

    public void addToTile(Tile nextTile) {
        nextTile.addEntity(entity);
    }

    public int getSpeed() {
        return speed;
    }

    public boolean isLegalToMoveTo(Tile nextTile) {
        return nextTile.isWalkable;
    }

    public boolean isForcedMove() {
        return forcedMove;
    }

    public void setForcedMove(boolean forcedMove) {
        this.forcedMove = forcedMove;
    }
}
*/
