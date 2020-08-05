package com.iamyours.reader;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import org.apache.commons.lang.StringUtils;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


@State(
        name = "PersistentState",
        storages = {@Storage(
                value = "ireader.xml"
        )}
)
public class PersistentState implements PersistentStateComponent<Element> {

    private static PersistentState persistentState;

    private String bookPathText;

    public PersistentState() {
    }

    public static PersistentState getInstance() {
        if (persistentState == null) {
            persistentState = ServiceManager.getService(PersistentState.class);
        }

        return persistentState;
    }


    @Nullable
    @Override
    public Element getState() {
        Element element = new Element("PersistentState");
//        element.setAttribute("bookPath", this.getBookPathText());
        return element;
    }

    @Override
    public void loadState(@NotNull Element state) {
//        this.setBookPathText(state.getAttributeValue("bookPath"));


    }

    @Override
    public void noStateLoaded() {

    }

}