package com.wzdxt.texas.config;

import com.wzdxt.texas.business.display.operation.AbsCheck;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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
    Map<String, Phase> phase;

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

    @Data
    public static class Phase {
        String name;
        List<Map<String, int[]>> check;
        List<String> next;
        Map<String, Action> actions;

        private List<AbsCheck> check_ = null;
        public List<AbsCheck> getCheck() {
            if (check_ == null) {
                check_ = new ArrayList<>();
                for (Map<String, int[]> map : chec)
            }
            return check_;
        }
    }

    @Data
    public static class Action {
        Map<String, int[]> preCheck;
        Map<String, int[]> operate;
        Map<String, int[]> postCheck;
    }


}
