package com.wzdxt.texas.config;

import lombok.Data;
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
//    Object status;
//    Object operation;
    OcrArea ocrArea;


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
        CheckGroup check;
        List<String> next;
        Map<String, Action> actions;
    }

    @Data
    public static class Action {
        CheckGroup preCheck;
        ActionGroup operate;
        CheckGroup postCheck;
    }

    /**
     * produce {@link com.wzdxt.texas.business.display.operation.AbsCheck}
     */
    public static class CheckGroup extends ArrayList<CheckOperation> {
    }

    /**
     * produce {@link com.wzdxt.texas.business.display.operation.AbsAction}
     */
    public static class ActionGroup extends ArrayList<ActionOperation> {
    }

    @Data
    public static class CheckOperation {
        public int[] contain;
        public int[] line;
        public int[] lineRange;
        public int[] point;
        public int[] pointRange;
        public int[] same;
    }

    @Data
    public static class ActionOperation {
        public int[] move;
        public int[] click;
    }

    @Data
    public static class OcrArea {
        int[] totalCoin;
    }


}

