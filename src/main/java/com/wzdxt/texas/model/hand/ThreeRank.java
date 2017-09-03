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
public class ThreeRank extends AbsHand {
    protected ThreeRank(Collection<Card> c) {
        super(c);
    }

    @Override
    public int getSort() {
        return 3;
    }

    public static ThreeRank of(Card c1, Collection<Card> c) {
        ThreeRank tr = new ThreeRank(c);
        tr.rank = c1.getRank();
        tr.add(c1);
        return tr;
    }

    public static ThreeRank compose(Collection<Card> c) {
        int[] rankCnt = new int[Constants.TOTAL_RANK];
        for (Card card : c) {
            int rank = card.getRank();
            rankCnt[rank]++;
            if (rankCnt[rank] >= 3) {
                return ThreeRank.of(card, c);
            }
        }
        return null;
    }

    /**
     *
     * @param c can receive any size
     * @return
     */
    public static ThreeRank compose7(CardSet c) {
        Map<Integer, CardSet> allCardSet = new HashMap<>(13);
        for (Iterator<Card> iter = c.descendingIterator(); iter.hasNext(); ) {
            Card card = iter.next();
            CardSet set = allCardSet.computeIfAbsent(card.getRank(), a -> new CardSet());
            set.add(card);
            if (set.size() == 3) {
                Card sample = set.first();
                CardSet fin = new CardSet(set);
                for (Card inner : c.descendingSet()) {
                    fin.add(inner);
                    if (fin.size() == 5) {
                        return ThreeRank.of(sample, fin);
                    }
                }
            }
        }
        return null;
    }

}
