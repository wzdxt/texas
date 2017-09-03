package com.wzdxt.texas.model.hand;

import com.wzdxt.texas.Constants;
import com.wzdxt.texas.model.Card;
import com.wzdxt.texas.model.CardSet;

import java.util.Collection;

/**
 * Created by wzdxt on 2017/8/26.
 */
public class HighCard extends AbsHand {

    protected HighCard(Collection<Card> c) {
        super(c);
    }

    @Override
    public int getSort() {
        return 0;
    }

    /**
     * for texas comparing
     *
     * @return
     */
    @Override
    public long getValue() {
        int idx = 0;
        int ret = 0;
        for (Card card : this) {
            ret += Math.pow(Constants.TOTAL_CARD, idx++) * card.getId();
        }
        return ret;
    }

    public static HighCard of(Collection<Card> c) {
        HighCard h = new HighCard(c);
        if (h.size() != 5) {
            throw new IllegalArgumentException();
        }
        return h;
    }

    public static HighCard compose(Collection<Card> c) {
        return HighCard.of(c);
    }

    public static HighCard compose7(CardSet c) {
        CardSet fin = new CardSet();
        for (Card card : c.descendingSet()) {
            fin.add(card);
            if (fin.size() == 5) {
                return HighCard.of(fin);
            }
        }
        return null;
    }
}
