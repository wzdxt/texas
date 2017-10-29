package com.wzdxt.texas.application;

import ch.qos.logback.classic.spi.ILoggingEvent;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.wzdxt.texas.business.display.Displayer;
import com.wzdxt.texas.business.display.GameStatus;
import com.wzdxt.texas.business.display.ScreenParam;
import com.wzdxt.texas.business.display.TexasPlayer;
import com.wzdxt.texas.business.display.logic.GameWindow;
import com.wzdxt.texas.business.display.operation.ActionClick;
import com.wzdxt.texas.business.display.operation.ActionKey;
import com.wzdxt.texas.business.display.operation.OperationEngine;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.List;

/**
 * Created by dai_x on 17-9-12.
 */
@AllArgsConstructor
@Component
@Slf4j
public class MainFrame extends JFrame {

    // root
    private JPanel panel1;
    private JButton matchAnchorButton;
    private JEditorPane logPane;
    // status
    private JButton currentPhaseButton;
    private JButton gameStatusButton;
    // auto
    private JRadioButton autoYesButton;
    private JRadioButton autoNoButton;
    private JRadioButton scanYesButton;
    private JRadioButton scanNoButton;
    private JButton actOnceButton;
    // action
    private JButton checkCallButton;
    private JButton foldButton;
    private JButton raiseButton;
    private JButton bet5xButton;
    private JButton bet10xButton;
    private JButton bet25xButton;
    private JButton bet50xButton;
    private JButton allInButton;
    private JButton saveButton;
    //
    private JButton clearLogButton;
    private JButton testButton;

    private final List<JComponent> componentList;

    @Autowired
    private Displayer displayer;
    @Autowired
    private GameWindow window;
    @Autowired
    private ApplicationContext ctx;
    @Autowired
    private TexasPlayer player;

    public MainFrame() {
        this.add(panel1);
        initUI();
        bindEvent();

        componentList = Arrays.asList(currentPhaseButton, gameStatusButton,
                autoYesButton, autoNoButton, scanYesButton, scanNoButton, actOnceButton, saveButton,
                checkCallButton, foldButton, raiseButton);
    }

    private void initUI() {
        setTitle("Quit button");
        setSize(350, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

    private void bindEvent() {
        matchAnchorButton.addActionListener(e ->
                new SwingWorker<ScreenParam, Void>() {
                    @Override
                    public ScreenParam doInBackground() {
                        return displayer.matchAnchor();
                    }

                    @Override
                    public void done() {
                        try {
                            ScreenParam sp = get();
                            if (sp != null) {
                                log.info(String.format("match anchor finished. %s", sp));
                                ctx.getBean(ScreenCaptureConfirmation.class).showScreenCapture(sp);
                                componentList.forEach(c -> c.setEnabled(true));
                            }
                        } catch (InterruptedException | ExecutionException e1) {
                            e1.printStackTrace();
                        }
                    }
                }.execute());

        currentPhaseButton.addActionListener(e ->
                new SwingWorker<GameStatus.Phase, Void>() {
                    @Override
                    public GameStatus.Phase doInBackground() {
                        window.refresh();
                        return displayer.getCurrentPhase();
                    }

                    @Override
                    public void done() {
                        try {
                            GameStatus.Phase p = get();
                            log.info(p.toString());
                        } catch (InterruptedException | ExecutionException e1) {
                            e1.printStackTrace();
                        }
                    }
                }.execute());

        gameStatusButton.addActionListener(e ->
                new SwingWorker<GameStatus, Void>() {
                    @Override
                    public GameStatus doInBackground() {
                        try {
                            window.refresh();
                            return displayer.getGameStatus();
                        } catch (Exception e) {
                            log.error("Error in game status button {} {}", e.toString(), e.getStackTrace());
                            throw e;
                        }
                    }

                    @Override
                    public void done() {
                        try {
                            GameStatus s = get();
                            log.info(s.toString());
                        } catch (InterruptedException | ExecutionException e1) {
                            e1.printStackTrace();
                        }
                    }
                }.execute());

        actOnceButton.addActionListener(e -> displayer.setActOnce());

        autoYesButton.addActionListener(e -> displayer.setAutoRun(true));

        autoNoButton.addActionListener(e -> displayer.setAutoRun(false));

        scanYesButton.addActionListener(e -> displayer.setScan(true));

        scanNoButton.addActionListener(e -> displayer.setScan(false));

        saveButton.addActionListener(e -> displayer.saveScreen());

        clearLogButton.addActionListener(e -> EventQueue.invokeLater(() -> logPane.setText("")));

        checkCallButton.addActionListener(e -> performAction(TexasPlayer.FinalAction.CHECK_OR_CALL));
        foldButton.addActionListener(e -> performAction(TexasPlayer.FinalAction.FOLD));
        raiseButton.addActionListener(e -> performAction(TexasPlayer.FinalAction.RAISE_DOUBLE));
        allInButton.addActionListener(e -> performAction(TexasPlayer.FinalAction.ALL_IN));
        bet5xButton.addActionListener(e -> performAction(TexasPlayer.FinalAction.BET_5));
        bet10xButton.addActionListener(e -> performAction(TexasPlayer.FinalAction.BET_10));
        bet25xButton.addActionListener(e -> performAction(TexasPlayer.FinalAction.BET_25));
        bet50xButton.addActionListener(e -> performAction(TexasPlayer.FinalAction.BET_50));

        testButton.addActionListener(e -> new SwingWorker<Object, Void>() {
            @Override
            public Object doInBackground() throws Exception {
                Thread.sleep(1000);
                ctx.getBean(ActionKey.class).set('A').perform();
                Thread.sleep(1000);
                ctx.getBean(ActionKey.class).set('A').perform();
                Thread.sleep(1000);
                ctx.getBean(ActionKey.class).set('A').perform();
//                Thread.sleep(1000);
//                ctx.getBean(ActionClick.class).set(0, 0).perform();
//                log.info("clicked");
//                Thread.sleep(1000);
//                ctx.getBean(ActionClick.class).set(0, 0).perform();
//                log.info("clicked");
//                Thread.sleep(1000);
//                ctx.getBean(ActionClick.class).set(0, 0).perform();
//                log.info("clicked");
                return null;
            }

            @Override
            public void done() {
                try {
                    get();
                    log.info("done");
                } catch (InterruptedException | ExecutionException e1) {
                    e1.printStackTrace();
                }
            }
        }.execute());
    }

    private void performAction(TexasPlayer.FinalAction action) {
        try {
            player.act(action);
        } catch (OperationEngine.OperationException e) {
            e.printStackTrace();
            log.error("{}:{}", e.toString(), e.getStackTrace());
        }
    }

    public void addLog(ILoggingEvent e) {
        EventQueue.invokeLater(() ->
                logPane.setText(logPane.getText() + e.toString() + "\n"));
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(8, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.setFocusable(false);
        panel1.putClientProperty("html.disable", Boolean.FALSE);
        matchAnchorButton = new JButton();
        matchAnchorButton.setAutoscrolls(false);
        matchAnchorButton.setFocusCycleRoot(true);
        matchAnchorButton.setSelected(false);
        matchAnchorButton.setText("Match Anchor");
        panel1.add(matchAnchorButton, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, 1, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        panel1.add(scrollPane1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        logPane = new JEditorPane();
        logPane.setEditable(true);
        scrollPane1.setViewportView(logPane);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel2, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panel2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Status"));
        currentPhaseButton = new JButton();
        currentPhaseButton.setEnabled(false);
        currentPhaseButton.setText("Current Phase");
        panel2.add(currentPhaseButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, 1, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        gameStatusButton = new JButton();
        gameStatusButton.setEnabled(false);
        gameStatusButton.setText("Game Status");
        panel2.add(gameStatusButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, 1, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(4, 2, new Insets(0, 0, 0, 0), -1, -1, true, true));
        panel1.add(panel3, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panel3.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Action"));
        checkCallButton = new JButton();
        checkCallButton.setEnabled(false);
        checkCallButton.setText("Check/Call");
        panel3.add(checkCallButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(126, 34), null, 0, false));
        foldButton = new JButton();
        foldButton.setEnabled(false);
        foldButton.setText("Fold");
        panel3.add(foldButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(126, 34), null, 0, false));
        raiseButton = new JButton();
        raiseButton.setEnabled(false);
        raiseButton.setText("Raise");
        panel3.add(raiseButton, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(126, 34), null, 0, false));
        bet5xButton = new JButton();
        bet5xButton.setEnabled(false);
        bet5xButton.setText("Bet 5x");
        panel3.add(bet5xButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        bet10xButton = new JButton();
        bet10xButton.setEnabled(false);
        bet10xButton.setText("Bet 10x");
        panel3.add(bet10xButton, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        bet25xButton = new JButton();
        bet25xButton.setEnabled(false);
        bet25xButton.setText("Bet 25x");
        panel3.add(bet25xButton, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        bet50xButton = new JButton();
        bet50xButton.setEnabled(false);
        bet50xButton.setText("Bet 50x");
        panel3.add(bet50xButton, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        allInButton = new JButton();
        allInButton.setEnabled(false);
        allInButton.setText("All in");
        panel3.add(allInButton, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(126, 34), null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(3, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel4, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panel4.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Automation"));
        autoYesButton = new JRadioButton();
        autoYesButton.setEnabled(false);
        autoYesButton.setText("Yes");
        panel4.add(autoYesButton, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 1, false));
        autoNoButton = new JRadioButton();
        autoNoButton.setEnabled(false);
        autoNoButton.setSelected(true);
        autoNoButton.setText("No");
        panel4.add(autoNoButton, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        actOnceButton = new JButton();
        actOnceButton.setEnabled(false);
        actOnceButton.setText("Act Once");
        panel4.add(actOnceButton, new GridConstraints(2, 1, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Run");
        panel4.add(label1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Scan");
        panel4.add(label2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        scanYesButton = new JRadioButton();
        scanYesButton.setEnabled(false);
        scanYesButton.setText("Yes");
        panel4.add(scanYesButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 1, false));
        scanNoButton = new JRadioButton();
        scanNoButton.setEnabled(false);
        scanNoButton.setSelected(true);
        scanNoButton.setText("No");
        panel4.add(scanNoButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        saveButton = new JButton();
        saveButton.setEnabled(false);
        saveButton.setText("Save");
        panel4.add(saveButton, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        clearLogButton = new JButton();
        clearLogButton.setText("Clear Log");
        panel1.add(clearLogButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        testButton = new JButton();
        testButton.setText("Test");
        panel1.add(testButton, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        ButtonGroup buttonGroup;
        buttonGroup = new ButtonGroup();
        buttonGroup.add(autoYesButton);
        buttonGroup.add(autoNoButton);
        buttonGroup = new ButtonGroup();
        buttonGroup.add(scanYesButton);
        buttonGroup.add(scanNoButton);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }
}
