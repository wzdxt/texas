package com.wzdxt.texas.model.hand;

import com.wzdxt.texas.ApplicationContextHolder;
import com.wzdxt.texas.Constants;
import com.wzdxt.texas.model.Card;
import com.wzdxt.texas.model.CardSet;
import com.wzdxt.texas.service.LevelDB;
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
        int ret = rank;
        for (Card card : this.descendingSet()) {
            if (card.getRank() != rank) {
                ret = ret * Constants.TOTAL_RANK + card.getRank();
            }
        }

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
     * @param cardSet
     * @return
     * @implNote use levelDB for speed up (but very slow!!!)
     */
    public static AbsHand from7(CardSet cardSet) {
//        if (cardSet.size() != 7) {
//            throw new IllegalArgumentException();
//        }
//        long targetId = ApplicationContextHolder.get().getBean(LevelDB.class).get7to5(cardSet.getId());
//        return AbsHand.of(targetId);
        return from7Raw(cardSet);
    }

    /**
     * the original logic
     *
     * @param cardSet
     * @return
     */
    public static AbsHand from7Raw(CardSet cardSet) {
        AbsHand hand;
        if ((hand = Flush.compose7(cardSet)) != null) {
            AbsHand h;
            if ((h = RoyalFlush.compose7(cardSet)) != null) return h;
            if ((h = StraightFlush.compose7(cardSet)) != null) return h;
            return hand;
        }
        if ((hand = ThreeRank.compose7(cardSet)) != null) {
            AbsHand h;
            if ((h = FourRank.compose7(cardSet)) != null) return h;
            if ((h = FullHouse.compose7(cardSet)) != null) return h;
            return hand;
        }
        if ((hand = Straight.compose7(cardSet)) != null) return hand;
        if ((hand = TwoPair.compose7(cardSet)) != null) return hand;
        if ((hand = OnePair.compose7(cardSet)) != null) return hand;
        return HighCard.compose7(cardSet);
    }

}
