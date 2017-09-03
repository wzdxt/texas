package com.wzdxt.texas.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wzdxt on 2017/9/2.
 */
public class C {
    public static Map<Tuple<Integer, Integer>, Integer> cache = new HashMap<>();

    public static int C(int all, int select) {
        if (cache.containsKey(Tuple.of(all, select)))
            return cache.get(Tuple.of(all, select));
        int ret = 1;
        for (int i = 1; i <= select; i++) {
            ret = ret * (all - i + 1) / i;
        }
        return ret;
    }
}
