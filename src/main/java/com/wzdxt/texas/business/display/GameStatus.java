package com.wzdxt.texas.business.display;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.stream.IntStream;

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
    boolean[] enemyRemain;
    private int remainNum;
    int currentTurn;
    int pool;
    int[] playerPools;
    int thisTurnPool;
    int myCoin;
    int myPool;
    // my turn

    public int getRemainNum() {
        return (int) IntStream.range(0, enemyRemain.length).filter(i->enemyRemain[i]).count();
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
