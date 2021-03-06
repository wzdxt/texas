package com.wzdxt.texas.business.display;

import com.wzdxt.texas.model.Card;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Created by dai_x on 17-9-4.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameStatus {
    Phase phase = Phase.NONE;          // ok
    // not in game
    int totalCoin;          // ok
    // in game
    Status status = Status.NONE;          // ok
    boolean[] playerExist;              // ok
    int bigBlind;            // ok
    boolean[] playerRemain;          // ok
    private int remainNum;          // ok
    @Deprecated
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
    // (call*2+pool)/bb*bb-pool

    public int getMyPool() {
        return playerPools == null ? 0
                : playerPools[0];
    }

    public int getThisTurnPool() {
        return playerPools == null ? 0
                : Arrays.stream(playerPools).sum();
    }

    public int getRemainNum() {
        return playerRemain == null ? 0
                : (int)IntStream.range(0, playerRemain.length).filter(i -> playerRemain[i]).count();
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

        public static Status of(String name) {
            return Status.valueOf(name.toUpperCase());
        }
    }
}
