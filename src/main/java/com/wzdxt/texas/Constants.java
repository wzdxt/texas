package com.wzdxt.texas;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wzdxt on 2017/8/26.
 */
public class Constants {
    public static final byte TOTAL_CARD = 52;
    public static final byte TOTAL_SUIT = 4;
    public static final byte TOTAL_RANK = 13;
    public static final Map<Byte, String> SUIT_MAP = new HashMap<>();
    public static final Map<Byte, String> RANK_MAP = new HashMap<>();
    public static final Map<String, Byte> SUIT_REVERSE_MAP = new HashMap<>();
    public static final Map<String, Byte> RANK_REVERSE_MAP = new HashMap<>();
    public static final byte RANK_2 = 0;
    public static final byte RANK_3 = 1;
    public static final byte RANK_4 = 2;
    public static final byte RANK_5 = 3;
    public static final byte RANK_6 = 4;
    public static final byte RANK_7 = 5;
    public static final byte RANK_8 = 6;
    public static final byte RANK_9 = 7;
    public static final byte RANK_10 = 8;
    public static final byte RANK_J = 9;
    public static final byte RANK_Q = 10;
    public static final byte RANK_K = 11;
    public static final byte RANK_A = 12;

    static {
        SUIT_MAP.put((byte) 0, "♠");
        SUIT_MAP.put((byte) 1, "♥");
        SUIT_MAP.put((byte) 2, "♣");
        SUIT_MAP.put((byte) 3, "♦");
        RANK_MAP.put((byte) 0, "2");
        RANK_MAP.put((byte) 1, "3");
        RANK_MAP.put((byte) 2, "4");
        RANK_MAP.put((byte) 3, "5");
        RANK_MAP.put((byte) 4, "6");
        RANK_MAP.put((byte) 5, "7");
        RANK_MAP.put((byte) 6, "8");
        RANK_MAP.put((byte) 7, "9");
        RANK_MAP.put((byte) 8, "10");
        RANK_MAP.put((byte) 9, "J");
        RANK_MAP.put((byte) 10, "Q");
        RANK_MAP.put((byte) 11, "K");
        RANK_MAP.put((byte) 12, "A");
        SUIT_MAP.forEach((k, v) -> SUIT_REVERSE_MAP.put(v, k));
        RANK_MAP.forEach((k, v) -> RANK_REVERSE_MAP.put(v, k));
    }

    public static final String LEVELDB_75_DIR = "./data/leveldb-75";
    public static final String LEVELDB_23_DIR = "./data/leveldb-23";
    public static final String LEVELDB_RIVER_LARGER_DIR = "./data/leveldb-river-larger";
}
