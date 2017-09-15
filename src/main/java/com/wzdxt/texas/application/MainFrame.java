package com.wzdxt.texas.application;

import ch.qos.logback.classic.spi.ILoggingEvent;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.wzdxt.texas.business.display.Displayer;
import com.wzdxt.texas.business.display.GameStatus;
import com.wzdxt.texas.business.display.ScreenParam;
import com.wzdxt.texas.business.display.logic.GameWindow;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ExecutionException;

/**
 * Created by dai_x on 17-9-12.
 */
@AllArgsConstructor
@Component
@Slf4j
public class MainFrame extends JFrame {

    private JPanel panel1;
    private JButton matchAnchorButton;
    private JEditorPane logPane;
    private JButton currentPhaseButton;
    private JButton gameStatusButton;
    private JRadioButton autoYesButton;
    private JRadioButton autoNoButton;
    private JButton button1;
    private JButton button2;
    private JButton actOnceButton;

    @Autowired
    private Displayer displayer;
    @Autowired
    private GameWindow window;
    @Autowired
    private ApplicationContext ctx;

    public MainFrame() {
        this.add(panel1);
        initUI();
        bindEvent();
    }

    private void initUI() {
        setTitle("Quit button");
        setSize(400, 600);
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
                                currentPhaseButton.setEnabled(true);
                                gameStatusButton.setEnabled(true);
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
                        window.refresh();
                        return displayer.getGameStatus();
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

        actOnceButton.addActionListener(e ->
                new SwingWorker() {
                    @Override
                    public Object doInBackground() {
                        displayer.setActOnce();
                        return null;
                    }
                }
        );

        autoYesButton.addActionListener(e ->
                new SwingWorker() {
                    @Override
                    public Object doInBackground() {
                        displayer.setAutoRun(true);
                        return null;
                    }
                }
        );

        autoNoButton.addActionListener(e -> displayer.setAutoRun(false));


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
        panel1.setLayout(new GridLayoutManager(6, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.setFocusable(false);
        panel1.putClientProperty("html.disable", Boolean.FALSE);
        matchAnchorButton = new JButton();
        matchAnchorButton.setAutoscrolls(false);
        matchAnchorButton.setFocusCycleRoot(true);
        matchAnchorButton.setSelected(false);
        matchAnchorButton.setText("Match Anchor");
        panel1.add(matchAnchorButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, 1, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        panel1.add(scrollPane1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        logPane = new JEditorPane();
        logPane.setEditable(false);
        scrollPane1.setViewportView(logPane);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridBagLayout());
        panel1.add(panel2, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Status"));
        currentPhaseButton = new JButton();
        currentPhaseButton.setEnabled(false);
        currentPhaseButton.setText("Current Phase");
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel2.add(currentPhaseButton, gbc);
        gameStatusButton = new JButton();
        gameStatusButton.setEnabled(false);
        gameStatusButton.setText("Game Status");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel2.add(gameStatusButton, gbc);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel3, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel3.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Action"));
        button1 = new JButton();
        button1.setEnabled(false);
        button1.setText("Button");
        panel3.add(button1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        button2 = new JButton();
        button2.setEnabled(false);
        button2.setText("Button");
        panel3.add(button2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel4, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel4.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Automation"));
        autoYesButton = new JRadioButton();
        autoYesButton.setEnabled(false);
        autoYesButton.setText("Yes");
        panel4.add(autoYesButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 1, false));
        autoNoButton = new JRadioButton();
        autoNoButton.setEnabled(false);
        autoNoButton.setSelected(true);
        autoNoButton.setText("No");
        panel4.add(autoNoButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        actOnceButton = new JButton();
        actOnceButton.setEnabled(false);
        actOnceButton.setText("Act Once");
        panel4.add(actOnceButton, new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        ButtonGroup buttonGroup;
        buttonGroup = new ButtonGroup();
        buttonGroup.add(autoYesButton);
        buttonGroup.add(autoNoButton);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }
}
