package com.wzdxt.texas.business.display;

import lombok.Builder;
import lombok.Data;

/**
 * Created by dai_x on 17-9-4.
 */
@Data
@Builder
public class GameStatus {
    Phase phase = Phase.NONE;
    // not in game
    int totalCoin;
    // in game
    Status status = Status.NONE;
    int playerNum;
    boolean[] enemyRemain;  // ok
    int remainNum;          // ok
    int currentTurn;
    int pool;
    int[] playerPools;
    int thisTurnPool;
    int myCoin;
    int myPool;
    int callNeed;
    // my turn

    public void setEnemyRemain(boolean[] pr) {
        this.enemyRemain = pr;
        this.remainNum = 0;
        for (boolean b : pr) {
            if (b) remainNum++;
        }
    }


    public enum Phase {
        NONE,
        MAIN_PAGE,
        ROOM_PAGE,
        WAITING,
        PLAYING;

        public static Phase of(String name) {
            return Phase.valueOf(name.toUpperCase());
        }
    }

    public enum Status {
        NONE,
        WATCHING,
        MY_TURN,
        FINISH;

        public static Phase of(String name) {
            return Phase.valueOf(name.toUpperCase());
        }
    }
}
