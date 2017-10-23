package com.wzdxt.texas.model;

import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;

/**
 * Created by dai_x on 17-9-29.
 */
public class CommonCard extends CardSet {
    public CommonCard(Collection<Card> c) {
        super(c);
    }

    public CommonCard() {
    }

    /**
     * ONLY FOR 3-5 CARDS!!!!!
     *
     * @return byte[4] = (6)card * 3-5
     */
    @Override
    public byte[] serialize() {
        long ret = 0;
        for (Card card : this) {
            ret = (ret << 6) | card.getId();
        }
        ret = (ret << 2) | (this.size() - 3);
        BitSet bs = BitSet.valueOf(new long[]{ret});
        return bs.toByteArray();
    }
}
