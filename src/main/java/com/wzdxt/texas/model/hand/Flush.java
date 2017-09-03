package com.wzdxt.texas.model.hand;

import com.wzdxt.texas.model.Card;
import com.wzdxt.texas.model.CardSet;

import java.util.Collection;

/**
 * Created by wzdxt on 2017/8/27.
 */
public class Flush extends AbsHand {
    protected int suit;

    protected Flush(Collection<Card> c) {
        super(c);
    }

    @Override
    public int getSort() {
        return 5;
    }

    public static Flush of(Collection<Card> c) {
        Flush f = new Flush(c);
        f.suit = f.first().getSuit();
        f.rank = f.first().getRank();
        if (f.size() != 5) {
            throw new IllegalArgumentException();
        }
        return f;
    }

    public static Flush compose(Collection<Card> c) {
        int suit = -1;
        for (Card card : c) {
            if (suit >= 0 && suit != card.getSuit()) {
                return null;
            }
            suit = card.getSuit();
        }
        return Flush.of(c);
    }

    /**
     *
     * @param c can receive any size
     * @return
     */
    public static Flush compose7(CardSet c) {
        CardSet[] suitCardSet = new CardSet[]{new CardSet(), new CardSet(), new CardSet(), new CardSet()};
        for (Card card : c.descendingSet()) {
            CardSet set = suitCardSet[card.getSuit()];
            set.add(card);
            if (set.size() == 5) {
                return Flush.of(set);
            }
        }
        return null;
    }

}
