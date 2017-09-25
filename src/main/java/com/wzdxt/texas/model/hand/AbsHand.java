package com.wzdxt.texas.model.hand;

import com.wzdxt.texas.Constants;
import com.wzdxt.texas.model.Card;
import com.wzdxt.texas.model.CardSet;
import lombok.Getter;

import java.util.Collection;

/**
 * Created by wzdxt on 2017/8/26.
 */
abstract public class AbsHand extends CardSet implements Comparable<AbsHand> {
    @Getter
    protected int rank;

    protected AbsHand(Collection<Card> c) {
        super(c);
    }


    abstract public int getSort();

    /**
     * for texas comparing
     *
     * @return
     */
    public long getValue() {
        int idx = 0;
        int ret = 0;
        for (Card card : this) {
            if (card.getRank() != rank) {
                ret += Math.pow(Constants.TOTAL_RANK, idx++) * card.getRank();
            }
        }
        ret += Math.pow(Constants.TOTAL_RANK, idx++) * rank;
        return ret;
    }

    /**
     * for texas comparing
     *
     * @param o
     * @return
     */
    @Override
    public int compareTo(AbsHand o) {
        return getSort() != o.getSort() ? getSort() - o.getSort() : (Long.compare(getValue(), o.getValue()));
    }

    /**
     * @param id Card set id
     * @return
     */
    public static AbsHand of(long id) {
        CardSet cardSet = CardSet.of(id);
        AbsHand hand;
        if ((hand = RoyalFlush.compose(cardSet)) != null) return hand;
        if ((hand = StraightFlush.compose(cardSet)) != null) return hand;
        if ((hand = FourRank.compose(cardSet)) != null) return hand;
        if ((hand = FullHouse.compose(cardSet)) != null) return hand;
        if ((hand = Flush.compose(cardSet)) != null) return hand;
        if ((hand = Straight.compose(cardSet)) != null) return hand;
        if ((hand = ThreeRank.compose(cardSet)) != null) return hand;
        if ((hand = TwoPair.compose(cardSet)) != null) return hand;
        if ((hand = OnePair.compose(cardSet)) != null) return hand;
        return HighCard.compose(cardSet);
    }

    /**
     *
     * @param cardSet
     * @return
     */
    public static AbsHand from7(CardSet cardSet) {
        AbsHand hand;
        if ((hand = RoyalFlush.compose7(cardSet)) != null) return hand;
        if ((hand = StraightFlush.compose7(cardSet)) != null) return hand;
        if ((hand = FourRank.compose7(cardSet)) != null) return hand;
        if ((hand = FullHouse.compose7(cardSet)) != null) return hand;
        if ((hand = Flush.compose7(cardSet)) != null) return hand;
        if ((hand = Straight.compose7(cardSet)) != null) return hand;
        if ((hand = ThreeRank.compose7(cardSet)) != null) return hand;
        if ((hand = TwoPair.compose7(cardSet)) != null) return hand;
        if ((hand = OnePair.compose7(cardSet)) != null) return hand;
        return HighCard.compose7(cardSet);
    }

}
