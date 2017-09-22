package com.wzdxt.texas.business.display;

import com.wzdxt.texas.business.display.logic.ImageComparator;
import com.wzdxt.texas.business.display.logic.ImageCutter;
import com.wzdxt.texas.business.display.logic.GameWindow;
import com.wzdxt.texas.business.display.logic.RankPixOcr;
import com.wzdxt.texas.business.display.logic.RankTessOcr;
import com.wzdxt.texas.business.display.operation.*;
import com.wzdxt.texas.business.display.util.OcrUtil;
import com.wzdxt.texas.config.DisplayerConfigure;
import com.wzdxt.texas.model.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by wzdxt on 2017/9/9.
 */
@Component
public class PhaseManager {
    @Autowired
    private DisplayerConfigure configure;
    @Autowired
    private GameWindow screen;
    @Autowired
    private ApplicationContext ctx;
    @Autowired
    private ImageCutter imageCutter;
    @Autowired
    private ImageComparator imageComparator;
    @Autowired
//    private RankTessOcr rankOcr;
    private RankPixOcr rankOcr;

    public GameStatus.Phase getCurrentPhase() {
        for (DisplayerConfigure.Phase phase : configure.getPhase().values()) {
            List<AbsCheck> checkList = AbsCheck.fromConfig(ctx, phase.getCheck());
            boolean succ = true;
            for (AbsCheck check : checkList) {
                succ &= check.perform(1);
            }
            if (succ) return GameStatus.Phase.of(phase.getName());
        }

        return GameStatus.Phase.NONE;
    }

    public GameStatus.Status getCurrentStatus() {
        for (DisplayerConfigure.Status statusConf : configure.getStatus().values()) {
            List<AbsCheck> checkList = AbsCheck.fromConfig(ctx, statusConf.getCheck());
            boolean succ = true;
            for (AbsCheck check : checkList) {
                succ &= check.perform(1);
            }
            if (succ) return GameStatus.Status.of(statusConf.getName());
        }
        return GameStatus.Status.FINISH;
    }

    protected String getTotalCoinOcrRes() {
        int[] area = configure.getOcrArea().getTotalCoin();
        return ocr(area[0], area[1], area[2], area[3]);
    }

    public int getTotalCoin() {
        String s = getTotalCoinOcrRes();
        return str2int(s);
    }

    public int[] getPlayerPool() {
        int[] ret = new int[6];
        for (int i = 0; i < 6; i++) {
            int[] area = configure.getOcrArea().getPlayerPool()[i];
            String s = ocr(area[0], area[1], area[2], area[3]);
            ret[i] = s == null ? 0 : str2int(s);
        }
        return ret;
    }

    public boolean[] getPlayerRemain() {
        boolean[] ret = new boolean[6];
        int rgb = configure.getOther().getPlayerRemainColor();
        for (int i = 1; i < 6; i++) {
            int[] point = configure.getOther().getPlayerRemain()[i];
            ret[i] = ctx.getBean(CheckPoint.class).set(point[0], point[1], rgb).perform(1);
        }
        return ret;
    }

    public boolean[] getPlayerExist() {
        boolean[] ret = new boolean[6];
        ret[0] = true;
        int[][] playerAbsent = configure.getOther().getPlayerAbsent();
        for (int i = 1; i < playerAbsent.length; i++) {
            CheckLine check = ctx.getBean(CheckLine.class).set(playerAbsent[i][0], playerAbsent[i][1], playerAbsent[i][2], playerAbsent[i][3], playerAbsent[i][4]);
            ret[i] = !check.perform();
        }
        return ret;
    }

    public boolean amILive() {
        int[] area = configure.getOcrArea().getMyCoin();
        int rgb = configure.getOcrArea().getMyCoinLiveColor();
        CheckContain check = ctx.getBean(CheckContain.class).set(area[0], area[1], area[2], area[3], rgb);
        return check.perform(1);
    }

    public int getMyCoin() {
        int[] area = configure.getOcrArea().getMyCoin();
        String s = ocr(area[0], area[1], area[2], area[3]);
        if (s != null) {
            return str2int(s);
        } else {
            return 0;
        }
    }

    public int getCallNeed() {
        int[] area = configure.getOcrArea().getCallButton();
        String s = ocr(area[0], area[1], area[2], area[3]);
        if (s != null) {
            s = s.replaceAll("[^\\d]", "");
            return s.equals("") ? 0 : str2int(s);
        } else {
            return 0;
        }
    }

    public List<Card> getMyCard() {
        List<Card> cardList = new ArrayList<>(2);
        for (DisplayerConfigure.CardArea cardArea : configure.getOcrArea().getMyCard()) {
            cardList.add(getCardFromArea(cardArea));
        }
        return cardList;
    }

    public List<Card> getCommonCard() {
        List<Card> cardList = new ArrayList<>(2);
        for (DisplayerConfigure.CardArea cardArea : configure.getOcrArea().getCommonCard()) {
            cardList.add(getCardFromArea(cardArea));
        }
        return cardList.stream().filter(Objects::nonNull).collect(Collectors.toList());
    }

    protected Card getCardFromArea(DisplayerConfigure.CardArea area) {
        int[] rankArea = area.getRank();
        int[] suitArea = area.getSuit();
        String rank = ocrRank(rankArea[0], rankArea[1], rankArea[2], rankArea[3]);
        String suit = ocrSuit(suitArea[0], suitArea[1], suitArea[2], suitArea[3]);
        return rank == null && suit == null ? null : Card.of(suit.substring(0, 1) + rank);
    }

    public int getCurrentTurn() {
        int[][] turn = configure.getOther().getTurn();
        for (int i = 0; i < turn.length; i++) {
            CheckSame check = ctx.getBean(CheckSame.class).set(turn[i]);
            if (check.perform()) return i;
        }
        return -1;
    }

    public int getTotalPool() {
        int[] area = configure.getOcrArea().getTotalPool();
        String s = ocr(area[0], area[1], area[2], area[3]);
        if (s != null) {
            s = s.replaceAll("[^\\d\\.万亿]", "");
            return str2int(s);
        } else {
            return 0;
        }
    }

    public int getBigBlind() {
        int[] area = configure.getOcrArea().getBlindInfo();
        String s = ocr(area[0], area[1], area[2], area[3]);
        if (s != null) {
            int p;
            for (p = s.length(); p > 0; p--) {
                if (s.charAt(p - 1) > '9' || s.charAt(p - 1) < '0')
                    break;
            }
            return str2int(s.substring(p));
        } else {
            return 150; // default blind
        }
    }

    protected int str2int(String s) {
        s = s.replaceAll("\\s", "");
        int len = s.length();
        int multi = 1;
        if (len == 0) return 0;
        switch (s.charAt(len - 1)) {
            case '万':
                multi = 1_0000;
                s = s.substring(0, len - 1);
                break;
            case '亿':
                multi = 1_0000_0000;
                s = s.substring(0, len - 1);
                break;
        }
        double d = Double.valueOf(s);
        return (int)Math.round(d * multi);
    }

    protected String ocr(int x1, int y1, int x2, int y2) {
        return ocr(x1, y1, x2, y2, "-l texas -psm 7");
    }

    protected String ocrRank(int x1, int y1, int x2, int y2) {
        BufferedImage bi = screen.capture(x1, y1, x2, y2);
        return rankOcr.ocr(bi);
//        return ocr(x1, y1, x2, y2, "-l texas-rank -psm 10");
    }

    protected String ocrSuit(int x1, int y1, int x2, int y2) {
        String options = "-l texas-suit -psm 10";
        BufferedImage bi = screen.capture(x1, y1, x2, y2);
        bi = imageCutter.cutEdge(bi);
        if (bi != null) {
            imageCutter.cutSuitCorner(bi);
            imageCutter.digCharacterInner(bi);
        }
        return bi == null ? null : OcrUtil.recognize(bi, options);
    }

    protected String ocr(int x1, int y1, int x2, int y2, String options) {
        BufferedImage bi = screen.capture(x1, y1, x2, y2);
        int background = imageComparator.getBackgroundRgb(bi);
        bi = imageCutter.cutEdge(bi);
        if (bi != null)
            imageCutter.digCharacterInner(bi, background);
        return bi == null ? null : OcrUtil.recognize(bi, options);
    }

}

