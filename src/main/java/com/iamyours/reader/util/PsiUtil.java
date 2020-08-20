package com.iamyours.reader.util;

import com.iamyours.reader.model.BookVO;
import com.iamyours.reader.run.FishRunConfiguration;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.codeStyle.JavaCodeStyleManager;
import com.intellij.psi.impl.java.stubs.impl.PsiLiteralStub;
import com.intellij.psi.impl.source.tree.java.PsiLiteralExpressionImpl;

public class PsiUtil {
    public static BookVO createBook(PsiMethod psiMethod) {
        PsiStatement[] statements = psiMethod.getBody().getStatements();
        BookVO bookVO = new BookVO();
        for (PsiStatement p : statements) {
            if (p instanceof PsiDeclarationStatement) {
                PsiDeclarationStatement declarationStatement = (PsiDeclarationStatement) p;
                PsiElement[] eles = declarationStatement.getDeclaredElements();
                for (PsiElement e : eles) {
                    if (e instanceof PsiLocalVariable) {
                        PsiLocalVariable var = (PsiLocalVariable) e;
                        Object value = getValue(var);
                        if ("path".equals(var.getName())) {
                            bookVO.bookPath = value + "";
                        } else if ("chapterIndex".equals(var.getName())) {
                            if (value instanceof Integer)
                                bookVO.chapterIndex = (Integer) value;
                        }
                    }
                }
            }
        }
        return bookVO;
    }

    private static Object getValue(PsiLocalVariable var) {
        PsiExpression exp = var.getInitializer();
        if (exp instanceof PsiLiteralExpression) {
            PsiLiteralExpression lit = (PsiLiteralExpression) exp;
            return lit.getValue();
        }
        return null;
    }

    public static void updateMethod(PsiElement element, FishRunConfiguration config) {
        PsiElementFactory factory = JavaPsiFacade.getInstance(config.getProject())
                .getElementFactory();
        if (element instanceof PsiMethod) {
            PsiMethod psiMethod = (PsiMethod) element;
            System.out.println("method:" + psiMethod.getName() + ",config:" + config.bookVO);
            if (!psiMethod.getName().equals(config.getName())) return;
            PsiStatement[] statements = psiMethod.getBody().getStatements();
            for (PsiStatement p : statements) {
                if (p instanceof PsiDeclarationStatement) {
                    PsiDeclarationStatement declarationStatement = (PsiDeclarationStatement) p;
                    PsiElement[] eles = declarationStatement.getDeclaredElements();
                    for (PsiElement e : eles) {
                        if (e instanceof PsiLocalVariable) {
                            PsiLocalVariable var = (PsiLocalVariable) e;
                            Object value = getValue(var);
                            if ("path".equals(var.getName())) {
                                String path = config.bookVO.bookPath;
                                PsiExpression exp = factory.createExpressionFromText("\"" + path + "\"", null);
                                WriteCommandAction.runWriteCommandAction(config.getProject(), new Runnable() {
                                    @Override
                                    public void run() {
                                        var.getInitializer().replace(exp);
                                    }
                                });
                            } else if ("chapterIndex".equals(var.getName())) {
                            }
                        }
                    }
                }
            }
        }
    }
}
