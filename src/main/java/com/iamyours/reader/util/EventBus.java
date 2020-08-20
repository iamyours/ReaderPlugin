package com.iamyours.reader.util;

public class EventBus {
    private static Next nextListener;

    public interface Next {
        void onNext();
    }

    public static void register(Next next) {
        nextListener = next;
    }

    public static void postNext() {
        if (nextListener != null)
            nextListener.onNext();
    }
}
