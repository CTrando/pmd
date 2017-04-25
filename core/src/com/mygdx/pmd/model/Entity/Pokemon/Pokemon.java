package com.mygdx.pmd.model.Entity.Pokemon;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.mygdx.pmd.PMD;
import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.interfaces.*;
import com.mygdx.pmd.model.Entity.*;
import com.mygdx.pmd.model.Floor.*;
import com.mygdx.pmd.model.Tile.*;
import com.mygdx.pmd.model.components.*;
import com.mygdx.pmd.model.logic.*;
import com.mygdx.pmd.utils.Constants;

public abstract class Pokemon extends Entity implements Logical {
    public Entity target;

    public TurnComponent tc;
    public CombatComponent cc;
    public MoveComponent mc;
    public DirectionComponent dc;
    public PositionComponent pc;
    public ActionComponent ac;
    public AnimationComponent anc;

    Logic logic;

    private PokemonName pokemonName;
    public Array<Move> moves;

    private Move move;
    public boolean attacking;

    /**
     * Constructor for Pokemon is meant to inject blank information, such as empty arrays and strings, and trivial
     * identification such as names and identification
     *
     * <p>More complex behaviors are left in the init method, which will be called by @PokemonFactory</p>
     * @param floor
     * @param x
     * @param y
     * @param pokemonName
     */
    Pokemon(Floor floor, int x, int y, PokemonName pokemonName) {
        super(floor, x, y);
        this.pokemonName = pokemonName;
        this.children = new Array<Entity>();

        //initialize moves and add default move
        moves = new Array<Move>(4);
    }

    public void init(){
        components.put(ActionComponent.class, new ActionComponent(this));
        components.put(TurnComponent.class, new TurnComponent(this));
        components.put(DirectionComponent.class, new DirectionComponent(this));
        components.put(CombatComponent.class, new CombatComponent(this));
        components.put(MoveComponent.class, new MoveComponent(this));
        components.put(RenderComponent.class, new RenderComponent(this));
        //should probably leave this to the entities themselves

        this.tc = getComponent(TurnComponent.class);
        this.cc = getComponent(CombatComponent.class);
        this.mc = getComponent(MoveComponent.class);
        this.dc = getComponent(DirectionComponent.class);
        this.pc = getComponent(PositionComponent.class);
        this.ac = getComponent(ActionComponent.class);
        this.anc = getComponent(AnimationComponent.class);

        cc.setHp(100);
        tc.setTurnState(Turn.COMPLETE);
        cc.setAggressionState(Aggression.passive);
        mc.setFacingTile(dc.getDirection());

        //default move
        moves.add(Move.SCRATCH);
        animationLogic = new AnimationLogic(this);
    }

    /**
     * Possible error with this because of casting - will try to avoid later
     * @param tile
     * @return
     */
    //@Override
    public boolean isLegalToMoveTo(Tile tile) {
        if (tile == null) return false;

       /* if (tile.hasEntityOfType(Movable.class)) {
            for (Aggressible aggressible : PUtils.getObjectsOfType(Aggressible.class, tile.getEntityList())) {
                if (aggressible.isAggressive()) {
                    return false;
                }
            }
        }*/
        return tile.isWalkable;
    }

    @Override
    public void update() {
        // have it run its logic first before it executes its instructions
        // may have negative repercussions
        this.runLogic();

        for(Entity child: children){
            child.update();
        }

        animationLogic.execute();
        super.update();
    }

    public void randomizeLocation() {
        Tile random = floor.chooseUnoccupiedTile();

        if (random.isWalkable) {
            mc.setNextTile(random);

            pc.removeFromCurrentTile();
            mc.addToTile(random);
            mc.setFacingTile(dc.getDirection());

            pc.setCurrentTile(random);
            mc.possibleNextTile = null;
        } else randomizeLocation();
    }

    public boolean canSeeEnemy() {
        if (!cc.isAggressive()){
            return false;
        }
        int rOffset = 0;
        int cOffset = 0;

        switch (dc.getDirection()) {
            case down:
                rOffset = -1;
                break;
            case up:
                rOffset = 1;
                break;
            case right:
                cOffset = 1;
                break;
            case left:
                cOffset = -1;
        }
        for (int i = 1; i <= Constants.VISIBILITY_RANGE; i++) {
            //these are the rules for viewing things

            try {
                Tile tile = tileBoard[pc.getCurrentTile().row + i * rOffset][pc.getCurrentTile().col + i * cOffset];
                if (tile instanceof GenericTile) return false;

                if(tile == pc.getCurrentTile()) return false;

                for(Entity entity: tile.getEntityList()){
                    if(entity == target){
                        return true;
                    }
                }

            } catch (ArrayIndexOutOfBoundsException e) {
            }
        }
        return false;
    }

    public void runLogic(){
        logic.execute();
    }

    public String toString(){
        return this.pokemonName.toString();
    }

    public void dispose() {
        pc.getCurrentTile().removeEntity(this);
    }

    public Move getRandomMove() {
        return moves.random();
    }

    public Move getRandomRangedMove() {
        Array<Move> retMoves = new Array<Move>();
        for(Move move: moves){
            if(move.isRanged()){
                retMoves.add(move);
            }
        }
        return retMoves.random();
    }

    public void resetMove(){
        this.move = null;
    }

    public Move getMove() {
        return move;
    }

    public void setMove(Move move){
        this.move = move;
    }

    public void reset(){
        super.reset();
        ac.setActionState(Action.IDLE);
        tc.setTurnState(Turn.COMPLETE);
    }
}
