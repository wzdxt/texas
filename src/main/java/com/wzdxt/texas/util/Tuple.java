package com.wzdxt.texas.util;

import lombok.Data;

/**
 * Created by wzdxt on 2017/9/2.
 */
@Data
public class Tuple<U, V> {
    public U left;
    public V right;

    public static <U, V> Tuple<U, V> of(U l, V r) {
        Tuple<U, V> tuple = new Tuple<>();
        tuple.left = l;
        tuple.right = r;
        return tuple;
    }
}
