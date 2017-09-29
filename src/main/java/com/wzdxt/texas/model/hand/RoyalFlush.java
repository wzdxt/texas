package com.wzdxt.texas.model.hand;

import com.wzdxt.texas.Constants;
import com.wzdxt.texas.model.Card;
import com.wzdxt.texas.model.CardSet;
import lombok.Getter;

import java.util.Collection;

/**
 * Created by wzdxt on 2017/8/26.
 */
public class RoyalFlush extends AbsHand {
    public static final int SORT = 9;

    @Getter
    protected int suit;

    protected RoyalFlush(Collection<Card> c) {
        super(c);
    }

    @Override
    public int getSort() {
        return 9;
    }

    @Override
    public long getValue() {
        return 0;
    }

    public static RoyalFlush of(Collection<Card> c) {
        RoyalFlush rf = new RoyalFlush(c);
        rf.suit = rf.first().getSuit();
        rf.rank = Constants.RANK_10;
        if (rf.size() != 5) {
            throw new IllegalArgumentException();
        }
        return rf;
    }

    public static RoyalFlush compose(Collection<Card> c) {
        RoyalFlush rf = new RoyalFlush(c);
        int suit = rf.first().getSuit();
        int rank = Constants.RANK_10;
        for (Card card : rf) {
            if (card.getSuit() != suit || card.getRank() != rank++) {
                return null;
            }
        }
        return rf;
    }

    /**
     *
     * @param c can receive any size
     * @return
     */
    public static RoyalFlush compose7(CardSet c) {
        Flush flush = Flush.compose7(c);
        if (flush != null && flush.rank == Constants.RANK_10) {
            return RoyalFlush.of(flush);
        }
        return null;
    }
}
