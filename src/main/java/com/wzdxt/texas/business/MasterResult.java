package com.wzdxt.texas.business;

import lombok.AllArgsConstructor;

/**
 * Created by wzdxt on 2017/9/3.
 */
public enum MasterResult {
    NO_OP,
    CALL_ONE,       // 一倍以内跟
    CALL_TWO,
    CALL_THREE,
    CALL_FIVE,
    CALL_TEN,
    BET_TWO_FIVE,   // 2-5倍加注
    BET_FIVE_TEN,
    ALL_IN,
}
