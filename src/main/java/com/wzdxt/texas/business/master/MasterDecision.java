package com.wzdxt.texas.business.master;

import lombok.AllArgsConstructor;

/**
 * Created by wzdxt on 2017/9/3.
 */
@AllArgsConstructor
public enum MasterDecision {
    CHECK_OR_FOLD(0, 0),
    CALL_2(0, 2),
    CALL_5(0, 5),
    BET_2_5(2, 5),
    CALL_10(0, 10),
    BET_2_10(2, 10),
    BET_5_25(5, 25),
    BET_10_50(10, 10000),
    BET_25_50(25, 10000),
    CALL_ANY(5, 10000),
    ALL_IN(10000, 10000);

    private int low, high;

    public static MasterDecision of(String name) {
        return MasterDecision.valueOf(name.toUpperCase());
    }
}
