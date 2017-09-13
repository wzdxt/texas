package com.wzdxt.texas.application;

import ch.qos.logback.classic.spi.ILoggingEvent;
import com.wzdxt.texas.business.display.Displayer;
import com.wzdxt.texas.business.display.GameStatus;
import com.wzdxt.texas.business.display.PhaseManager;
import com.wzdxt.texas.business.display.ScreenParam;
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

    @Autowired
    private Displayer displayer;
    @Autowired
    private PhaseManager phaseManager;

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
                        return phaseManager.getCurrentPhase();
                    }

                    @Override
                    public void done() {
//                    GameStatus.Phase p = get();
                    }
                }.execute());
    }

    public void addLog(ILoggingEvent e) {
        EventQueue.invokeLater(() ->
                editorPane1.setText(editorPane1.getText() + e.toString() + "\n"));
    }

}
