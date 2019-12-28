package com.mygdx.pmd.model.Entity.Pokemon;


import com.mygdx.pmd.enums.PokemonType;
import com.mygdx.pmd.model.Entity.Entity;
import com.mygdx.pmd.model.components.*;
import com.mygdx.pmd.utils.AssetManager;


public abstract class Pokemon extends Entity {

    private PokemonType fType;

    public Pokemon(PokemonType type) {
        fType = type;
        add(new NameComponent(type.toString()));
        add(new PositionComponent(0, 0));
        add(new DirectionComponent());
        add(new AnimationComponent(fType.toString(), "idleDown"));
    }

    @Override
    public String toString() {
        return fType.toString();
    }

    /*
    Pokemon(Floor floor, int x, int y, PokemonName pokemonName) {
        super(floor, x, y);
        this.pokemonName = pokemonName;
        this.children = new Array<TestEntity>();

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
        this.rc = getComponent(RenderComponent.class);
        this.anc = getComponent(AnimationComponent.class);

        cc.setHp(100);
        tc.setTurnState(Turn.COMPLETE);
        cc.setAggressionState(Aggression.passive);
        mc.setFacingTile(dc.getDirection());
        anc.setCurrentAnimation("downidle");

        //default move
        moves.add(Move.SCRATCH);
    }

    /*
    @Override
    public void update() {
        // have it run its logic first before it executes its instructions
        // may have negative repercussions
        this.runLogic();

        for(TestEntity child: children){
            child.update();
        }

        rc.setSprite(anc.getCurrentSprite());
        super.update();
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

                for(TestEntity entity: tile.getEntityList()){
                    if(entity == cc.getTarget()){
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
        children.clear();
        dc.setDirection(Direction.down);
        anc.setCurrentAnimation("downidle");
        ac.setActionState(Action.IDLE);
        tc.setTurnState(Turn.COMPLETE);
    }
    */
}
