package com.wzdxt.texas.application;

import ch.qos.logback.classic.spi.ILoggingEvent;
import com.wzdxt.texas.business.display.Displayer;
import com.wzdxt.texas.business.display.GameStatus;
import com.wzdxt.texas.business.display.PhaseManager;
import com.wzdxt.texas.business.display.ScreenParam;
import com.wzdxt.texas.business.display.logic.GameWindow;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    private JButton matchAnchorButton;
    private JPanel panel1;
    private JEditorPane editorPane1;
    private JButton currentPhaseButton;
    private JButton gameStatusButton;
    private JRadioButton yesRadioButton;
    private JRadioButton noRadioButton;
    private JButton button1;
    private JButton button2;

    @Autowired
    private Displayer displayer;
    @Autowired
    private GameWindow window;

    public MainFrame() {
        this.add(panel1);
        initUI();
        bindEvent();
    }

    private void initUI() {
        setTitle("Quit button");
        setSize(600, 400);
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
                                new ScreenCaptureConfirmation(sp);
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

    }

    public void addLog(ILoggingEvent e) {
        EventQueue.invokeLater(() ->
                editorPane1.setText(editorPane1.getText() + e.toString() + "\n"));
    }

}
