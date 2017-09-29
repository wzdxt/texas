package com.wzdxt.texas.model.hand;

import com.wzdxt.texas.ApplicationContextHolder;
import com.wzdxt.texas.Constants;
import com.wzdxt.texas.model.Card;
import com.wzdxt.texas.model.CardSet;
import com.wzdxt.texas.service.LevelDB;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.List;

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
     * @param id (6)card*5 (4)type
     * @return
     */
    public static AbsHand of(long id) {
        int type = (int) (id & 0xf);
        id >>= 4;
        List<Card> cardList = new ArrayList<>(5);
        for (int i = 0; i < 5; i++) {
            cardList.add(Card.of((byte) (id & 0x3f)));
            id >>= 6;
        }
        switch (type) {
        case HighCard.SORT:
            return HighCard.compose(cardList);
        case OnePair.SORT:
            return OnePair.compose(cardList);
        case TwoPair.SORT:
            return TwoPair.compose(cardList);
        case ThreeRank.SORT:
            return ThreeRank.compose(cardList);
        case Straight.SORT:
            return Straight.compose(cardList);
        case Flush.SORT:
            return Flush.compose(cardList);
        case FullHouse.SORT:
            return FullHouse.compose(cardList);
        case FourRank.SORT:
            return FourRank.compose(cardList);
        case StraightFlush.SORT:
            return StraightFlush.compose(cardList);
        case RoyalFlush.SORT:
            return RoyalFlush.compose(cardList);
        }
        return null;
    }

    /**
     * ONLY FOR 5 CARDS!!!!!
     * @return byte[5] (6)card*5 (4)type
     */
    public byte[] serialize() {
        long ret = 0;
        for (Card card : this) {
            ret = (ret << 6) | card.getId();
        }
        ret = (ret << 4) | this.getSort();
        BitSet bs = BitSet.valueOf(new long[]{ret});
        return bs.toByteArray();
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
//        long targetId = ApplicationContextHolder.get().getBean(LevelDB.class).get7to5(cardSet.serialize());
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
