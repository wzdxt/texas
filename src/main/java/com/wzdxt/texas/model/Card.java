package com.wzdxt.texas.model;

import com.wzdxt.texas.Constants;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wzdxt on 2017/8/26.
 */
@Getter
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Card implements Comparable<Card> {
    private byte suit;
    private byte rank;

    public static final List<Card> CARD_LIST;
    public static final Map<Byte, Card> CARD_MAP;
    public static final Map<Card, Byte> CARD_ID_MAP;

    static {
        CARD_LIST = new ArrayList<>(52);
        CARD_MAP = new HashMap<>(52);
        CARD_ID_MAP = new HashMap<>(52);
        for (byte r = 0; r < Constants.TOTAL_RANK; r++) {
            for (byte s = 0; s < Constants.TOTAL_SUIT; s++) {
                byte id = (byte) ((r << 2) | s);
                Card card = new Card(s, r);
                CARD_LIST.add(card);
                CARD_MAP.put(id, card);
                CARD_ID_MAP.put(card, id);
            }
        }
    }

    public byte getId() {
        return CARD_ID_MAP.get(this);
    }

    public String toString() {
        return Constants.SUIT_MAP.get(suit) + Constants.RANK_MAP.get(rank);
    }

    @Override
    public int compareTo(Card o) {
        return CARD_ID_MAP.get(this) - CARD_ID_MAP.get(o);
    }

    @Override
    public boolean equals(Object o) {
        return this == o;
    }

    public static Card of(byte id) {
        return CARD_MAP.get(id);
    }

    public static Card of(String s) {
        Byte suit = Constants.SUIT_REVERSE_MAP.get(s.substring(0, 1));
        Byte rank = Constants.RANK_REVERSE_MAP.get(s.substring(1));
        assert suit != null;
        assert rank != null;
        byte id = (byte) ((rank << 2) | suit);
        return CARD_MAP.get(id);
    }

    public static Card of(byte suit, byte rank) {
        byte id = (byte) ((rank << 2) | suit);
        return CARD_MAP.get(id);
    }

}
