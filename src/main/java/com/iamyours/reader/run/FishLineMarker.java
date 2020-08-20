package com.iamyours.reader.run;

import com.intellij.execution.lineMarker.ExecutorAction;
import com.intellij.execution.lineMarker.RunLineMarkerContributor;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiJavaToken;
import com.intellij.psi.PsiMethod;
import com.intellij.util.Function;
import icons.ReaderIcons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class FishLineMarker extends RunLineMarkerContributor {
    public FishLineMarker() {
        super();
        System.out.println("init fish line marker...");
    }


    @Override
    public @Nullable Info getInfo(@NotNull PsiElement element) {
        if (element instanceof PsiJavaToken && element.getParent() instanceof PsiMethod) {
            PsiMethod psiMethod = (PsiMethod) element.getParent();
            String name = psiMethod.getName();
            if (name.startsWith("fish")) {
                final Icon icon = ReaderIcons.LOGO;
                PsiClass psiClass = (PsiClass) psiMethod.getParent();
                String classAndMethod = psiClass + "." + name + "()";
                final Function<PsiElement, String> tooltipProvider =
                        psiElement -> "Run '" + classAndMethod + "'";
                return new RunLineMarkerContributor.Info(icon, tooltipProvider, ExecutorAction.getActions());
            }
        }
        return null;
    }
}
