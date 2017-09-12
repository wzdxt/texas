package com.wzdxt.texas.application;

import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Created by dai_x on 17-9-12.
 */
@Component
public class CalculatorMainFrame extends JFrame {

    private JButton button1;
    private JPanel panel1;

    public CalculatorMainFrame() {
        this.add(panel1);
        initUI();
        bindEvent();
    }

    private void initUI() {
        setTitle("Quit button");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void bindEvent() {
        
    }

}
