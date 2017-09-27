package com.wzdxt.texas.model;

import com.wzdxt.texas.Constants;
import lombok.EqualsAndHashCode;

import java.util.Collection;
import java.util.TreeSet;

/**
 * Created by wzdxt on 2017/8/26.
 */
@EqualsAndHashCode
public class CardSet extends TreeSet<Card> {

    public CardSet(Collection<Card> c) {
        super(c);
    }

    public CardSet() {
        super();
    }



    public long getId() {
//        int idx = 0;
        long ret = 0;
        for (Card card : this.descendingSet()) {
            ret = ret * (Constants.TOTAL_CARD + 1) + card.getId() + 1;
//            ret += Math.pow((Constants.TOTAL_CARD + 1), idx++) * (card.getId() + 1);
        }
        return ret;
    }

    public static CardSet of(long id) {
        CardSet cardSet = new CardSet();
        while (id > 0) {
            cardSet.add(Card.of((int) (id % (Constants.TOTAL_CARD + 1)) - 1));
            id /= (Constants.TOTAL_CARD + 1);
        }
        return cardSet;
    }

}
