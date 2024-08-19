package com.teixeirarios.mad.lib.analytics;

import android.content.Context;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.teixeirarios.mad.lib.drivers.analytics.AbstractAnalyticsService;

import java.util.Map;

public class AnalyticsServiceJava implements AbstractAnalyticsService {

    private final FirebaseAnalytics firebaseAnalytics;

    public AnalyticsServiceJava(Context context) {
        firebaseAnalytics = FirebaseAnalytics.getInstance(context);
    }

    @Override
    public void logScreenView(String screenName, String screenClass) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, screenName);
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, screenClass);
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle);
    }

    @Override
    public void logCustomEvent(String eventName, Map<String, String> params) {
        Bundle bundle = new Bundle();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            bundle.putString(entry.getKey(), entry.getValue());
        }
        firebaseAnalytics.logEvent(eventName, bundle);
    }
}