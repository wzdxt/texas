package com.wzdxt.texas.model.hand;

import com.wzdxt.texas.Constants;
import com.wzdxt.texas.model.Card;
import com.wzdxt.texas.model.CardSet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
        List<Card>[] allCardSet = new List[13];
        for (Iterator<Card> iter = c.descendingIterator(); iter.hasNext(); ) {
            Card card = iter.next();
            int rank = card.getRank();
            if (allCardSet[rank] == null)
                allCardSet[rank] = new ArrayList<>(3);
//            List<Card> list = allCardSet.computeIfAbsent(card.getRank(), a -> new ArrayList<>(3));
            List<Card> list = allCardSet[rank];
            list.add(card);
            if (list.size() == 3) {
                Card sample = list.get(0);
                CardSet fin = new CardSet(list);
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
