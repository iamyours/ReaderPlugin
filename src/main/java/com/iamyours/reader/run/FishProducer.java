package com.iamyours.reader.run;

import com.iamyours.reader.model.BookVO;
import com.iamyours.reader.util.PsiUtil;
import com.intellij.execution.actions.ConfigurationContext;
import com.intellij.execution.actions.RunConfigurationProducer;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.openapi.util.Ref;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FishProducer extends RunConfigurationProducer<FishRunConfiguration> {
    public FishProducer() {
        super(new FishConfigType());
    }

    @Override
    protected boolean setupConfigurationFromContext(
            @NotNull FishRunConfiguration config,
            @NotNull ConfigurationContext context,
            @NotNull Ref<PsiElement> ref) {
        final PsiElement element = context.getPsiLocation();
        if (element instanceof PsiJavaToken && element.getParent() instanceof PsiMethod) {
            System.out.println("getInfo:" + element + ",parent:" + element.getParent() + ",gp:" + element.getParent().getParent());
            PsiMethod psiMethod = (PsiMethod) element.getParent();
            String name = psiMethod.getName();
            if (name.startsWith("fish")) {
                config.setRunName(name);
                BookVO bookVO = PsiUtil.createBook(psiMethod);
                config.bookVO = bookVO;
                config.classFile = psiMethod.getContainingFile().getVirtualFile().getPath();
                System.out.println("setupConfigurationFromContext..." + bookVO);
                config.setGeneratedName();
                return true;
            }
        }
        return false;
    }

    private String getMethodKey(PsiMethod psiMethod) {
        PsiClass cls = (PsiClass) psiMethod.getParent();
        return cls.getName() + "." + psiMethod.getName();
    }


    @Override
    public boolean isConfigurationFromContext(
            @NotNull FishRunConfiguration config,
            @NotNull ConfigurationContext context) {
        String name = config.getName();
        System.out.println("isConfigurationFromContext:" + name);
        final PsiElement element = context.getPsiLocation();
        if (element instanceof PsiJavaToken && element.getParent() instanceof PsiMethod) {
            PsiMethod psiMethod = (PsiMethod) element.getParent();
            String methodName = psiMethod.getName();
            if (methodName.startsWith("fish")
                    && methodName.equals(config.getName())) {
                return isInConfigs(context, psiMethod);
            }
        }
        return false;
    }

    private boolean isInConfigs(ConfigurationContext context, PsiMethod psiMethod) {
        List<RunConfiguration> list = context.getRunManager().getAllConfigurationsList();
        String name = psiMethod.getName();
        for (RunConfiguration config : list) {
            if (config instanceof FishRunConfiguration
                    && config.getName().equals(name)) {
                FishRunConfiguration rc = (FishRunConfiguration) config;
                System.out.println("inConfig:" + rc.bookVO);
                rc.bookVO = PsiUtil.createBook(psiMethod);
                rc.classFile = psiMethod.getContainingFile().getVirtualFile().getPath();
                return true;
            }
            ;
        }
        return false;
    }
}
