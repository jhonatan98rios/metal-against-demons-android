package com.teixeirarios.mad.lib.drivers.analytics;

public interface AbstractAnalyticsService {
    void logScreenView(String screenName, String screenClass);
    void logCustomEvent(String eventName, java.util.Map<String, String> params);
}