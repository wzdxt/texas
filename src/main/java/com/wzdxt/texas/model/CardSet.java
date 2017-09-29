package com.wzdxt.texas.model;

import com.wzdxt.texas.Constants;
import lombok.EqualsAndHashCode;

import java.util.Arrays;
import java.util.BitSet;
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


    /**
     * ONLY FOR 7 CARDS!!
     * @return byte[6] = 42 bit
     */
    public byte[] serialize() {
        long ret = 0;
        for (Card card : this) {
            ret  = (ret << 6) | card.getId();
        }
        BitSet bs = BitSet.valueOf(new long[]{ret});
        return bs.toByteArray();
    }

}
