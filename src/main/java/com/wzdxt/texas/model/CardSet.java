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
        int idx = 0;
        int ret = 0;
        for (Card card : this) {
            ret += Math.pow(Constants.TOTAL_CARD, idx++) * card.getId();
        }
        return ret;
    }

}
