package com.wzdxt.texas.model;

import com.wzdxt.texas.Constants;
import lombok.Value;

/**
 * Created by wzdxt on 2017/8/26.
 */
@Value
public class Card implements Comparable<Card> {
    private int suit;
    private int rank;

    public int getId() {
        return Constants.TOTAL_RANK * suit + rank;
    }

    public String toString() {
        return Constants.SUIT_MAP.get(suit) + Constants.RANK_MAP.get(rank);
    }

    @Override
    public int compareTo(Card o) {
        return rank > o.rank ? 1 : (rank < o.rank ? -1 : (suit - o.suit));
    }

    @Override
    public boolean equals(Object o) {
        return !(o == null || !(o instanceof Card)) && this.getId() == ((Card)o).getId();
    }

    public static Card of(int id) {
        if (id < 0 || id >= Constants.TOTAL_CARD)
            throw new IllegalArgumentException();
        int suit = id / Constants.TOTAL_RANK;
        int rank = id % Constants.TOTAL_RANK;
        return new Card(suit, rank);
    }

    public static Card of(String s) {
        Integer suit = Constants.SUIT_REVERCE_MAP.get(s.substring(0, 1));
        Integer rank = Constants.RANK_REVERCE_MAP.get(s.substring(1));
        assert suit != null;
        assert rank != null;
        return new Card(suit, rank);
    }
}
