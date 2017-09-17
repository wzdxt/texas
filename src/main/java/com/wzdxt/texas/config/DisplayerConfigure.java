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
    int scanInterval;
    Anchor anchor;
    Check check;
    Map<String, Phase> phase;
    Map<String, Status> status;
    Map<String, OperationPlan> operation;
    OcrArea ocrArea;
    Other other;


    @Data
    public static class Anchor {
        int fix;    // 边界容错
        int delay;  // 取点延时
        List<Map<String, int[]>> methods;   // 对比方法
        int allowMistake;    // 允许像素误差
    }

    @Data
    public static class Check {
        int rgbMistake;
        int lineStep;
    }

    @Data
    public static class Phase {
        String name;
        CheckOperationList check;
        List<String> next;
        Map<String, OperationPlan> actions;
    }

    public static class OperationPlan extends ArrayList<OperationPlanItem> {
    }

//    public static class OperationPlanItem extends ArrayList<OperationGroup> {
//    }

    @Data
    public static class OperationPlanItem {
        CheckOperationList check;
        ActionOperationList act;
    }

    /**
     * produce {@link com.wzdxt.texas.business.display.operation.AbsCheck}
     */
    public static class CheckOperationList extends ArrayList<CheckOperation> {
    }

    /**
     * produce {@link com.wzdxt.texas.business.display.operation.AbsAction}
     */
    public static class ActionOperationList extends ArrayList<ActionOperation> {
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
        public int[] drag;
    }

    @Data
    public static class OcrArea {
        int[] totalCoin;
        int[][] playerPool;
        int[] myCoin;
        int myCoinLiveColor;
        int myCoinDeadColor;
        int[] callButton;
        int[] blindInfo;
        CardArea[] myCard;
        CardArea[] commonCard;
        int[] totalPool;
    }

    @Data
    public static class CardArea {
        int[] rank;
        int[] suit;
    }

    @Data
    public static class Other {
        int[][] playerRemain;
        int playerRemainColor;
        int[][] turn;
        int[][] playerAbsent;
        int[] playerColor;
    }

    @Data
    public static class Status {
        String name;
        CheckOperationList check;
        String[] next;
    }

}

