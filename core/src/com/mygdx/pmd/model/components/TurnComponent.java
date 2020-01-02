package com.mygdx.pmd.model.components;


import com.badlogic.ashley.core.Component;

/**
 * Created by Cameron on 4/16/2017.
 */
enum TurnState {
    END,
    IDLE
}

public class TurnComponent implements Component {
    private TurnState fState;

    public TurnComponent() {
        fState = TurnState.END;
    }

    public boolean turnEnded() {
        return fState == TurnState.END;
    }

    public void startTurn() {
        switch (fState) {
            case IDLE:
                throw new IllegalStateException("Cannot start turn that has already start");
            case END:
                fState = TurnState.IDLE;
                break;
        }
    }

    public void endTurn() {
        switch (fState) {
            case IDLE:
                fState = TurnState.END;
                break;
            case END:
                throw new IllegalStateException("Cannot end turn that has already ended");
        }
    }
}

