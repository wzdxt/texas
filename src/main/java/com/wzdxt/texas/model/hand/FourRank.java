package com.wzdxt.texas.model.hand;

import com.wzdxt.texas.Constants;
import com.wzdxt.texas.model.Card;
import com.wzdxt.texas.model.CardSet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by wzdxt on 2017/8/27.
 */
public class FourRank extends AbsHand {
    public static final int SORT = 7;

    protected int rank1; // single

    protected FourRank(Collection<Card> c) {
        super(c);
    }

    @Override
    public int getSort() {
        return 7;
    }

    @Override
    public long getValue() {
        return rank * Constants.TOTAL_RANK + rank1;
    }

    public static FourRank of(Card c1, Collection<Card> c) {
        FourRank fr = new FourRank(c);
        fr.rank = c1.getRank();
        fr.add(c1);
        if (fr.size() != 5) {
            throw new IllegalArgumentException();
        }
        return fr;
    }

    public static FourRank compose(Collection<Card> c) {
        int[] rankCnt = new int[Constants.TOTAL_RANK];
        c.forEach(card -> rankCnt[card.getRank()]++);
        int rank4 = -1;
        for (int i = 0; i < rankCnt.length; i++) {
            int cnt = rankCnt[i];
            switch (cnt) {
                case 0:
                case 1:
                    break;
                case 4:
                    rank4 = i;
                    break;
                default:
                    return null;
            }
        }
        if (rank4 == -1) {
            return null;
        }
        for (Card card : c) {
            if (card.getRank() == rank4) {
                return FourRank.of(card, c);
            }
        }
        return null;
    }

    /**
     *
     * @param c can receive any size
     * @return
     */
    public static FourRank compose7(CardSet c) {
        Map<Byte, List<Card>> allCardSet = new HashMap<>(13);
        Card large = null;
        for (Iterator<Card> iter = c.descendingIterator(); iter.hasNext(); ) {
            Card card = iter.next();
            List<Card> list = allCardSet.computeIfAbsent(card.getRank(), a -> new ArrayList<>(4));
            if (allCardSet.size() > 4) break;
            list.add(card);
            if (list.size() == 4) {
                if (large.getRank() == card.getRank()) {
                    large = null;
                }
                if (large == null) {
                    large = iter.next();
                }
                Card sample = list.get(0);
                CardSet fin = new CardSet(list);
                fin.add(large);
                return FourRank.of(sample, fin);
            }
            if (large == null) {
                large = card;
            }
        }
        return null;
    }

}
