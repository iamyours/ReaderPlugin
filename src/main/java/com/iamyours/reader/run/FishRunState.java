package com.iamyours.reader.run;

import com.iamyours.reader.engin.BookEngine;
import com.intellij.execution.DefaultExecutionResult;
import com.intellij.execution.ExecutionException;
import com.intellij.execution.ExecutionResult;
import com.intellij.execution.Executor;
import com.intellij.execution.configurations.RunProfileState;
import com.intellij.execution.process.*;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.execution.runners.ProgramRunner;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.util.Key;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class FishRunState implements RunProfileState {
    private FishRunConfiguration config;
    private ExecutionEnvironment environment;

    protected FishRunState(ExecutionEnvironment environment, FishRunConfiguration config) {
        this.config = config;
        this.environment = environment;
    }

    private FishRunConsole console;

    private void doRun(ProcessHandler handler, FishRunConfiguration config) {
        ProgressManager.getInstance().run(new Task.Backgroundable(environment.getProject(), "build", true) {
            @Override
            public void run(@NotNull ProgressIndicator progressIndicator) {
                SwingUtilities.invokeLater(() -> {
                    console.startBuild(config);
                });

                BookEngine engine = new BookEngine(config.bookVO);

                SwingUtilities.invokeLater(() -> {
                    console.loadWithEngine(engine);
                });
                handler.destroyProcess();
            }
        });
    }

    @Nullable
    @Override
    public ExecutionResult execute(Executor executor, @NotNull ProgramRunner programRunner) throws ExecutionException {
        System.out.println("execute:" + executor);
        ProcessHandler handler = new NopProcessHandler();
        console = new FishRunConsole();
        DefaultExecutionResult result = new DefaultExecutionResult(console, handler) {
        };
        ProcessTerminatedListener.attach(handler);
        handler.addProcessListener(new ProcessListener() {
            @Override
            public void startNotified(@NotNull ProcessEvent processEvent) {
                System.out.println("startNotified...");
                doRun(handler, config);
            }

            @Override
            public void processTerminated(@NotNull ProcessEvent processEvent) {
                System.out.println("processTerminated...");
            }

            @Override
            public void processWillTerminate(@NotNull ProcessEvent processEvent, boolean b) {

            }

            @Override
            public void onTextAvailable(@NotNull ProcessEvent processEvent, @NotNull Key key) {
                System.out.println("onTextAvailable...");
            }
        });
        return result;
    }
}
