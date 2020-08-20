package com.iamyours.reader.run;

import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.execution.configurations.ConfigurationTypeBase;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.openapi.project.Project;
import icons.ReaderIcons;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class FishConfigType implements ConfigurationType {
    final ConfigurationFactory factory = new Factory(this);

    private static final FishConfigType instance = new FishConfigType();

    public static FishConfigType getInstance() {
        return instance;
    }

    @Override
    public String getDisplayName() {
        return "Fish";
    }

    @Override
    public String getConfigurationTypeDescription() {
        return "Fish run configuration";
    }

    @Override
    public Icon getIcon() {
        return ReaderIcons.LOGO;
    }

    @Override
    public @NotNull String getId() {
        return "FISH_RUN_CONFIGURATION";
    }

    @Override
    public ConfigurationFactory[] getConfigurationFactories() {
        return new ConfigurationFactory[]{factory};
    }

    static class Factory extends ConfigurationFactory {
        protected Factory(@NotNull FishConfigType type) {
            super(type);
        }

        @Override
        public @NotNull RunConfiguration createTemplateConfiguration(@NotNull Project project) {
            return new FishRunConfiguration(project, this, "");
        }
    }
}
