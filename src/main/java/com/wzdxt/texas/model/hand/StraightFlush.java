package com.wzdxt.texas.model.hand;

import com.wzdxt.texas.Constants;
import com.wzdxt.texas.model.Card;
import com.wzdxt.texas.model.CardSet;

import java.util.Collection;

/**
 * Created by wzdxt on 2017/8/27.
 */
public class StraightFlush extends AbsHand {
    protected int suit;

    protected StraightFlush(Collection<Card> c) {
        super(c);
    }

    @Override
    public int getSort() {
        return 8;
    }

    @Override
    public long getValue() {
        return rank == Constants.RANK_A ? 1 : rank + 2;
    }

    public static StraightFlush of(Collection<Card> c) {
        StraightFlush sf = new StraightFlush(c);
        sf.rank = sf.first().getRank();
        if (sf.rank == Constants.RANK_2 && sf.last().getRank() == Constants.RANK_A) {
            sf.rank = Constants.RANK_A;
        }
        sf.suit = sf.first().getSuit();
        if (sf.size() != 5) {
            throw new IllegalArgumentException();
        }
        return sf;
    }

    public static StraightFlush compose(Collection<Card> c) {
        if (Straight.compose(c) == null)
            return null;
        if (Flush.compose(c) == null)
            return null;
        return StraightFlush.of(c);
    }

    public static StraightFlush compose7(CardSet c) {
        CardSet[] allCardSet = new CardSet[]{
                new CardSet(),
                new CardSet(),
                new CardSet(),
                new CardSet(),
        };
        for (Card card : c) {
            allCardSet[card.getSuit()].add(card);
        }
        for (CardSet set : allCardSet) {
            if (set.size() >= 5) {
                Straight straight = Straight.compose7(set);
                return straight == null ? null : StraightFlush.of(straight);
            }
        }
        return null;
    }

}
