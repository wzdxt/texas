package com.wzdxt.texas.business.display;

import com.wzdxt.texas.model.Card;
import lombok.Builder;
import lombok.Data;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

/**
 * Created by dai_x on 17-9-4.
 */
@Data
@Builder
public class GameStatus {
    Phase phase = Phase.NONE;          // ok
    // not in game
    int totalCoin;          // ok
    // in game
    Status status = Status.NONE;          // ok
    boolean[] playerExist;              // ok
    int bigBlind;            //
    boolean[] enemyRemain;          // ok
    private int remainNum;          // ok
    int currentTurn;            // ok
    int totalPool;              // ok
    int[] playerPools;          // ok
    int thisTurnPool;       // ok
    int myCoin;          // ok
    int myPool;         // ok
    // my turn
    int callNeed;          // ok
    List<Card> myCard;          // ok
    List<Card> commonCard;          // ok

    public int getMyPool() {
        return playerPools[0];
    }

    public int getThisTurnPool() {
        return Arrays.stream(playerPools).sum();
    }

    public int getRemainNum() {
        return (int)IntStream.range(0, enemyRemain.length).filter(i -> enemyRemain[i]).count();
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
