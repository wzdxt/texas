package com.wzdxt.texas.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by wzdxt on 2017/9/3.
 */
@Data
@Component
@ConfigurationProperties("texas.master")
public class MasterConfigures {
    CheckConf flop;
    CheckConf turn;
    CheckConf river;

    @Data
    public static class CheckConf {
        int check;
        int[] threshold;
    }
}
