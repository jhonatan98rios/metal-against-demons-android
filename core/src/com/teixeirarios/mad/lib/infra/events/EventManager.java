package com.teixeirarios.mad.lib.infra.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventManager {

    private final Map<String, List<EventListener>> listeners = new HashMap<>();
    private static EventManager instance;

    private EventManager() {}

    public static EventManager getInstance() {
        if (instance == null) {
            instance = new EventManager();
        }
        return instance;
    }

    public void on(String event, EventListener listener) {
        List<EventListener> eventListeners = listeners.get(event);
        if (eventListeners == null) {
            eventListeners = new ArrayList<>();
            listeners.put(event, eventListeners);
        }
        eventListeners.add(listener);
    }

    public void emit(String event, Object... args) {
        List<EventListener> callbacks = listeners.get(event);
        if (callbacks != null) {
            for (int i = 0; i < callbacks.size(); i++) {
                EventListener listener = callbacks.get(i);
                listener.handle(args);
            }
        }
    }

    // Interface para definir o tipo de listener
    public interface EventListener {
        void handle(Object... args);
    }
}

