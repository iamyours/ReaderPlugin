package com.iamyours.reader.run;

import com.iamyours.reader.model.BookVO;
import com.iamyours.reader.util.PsiUtil;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.ui.TextBrowseFolderListener;
import com.intellij.openapi.ui.TextComponentAccessor;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.io.File;

public class FishRunConfigUI extends SettingsEditor<FishRunConfiguration> {

    private TextFieldWithBrowseButton bookFile;
    private JPanel form;

    @Override
    protected void resetEditorFrom(@NotNull FishRunConfiguration config) {
        System.out.println("resetEditorFrom:" + config.bookVO);
        BookVO vo = config.bookVO;
        if (vo == null) return;
        bookFile.setText(vo.bookPath);

    }

    @Override
    protected void applyEditorTo(@NotNull FishRunConfiguration config) throws ConfigurationException {
        System.out.println("applyEditorTo:" + config.bookVO);
        BookVO vo = new BookVO();
        vo.bookPath = bookFile.getText();
        config.bookVO = vo;
        if (config.classFile != null) {
            updateMethod(vo, config);
        }

    }

    private void updateMethod(BookVO vo, FishRunConfiguration config) {
        File file = new File(config.classFile);
        if (!file.exists()) return;
        VirtualFile vf = LocalFileSystem.getInstance().refreshAndFindFileByIoFile(file);
        PsiFile psiFile = PsiManager.getInstance(config.getProject()).findFile(vf);
        psiFile.accept(new JavaRecursiveElementVisitor() {
            @Override
            public void visitElement(@NotNull PsiElement element) {
                super.visitElement(element);
                PsiUtil.updateMethod(element, config);
            }
        });
    }

    @Override
    protected @NotNull JComponent createEditor() {
        bookFile.setVisible(true);
        bookFile.setEditable(true);
        FileChooserDescriptor chooserDescriptor = new FileChooserDescriptor(true, false, false, false, false, false);
        bookFile.addBrowseFolderListener("Open File", null, null,
                chooserDescriptor, TextComponentAccessor.TEXT_FIELD_WHOLE_TEXT);
        return form;
    }
}
