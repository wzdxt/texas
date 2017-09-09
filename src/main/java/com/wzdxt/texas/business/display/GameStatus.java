package com.wzdxt.texas.business.display;

import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * Created by dai_x on 17-9-4.
 */
@Data
@Builder
public class GameStatus {
    Phase phase;
    // not in game
    int totalCoin;
    // in game
    Status status;
    int playerNum;
    int remainNum;
    int currentTurn;
    int pool;
    int thisTurnPool;
    // my turn


    public enum Phase {
        NONE,
        MAIN_PAGE,
        ROOM_PAGE,
        WAITING,
        PLAYING,
    }

    public enum Status {
        NONE,
        WATCHING,
        MY_TURN,
        FINISH,
    }
}
