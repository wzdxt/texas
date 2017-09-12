package com.wzdxt.texas.application;

import ch.qos.logback.classic.spi.ILoggingEvent;
import com.wzdxt.texas.business.display.Displayer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.text.PlainDocument;

/**
 * Created by dai_x on 17-9-12.
 */
@AllArgsConstructor
@Component
@Slf4j
public class MainFrame extends JFrame {

    private JButton button1;
    private JPanel panel1;
    private JList<String> list1;

    private DefaultListModel<String> logListModel = new DefaultListModel<>();

    @Autowired
    private Displayer displayer;

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

        list1.setModel(logListModel);
    }

    private void bindEvent() {
        button1.addActionListener(e -> displayer.matchAnchorAsync());
    }

    public void addLog(ILoggingEvent e) {
        logListModel.add(logListModel.size(), e.toString());
    }

}
