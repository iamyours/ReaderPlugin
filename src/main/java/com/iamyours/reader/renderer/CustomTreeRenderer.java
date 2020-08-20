package com.iamyours.reader.renderer;

import com.intellij.ide.util.treeView.NodeRenderer;
import com.intellij.ui.AnimatedIcon;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class CustomTreeRenderer extends NodeRenderer {
    @Override
    public void customizeCellRenderer(@NotNull JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        super.customizeCellRenderer(tree, value, selected, expanded, leaf, row, hasFocus);
        setIcon(new AnimatedIcon.Default());
    }
}
