package com.wzdxt.texas.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by dai_x on 17-9-5.
 */
@Data
@Component
@ConfigurationProperties("displayer")
public class DisplayerConfigure {
    Anchor anchor;
    Check check;

    @Data
    public static class Anchor {
        int fix;    // 边界容错
        int delay;  // 取点延时
        List<Map<String, int[]>> methods;   // 对比方法
    }

    @Data
    public static class Check {
        int rgbMistake;
        int lineStep;
    }


}
