package com.iamyours.reader.ui;

import com.intellij.xml.util.XmlStringUtil;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelWithText extends JPanel {
    private final JLabel myLabel;

    public PanelWithText() {
        this("");
    }

    public PanelWithText(String text) {
        super(new GridBagLayout());
        this.myLabel = new JLabel();
        this.myLabel.setText(XmlStringUtil.wrapInHtml(text));
        this.add(this.myLabel, new GridBagConstraints(0, 0, 1, 1, 1.0D, 1.0D, 11, 4, new Insets(6, 1, 6, 1), 0, 0));
    }

    public void setText(String text) {
        this.myLabel.setText(XmlStringUtil.wrapInHtml(text));
    }
}