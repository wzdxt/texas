package com.wzdxt.texas.application;

import com.intellij.uiDesigner.core.GridLayoutManager;
import org.jfree.chart.JFreeChart;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Created by dai_x on 17-9-19.
 */
public class ChartDialog extends JDialog {

    public ChartDialog() {
        super();
    }

    public void plot(List<Double> list) {

    }

    public static void main(String[] args) {
        new ChartDialog();
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
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
    }
}