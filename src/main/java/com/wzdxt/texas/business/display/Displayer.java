package com.wzdxt.texas.business.display;

import com.wzdxt.texas.business.display.logic.GameWindow;
import com.wzdxt.texas.business.display.operation.OperationEngine;
import com.wzdxt.texas.business.master.MasterDecision;
import com.wzdxt.texas.config.DisplayerConfigure;
import com.wzdxt.texas.model.Card;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * Created by dai_x on 17-9-4.
 */
@Component
@Slf4j
public class Displayer {
    @Autowired
    private AnchorMatcher anchorMatcher;
    @Autowired
    private DisplayerConfigure configure;
    @Autowired
    private ScreenParam screenParam;
    @Autowired
    private PhaseManager phaseManager;
    @Autowired
    private GameWindow window;
    @Autowired
    private TexasPlayer player;

    private int screenWidth, screenHeight;
    private transient boolean scan, autoRun, actOnce;
    private static int errorCnt = 0;

    public Displayer() {
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        screenWidth = size.width;
        screenHeight = size.height;
    }

    public void setScan(boolean b) {
        this.scan = b;
    }

    public void setAutoRun(boolean b) {
        this.autoRun = b;
    }

    public void setActOnce() {
        this.actOnce = true;
    }

    @Async
    public void run() {
//        boolean noticed = false;
        GameStatus lastStatus = null;
        while (true) {
            try {
                if (autoRun || actOnce || scan) {
                    GameStatus.Phase phase = getCurrentPhase();
                    if (phase == GameStatus.Phase.PLAYING) {
                        GameStatus status = getGameStatus();
                        if (status.status == GameStatus.Status.MY_TURN) {
                            if (!status.equals(lastStatus) || autoRun || actOnce) {
                                log.info("手牌: {}", status.getMyCard());
                                log.info("台面: {}", status.getCommonCard());
                                MasterDecision masterDecision = player.askMaster(status);
                                log.info("AI建议: {}", masterDecision);
                                TexasPlayer.FinalAction finalAction = player.makeAction(masterDecision, status);
                                log.info("最终操作: {}", finalAction);
//                                noticed = true;
                                if (autoRun || actOnce) {
                                    try {
                                        player.act(finalAction);
                                        log.info("执行成功！");
                                    } catch (OperationEngine.OperationException e) {
                                        log.error(String.format("[%d] player operation fail. %s", errorCnt++, status), e);
                                        window.save(String.valueOf(errorCnt));
                                    }
                                    actOnce = false;
                                }
                                lastStatus = status;
                            }
                        } else {
//                            noticed = false;
                        }
                    }
                }
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            } catch (Exception e) {
                log.error("Error in Displayer run {} {}", e.toString(), e.getStackTrace());
            }
        }
    }

    public GameStatus.Phase getCurrentPhase() {
        window.refresh();
        return phaseManager.getCurrentPhase();
    }

    public GameStatus getGameStatus() {
        GameStatus.GameStatusBuilder builder = GameStatus.builder();
        GameStatus.Phase phase = phaseManager.getCurrentPhase();
        builder.phase(phase);
        switch (phase) {
        case MAIN_PAGE:
            builder.totalCoin(phaseManager.getTotalCoin());
            return builder.build();
        case WAITING:
        case ROOM_PAGE:
        case NONE:
            return builder.build();
        case PLAYING:
            GameStatus.Status status = phaseManager.getCurrentStatus();
            builder.status(status);
            if (status == GameStatus.Status.MY_TURN) {
                return fulfillGameStatus(builder.build());
            } else {
                return builder.build();
            }
        default:
            throw new RuntimeException("phase error");
        }
    }

    public GameStatus fulfillGameStatus(GameStatus status) {
        int[] playerPools = phaseManager.getPlayerPool();
        boolean[] playerRemain = phaseManager.getPlayerRemain();
        playerRemain[0] = phaseManager.amILive();
        int callNeed = phaseManager.getCallNeed();
        boolean[] playerExist = phaseManager.getPlayerExist();
        int myCoin = phaseManager.getMyCoin();
        List<Card> myCard = phaseManager.getMyCard();
        List<Card> commonCard = phaseManager.getCommonCard();
        int currentTurn = phaseManager.getCurrentTurn();
        int totalPool = phaseManager.getTotalPool();
        int bigBlind = phaseManager.getBigBlind();


        status.setPlayerPools(playerPools);
        status.setPlayerRemain(playerRemain);
        status.setCallNeed(callNeed);
        status.setPlayerExist(playerExist);
        status.setMyCoin(myCoin);
        status.setMyCard(myCard);
        status.setCommonCard(commonCard);
        status.setCurrentTurn(currentTurn);
        status.setTotalPool(totalPool);
        status.setBigBlind(bigBlind);

        return status;
    }

    public void saveScreen() {
        window.save("save-" + System.currentTimeMillis());
    }

    BufferedImage screenCapture(int x1, int y1, int x2, int y2) {
        try {
            BufferedImage screenCapture = new Robot().createScreenCapture(
                    new Rectangle(x1, y1, x2 - x1, y2 - y1)
            );
            return screenCapture;
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }

    Point getMousePos() {
        return MouseInfo.getPointerInfo().getLocation();
    }

    Point getMousePosDelay() {
        int sec = configure.getAnchor().getDelay();
        try {
            log.info("starting get mouse position ...");
            for (int i = 0; i < sec; i++) {
                Thread.sleep(1000);
                log.info("{}", sec - i);
            }
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
        return getMousePos();
    }

    /**
     * Now {@link com.wzdxt.texas.business.display.logic.GameWindow} is not prepared
     *
     * @return
     */
    @Cacheable("screenParam")
    public ScreenParam matchAnchor() {
        Point p = getMousePosDelay();
        int x1 = p.x;
        int y1 = p.y;
        int x2 = x1 + AnchorHolder.getAnchor().getWidth();
        int y2 = y1 + AnchorHolder.getAnchor().getHeight();
        x1 = Math.max(x1 - configure.getAnchor().getFix(), 0);
        y1 = Math.max(y1 - configure.getAnchor().getFix(), 0);
        x2 = Math.min(x2 + configure.getAnchor().getFix(), screenWidth);
        y2 = Math.min(y2 + configure.getAnchor().getFix(), screenHeight);
        BufferedImage screenCapture = screenCapture(x1, y1, x2, y2);
        AnchorMatcher.Result result = anchorMatcher.match(screenCapture);
        if (result.mistake < configure.getAnchor().getAllowMistake()) {
            screenParam.setGameX1(x1 + result.x1);
            screenParam.setGameX2(x1 + result.x2);
            screenParam.setGameY1(y1 + result.y1);
            screenParam.setGameY2(y1 + result.y2);
            screenParam.setWidth(result.x2 - result.x1);
            screenParam.setHeight(result.y2 - result.y1);
            log.debug(String.format("match anchor finished. mistake: %d, %s", result.getMistake(), screenParam));
            return screenParam;
        } else {
            log.debug(String.format("match anchor finished. mistake: %d", result.getMistake()));
            return null;
        }
    }


}
