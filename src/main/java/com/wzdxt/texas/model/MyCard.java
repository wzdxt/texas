package com.wzdxt.texas.model;

import java.util.Iterator;

/**
 * Created by dai_x on 17-9-29.
 */
public class MyCard extends CardSet {
    /**
     *
     * @return byte[2]
     */
    @Override
    public byte[] serialize() {
        byte[] ret = new byte[2];
        Iterator<Card> iter = this.iterator();
        ret[0] = iter.next().getId();
        ret[1] = iter.next().getId();
        return ret;
    }
}
