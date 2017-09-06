package com.wzdxt.texas.business.display;

import lombok.Builder;
import lombok.Data;

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
    int playerNum;
    int currentTurn;
    int pool;
    // my turn


    public enum Phase {
        NONE,
        MAIN_PAGE,
        WAITING,
        WATCHING,
        MY_TURN;
    }
}
