package com.wzdxt.texas;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wzdxt on 2017/8/26.
 */
public class Constants {
    public static final int TOTAL_CARD = 52;
    public static final int TOTAL_SUIT = 4;
    public static final int TOTAL_RANK = 13;
    public static final Map<Integer, String> SUIT_MAP = new HashMap<>();
    public static final Map<Integer, String> RANK_MAP = new HashMap<>();
    public static final Map<String, Integer> SUIT_REVERCE_MAP = new HashMap<>();
    public static final Map<String, Integer> RANK_REVERCE_MAP = new HashMap<>();
    public static final int RANK_2 = 0;
    public static final int RANK_3 = 1;
    public static final int RANK_4 = 2;
    public static final int RANK_5 = 3;
    public static final int RANK_6 = 4;
    public static final int RANK_7 = 5;
    public static final int RANK_8 = 6;
    public static final int RANK_9 = 7;
    public static final int RANK_10 = 8;
    public static final int RANK_J = 9;
    public static final int RANK_Q = 10;
    public static final int RANK_K = 11;
    public static final int RANK_A = 12;

    static {
        SUIT_MAP.put(0, "♠");
        SUIT_MAP.put(1, "♥");
        SUIT_MAP.put(2, "♣");
        SUIT_MAP.put(3, "♦");
        RANK_MAP.put(0, "2");
        RANK_MAP.put(1, "3");
        RANK_MAP.put(2, "4");
        RANK_MAP.put(3, "5");
        RANK_MAP.put(4, "6");
        RANK_MAP.put(5, "7");
        RANK_MAP.put(6, "8");
        RANK_MAP.put(7, "9");
        RANK_MAP.put(8, "10");
        RANK_MAP.put(9, "J");
        RANK_MAP.put(10, "Q");
        RANK_MAP.put(11, "K");
        RANK_MAP.put(12, "A");
        SUIT_MAP.forEach((k, v) -> SUIT_REVERCE_MAP.put(v, k));
        RANK_MAP.forEach((k, v) -> RANK_REVERCE_MAP.put(v, k));
    }

    public static final String LEVELDB_DIR = "./data/leveldb";
}
