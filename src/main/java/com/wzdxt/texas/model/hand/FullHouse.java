package com.wzdxt.texas.model.hand;

import com.wzdxt.texas.Constants;
import com.wzdxt.texas.model.Card;
import com.wzdxt.texas.model.CardSet;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by wzdxt on 2017/8/27.
 */
public class FullHouse extends AbsHand {
    public static final int SORT = 6;

    protected int rank2; // triple

    protected FullHouse(Collection<Card> c) {
        super(c);
    }

    @Override
    public int getSort() {
        return 6;
    }

    @Override
    public long getValue() {
        return rank2 * Constants.TOTAL_RANK + rank;
    }

    /**
     * @param c1 triple
     * @param c
     * @return
     */
    public static FullHouse of(Card c1, Collection<Card> c) {
        FullHouse fh = new FullHouse(c);
        fh.rank2 = c1.getRank();
        fh.add(c1);
        for (Card card : c) {
            if (card.getRank() != fh.rank2) {
                fh.rank = card.getRank();
                break;
            }
        }
        if (fh.size() != 5) {
            throw new IllegalArgumentException();
        }
        return fh;
    }

    public static FullHouse compose(Collection<Card> c) {
        int[] rankCnt = new int[Constants.TOTAL_RANK];
        c.forEach(card -> rankCnt[card.getRank()]++);
        int rank3 = -1;
        for (int i = 0; i < rankCnt.length; i++) {
            int cnt = rankCnt[i];
            switch (cnt) {
                case 0:
                case 2:
                    break;
                case 3:
                    rank3 = i;
                    break;
                default:
                    return null;
            }
        }
        if (rank3 == -1) {
            return null;
        }
        for (Card card : c) {
            if (card.getRank() == rank3) {
                return FullHouse.of(card, c);
            }
        }
        return null;
    }

    public static FullHouse compose7(CardSet c) {
        int[] rankCnt = new int[Constants.TOTAL_RANK];
        c.forEach(card -> rankCnt[card.getRank()]++);
        int rank3 = -1;
        int rank2 = -1;
        outer:
        for (int rank = Constants.TOTAL_RANK - 1; rank >= 0; rank--) {
            switch (rankCnt[rank]) {
                case 3:
                    if (rank3 == -1) {
                        rank3 = rank;
                    } else {
                        rank2 = rank;
                        break outer;
                    }
                    break;
                case 2:
                    if (rank2 == -1) rank2 = rank;
                    if (rank3 > -1) break outer;
                    break;
                default:
                    break;
            }
        }
        if (rank3 > -1 && rank2 > -1) {
            CardSet fin = new CardSet();
            Card large = null;
            for (Card card : c.descendingSet()) {
                if (card.getRank() == rank3) {
                    large = card;
                }
                if (card.getRank() == rank3 || card.getRank() == rank2) {
                    fin.add(card);
                    if (fin.size() == 5) {
                        return FullHouse.of(large, fin);
                    }
                }
            }
        }
        return null;
    }

}
