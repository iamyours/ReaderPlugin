package com.iamyours.reader.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;

public class RunFishAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        PsiElement element = anActionEvent.getRequiredData(CommonDataKeys.PSI_ELEMENT);
        if (element instanceof PsiMethod) {
            PsiMethod psiMethod = (PsiMethod) element;
            PsiStatement[] statements = psiMethod.getBody().getStatements();
            for (PsiStatement p : statements) {
                if (p instanceof PsiDeclarationStatement) {
                    PsiDeclarationStatement declarationStatement = (PsiDeclarationStatement) p;
                    PsiElement[] eles = declarationStatement.getDeclaredElements();
                    for (PsiElement e : eles) {
                        if (e instanceof PsiLocalVariable) {
                            System.out.println(((PsiLocalVariable) e).getName());
                            PsiLocalVariable var = (PsiLocalVariable) e;
                            PsiExpression exp = var.getInitializer();
                            if (exp instanceof PsiLiteralExpression) {
                                PsiLiteralExpression lit = (PsiLiteralExpression) exp;
                                String text = lit.getText();
                            }
                        }
                    }
                }
            }
        }
        System.out.println("Run Fish:" + element);
    }
}
