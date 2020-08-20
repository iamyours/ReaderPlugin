package com.iamyours.reader.widget;

import com.intellij.openapi.ui.SimpleToolWindowPanel;

import java.awt.*;

public class EastToolWindowPanel extends SimpleToolWindowPanel {
    public EastToolWindowPanel(boolean vertical) {
        super(vertical);
    }

    public void add(Component comp, Object constraints) {
        if("West".equals(constraints)){
            addImpl(comp, "East", -1);
            return;
        }
        addImpl(comp, constraints, -1);
    }
}
