package com.iamyours.reader.run;

import com.iamyours.reader.engin.BookEngine;
import com.iamyours.reader.model.ChapterVO;
import com.iamyours.reader.renderer.CustomTreeRenderer;
import com.iamyours.reader.ui.PanelWithText;
import com.iamyours.reader.util.EventBus;
import com.iamyours.reader.widget.EastToolWindowPanel;
import com.intellij.execution.ui.ExecutionConsole;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.ui.JBSplitter;
import com.intellij.ui.LoadingNode;
import com.intellij.ui.OnePixelSplitter;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.treeStructure.SimpleTree;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER;

/**
 * Run运行界面
 */
public class FishRunConsole implements ExecutionConsole, KeyListener {
    private JTree tree;
    private JTextArea textArea;
    private JComponent component;
    private DefaultMutableTreeNode root;

    @Override
    public @NotNull JComponent getComponent() {
        if (component == null) {
            component = createComponent();
        }
        return component;
    }

    @Override
    public JComponent getPreferredFocusableComponent() {
        return getComponent();
    }

    public void startBuild(FishRunConfiguration config) {
        root.add(new LoadingNode("...."));
        textArea.requestFocus();
    }

    @Override
    public void dispose() {

    }


    private JComponent createComponent() {
        SimpleToolWindowPanel panel = new SimpleToolWindowPanel(true);

        SimpleToolWindowPanel top = new SimpleToolWindowPanel(true);
        tree = new SimpleTree() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                DefaultMutableTreeNode root = (DefaultMutableTreeNode) treeModel.getRoot();
            }
        };
        ChapterVO obj = new ChapterVO(0, "build");
        root = new DefaultMutableTreeNode(obj);
        DefaultTreeModel model = new DefaultTreeModel(root);
        tree.setModel(model);
        tree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                TreePath path = e.getPath();
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
                if (engine != null) {
                    engine.selectChapter((ChapterVO) node.getUserObject());
                    textArea.setText(engine.getCurrentContent());
                }
            }
        });
        removeKeyListener(tree);//移除搜索相关事件
        tree.addKeyListener(this);
        tree.setCellRenderer(new CustomTreeRenderer());
        SimpleToolWindowPanel right = new EastToolWindowPanel(false);
        final ActionManager actionManager = ActionManager.getInstance();
        ActionToolbar actionToolbar = actionManager.createActionToolbar("Reader Toolbar",
                (DefaultActionGroup) actionManager.getAction("reader.TextArea"),
                true);
        actionToolbar.setTargetComponent(textArea);
        right.setToolbar(actionToolbar.getComponent());
        textArea = new JTextArea("press 'N' to next");
        textArea.setMargin(new Insets(5, 5, 5, 5));
        textArea.addKeyListener(this);
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        textArea.setEditable(false);
        JBScrollPane textScrollPane = new JBScrollPane(textArea);
        right.setContent(textScrollPane);
        JBSplitter splitPane = new OnePixelSplitter(false, "test", 0.3f);
        splitPane.setFirstComponent(top);
        splitPane.setSecondComponent(right);
        Color color = new Color(0, 0, 0, 0);
        splitPane.getDivider().setBackground(color);
        JBScrollPane scrollPane = new JBScrollPane(tree);
        scrollPane.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
        top.setContent(scrollPane);
        panel.setContent(splitPane);
        return panel;
    }

    private void removeKeyListener(JComponent component) {
        KeyListener[] listeners = component.getKeyListeners();
        for (KeyListener listener : listeners) {
            component.removeKeyListener(listener);
        }
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {


    }

    private int count;

    @Override
    public void keyReleased(KeyEvent e) {
        String str = e.getKeyChar() + "";
        if ("N".equals(str.toUpperCase())) {
            engine.readNext();
            textArea.setText(engine.getCurrentContent());
        }
    }

    private BookEngine engine;
    private int currentChapterIndex = 0;
    private boolean expanded = true;

    private void selectCurrent() {
        if (!expanded) return;
        tree.setSelectionRow(currentChapterIndex);
        tree.scrollRowToVisible(currentChapterIndex);
    }

    public void loadWithEngine(BookEngine engine) {
        this.engine = engine;
        engine.setChapterListener(new BookEngine.ChapterListener() {
            @Override
            public void chapterChanged(int index) {
                currentChapterIndex = index + 1;
                selectCurrent();
            }
        });
        tree.addTreeExpansionListener(new TreeExpansionListener() {
            @Override
            public void treeExpanded(TreeExpansionEvent event) {
                expanded = true;
                selectCurrent();
            }

            @Override
            public void treeCollapsed(TreeExpansionEvent event) {
                expanded = false;
            }
        });
        EventBus.register(() -> {
            engine.readNext();
            textArea.setText(engine.getCurrentContent());
        });
        root.removeAllChildren();
        for (ChapterVO c : engine.getChapterList()) {
            root.add(new DefaultMutableTreeNode(c));
        }
        tree.expandRow(0);
        tree.updateUI();
    }
}
