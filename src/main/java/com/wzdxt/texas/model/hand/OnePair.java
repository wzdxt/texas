package com.wzdxt.texas.model.hand;

import com.wzdxt.texas.Constants;
import com.wzdxt.texas.model.Card;
import com.wzdxt.texas.model.CardSet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by wzdxt on 2017/8/26.
 */
public class OnePair extends AbsHand {
    protected OnePair(Collection<Card> c) {
        super(c);
    }

    @Override
    public int getSort() {
        return 1;
    }

    /**
     *
     * @param c1 one of pair cards
     * @param c
     * @return
     */
    public static OnePair of(Card c1, Collection<Card> c) {
        OnePair p = new OnePair(c);
        p.rank = c1.getRank();
        p.add(c1);
        if (p.size() != 5) {
            throw new IllegalArgumentException();
        }
        return p;
    }

    public static OnePair compose(Collection<Card> c) {
        int[] rankCnt = new int[Constants.TOTAL_RANK];
        for (Card card : c) {
            int rank = card.getRank();
            rankCnt[rank]++;
            if (rankCnt[rank] >= 2) {
                return OnePair.of(card, c);
            }
        }
        return null;
    }

    /**
     *
     * @param c can receive any size
     * @return
     */
    public static OnePair compose7(CardSet c) {
        Card prev = null;
        Card big1 = null;
        Card big2 = null;
        for (Iterator<Card> iter = c.descendingIterator();iter.hasNext();) {
            Card card = iter.next();
            if (prev != null && prev.getRank() == card.getRank()) {
                big1 = prev;
                big2 = card;
                break;
            }
            prev = card;
        }
        if (big1 != null) {
            List<Card> fin = new ArrayList<>();
            fin.add(big1);
            fin.add(big2);
            for (Card card : c.descendingSet()) {
                if (card != big1 && card != big2) {
                    fin.add(card);
                    if (fin.size() == 5) {
                        return OnePair.of(prev, fin);
                    }
                }
            }
        }
        return null;
    }

}
