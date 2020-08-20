package com.iamyours.reader.ui;

import com.iamyours.reader.model.ChapterVO;
import com.iamyours.reader.renderer.CustomTreeRenderer;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.actionSystem.impl.ActionToolbarImpl;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.LabeledComponent;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.openapi.ui.TitlePanel;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.ui.JBSplitter;
import com.intellij.ui.LoadingNode;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.components.JBSlidingPanel;
import com.intellij.ui.treeStructure.SimpleTree;
import icons.ReaderIcons;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import java.awt.*;

public class JUnitPanel extends SimpleToolWindowPanel {
    public JUnitPanel(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        super(false, true);
        final ActionManager actionManager = ActionManager.getInstance();
        ActionToolbar actionToolbar = actionManager.createActionToolbar("Reader Toolbar",
                (DefaultActionGroup) actionManager.getAction("reader.JUnitPanel"),
                true);
//        actionToolbar.setTargetComponent();
        setToolbar(actionToolbar.getComponent());
        addTopToolbar();
//        SimpleToolWindowPanel treePanel = new SimpleToolWindowPanel(Boolean.TRUE, Boolean.TRUE);
    }

    private SimpleTree tree;
    private TreeModel model;

    private void addTopToolbar() {
        final ActionManager actionManager = ActionManager.getInstance();
        ActionToolbar actionToolbar = actionManager.createActionToolbar("Reader Toolbar",
                (DefaultActionGroup) actionManager.getAction("reader.JUnitPanelTop"),
                true);
        SimpleToolWindowPanel top = new SimpleToolWindowPanel(true);
        top.setToolbar(actionToolbar.getComponent());
        tree = new SimpleTree();
        actionToolbar.setTargetComponent(tree);
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(new ChapterVO(0, "TestChapter"));
        root.add(new DefaultMutableTreeNode(new ChapterVO(1, "Test2")));
        model = new DefaultTreeModel(root);
        tree.setModel(model);
//        tree.setCellRenderer(new CustomTreeRenderer());
        root.add(new LoadingNode());
        JBScrollPane contentScrollPanel = new JBScrollPane(tree, JBScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JBScrollPane.HORIZONTAL_SCROLLBAR_NEVER);


        SimpleToolWindowPanel right = new SimpleToolWindowPanel(true);
        PanelWithText labeledComponent = new PanelWithText();
        labeledComponent.setText("test");
//        label.setText("<html><font color=red>Test failed 1</font>,passed 1:1 of 2 tests  - 8ms</html>");
//        label.setIcon(ReaderIcons.IGNORED);
//        rightTop.setLayout(new GridLayout(1, 1));
//        rightTop.add(label);
        right.setToolbar(labeledComponent);
        JTextArea textArea = new JTextArea("Test");
        right.setContent(textArea);
        JBSplitter splitPane = new JBSplitter(false);
        splitPane.setFirstComponent(top);
        splitPane.setSecondComponent(right);
        Color color = new Color(0, 0, 0, 0);
        splitPane.getDivider().setBackground(color);
        Dimension minimumSize = new Dimension(60, 50);
        top.setMinimumSize(minimumSize);
        top.setContent(tree);
        setContent(splitPane);
    }
}
