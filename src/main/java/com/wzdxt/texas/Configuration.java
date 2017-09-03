package com.wzdxt.texas;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by wzdxt on 2017/9/3.
 */
@Component
@ConfigurationProperties("texas.master")
public class Configuration {
    int flopCheck;
    int flopThresholdOne;
    int flopThresholdTwo;
    int flopThresholdThree;
    int turnCheck;
    int turnThresholdOne;
    int turnThresholdTwo;
    int turnThresholdThree;
    int riverCheck;
    int riverThresholdOne;
    int riverThresholdTwo;
    int riverThresholdThree;
}
