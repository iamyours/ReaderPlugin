package com.iamyours.reader.run;

import com.iamyours.reader.model.BookVO;
import com.iamyours.reader.util.ElementIO;
import com.intellij.execution.*;
import com.intellij.execution.configurations.*;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.InvalidDataException;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FishRunConfiguration extends LocatableConfigurationBase {
    public BookVO bookVO;
    private String runName;
    public String classFile;

    protected FishRunConfiguration(@NotNull Project project, @NotNull ConfigurationFactory factory, @Nullable String name) {
        super(project, factory, name);
    }

    public void setRunName(String name) {
        runName = name;
    }

    @Override
    public @Nullable String suggestedName() {
        return runName;
    }

    @Override
    public @NotNull SettingsEditor<? extends RunConfiguration> getConfigurationEditor() {
        System.out.println("getConfigurationEditor...");
        return new FishRunConfigUI();
    }

    @Override
    public @Nullable RunProfileState getState(@NotNull Executor executor, @NotNull ExecutionEnvironment executionEnvironment) throws ExecutionException {
        System.out.println("getState...");
        return new FishRunState(executionEnvironment, this);
    }


    @Override
    public void writeExternal(@NotNull Element element) {
        super.writeExternal(element);
        System.out.println("writeExternal..." + element);
        if (bookVO == null) return;
        ElementIO.addOption(element, "bookPath", bookVO.bookPath);
        ElementIO.addOption(element, "classFile", classFile);

    }

    @Override
    public void readExternal(@NotNull Element element) throws InvalidDataException {
        super.readExternal(element);
        bookVO = new BookVO();
        String path = ElementIO.readOptions(element).get("bookPath");
        classFile = ElementIO.readOptions(element).get("classFile");
        bookVO.bookPath = path;
        System.out.println("readExternal..." + path);
    }
}
