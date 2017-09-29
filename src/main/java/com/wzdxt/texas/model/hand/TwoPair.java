package com.wzdxt.texas.model.hand;

import com.wzdxt.texas.Constants;
import com.wzdxt.texas.model.Card;
import com.wzdxt.texas.model.CardSet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by wzdxt on 2017/8/26.
 */
public class TwoPair extends AbsHand {
    public static final int SORT = 2;

    protected int rank2; //larger than rank

    protected TwoPair(Collection<Card> c) {
        super(c);
    }

    @Override
    public int getSort() {
        return 2;
    }

    @Override
    public long getValue() {
        int ret = rank2;
        ret = ret * Constants.TOTAL_RANK + rank;
        for (Card card : this.descendingSet()) {
            if (card.getRank() != rank && card.getRank() != rank2) {
                ret = ret * Constants.TOTAL_RANK + card.getRank();
            }
        }

        return ret;
    }

    /**
     * @param c1 larger
     * @param c2 smaller
     * @param c
     * @return
     */
    public static TwoPair of(Card c1, Card c2, Collection<Card> c) {
        TwoPair tp = new TwoPair(c);
        tp.add(c1);
        tp.add(c2);
        if (c1.getRank() > c2.getRank()) {
            tp.rank2 = c1.getRank();
            tp.rank = c2.getRank();
        } else {
            tp.rank2 = c2.getRank();
            tp.rank = c1.getRank();
        }
        return tp;
    }

    public static TwoPair compose(Collection<Card> c) {
        int[] rankCnt = new int[Constants.TOTAL_RANK];
        Card prepared = null;
        for (Card card : c) {
            int rank = card.getRank();
            rankCnt[rank]++;
            if (rankCnt[rank] == 2) {
                if (prepared == null) {
                    prepared = card;
                } else {
                    return TwoPair.of(prepared, card, c);
                }
            }
        }
        return null;
    }

    public static TwoPair compose7(CardSet c) {
        int[] rankCnt = new int[Constants.TOTAL_RANK];
        c.forEach(card -> rankCnt[card.getRank()]++);
        int rank2 = -1;
        int rank22 = -1;
        int large = -1;
        outer:
        for (int rank = Constants.TOTAL_RANK - 1; rank >= 0; rank--) {
            switch (rankCnt[rank]) {
                case 2:
                    if (rank2 == -1) {
                        rank2 = rank;
                    } else if (rank22 == -1) {
                        rank22 = rank;
                    } else {
                        large = rank;
                        break outer;
                    }
                    break;
                case 1:
                    if (large == -1) {
                        large = rank;
                    }
                default:
                    break;
            }
        }
        if (rank22 > -1) {
            List<Card> fin = new ArrayList<>(5);
            Card c2 = null, c22 = null;
            for (Card card : c.descendingSet()) {
                if (card.getRank() == rank2) c2 = card;
                if (card.getRank() == rank22) c22 = card;
                if (card.getRank() == rank2 || card.getRank() == rank22 || card.getRank() == large) {
                    fin.add(card);
                    if (fin.size() == 5) {
                        return TwoPair.of(c2, c22, fin);
                    }
                }
            }
        }
        return null;
    }

}