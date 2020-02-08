package ru.danilashamin.routetracker.application;

import java.util.Locale;

public final class AppConfig {
    private AppConfig() {
        throw new AssertionError();
    }

    public static class Locales {
        public static final Locale RU = new Locale("ru", "RU");
    }

    public static class Location {
        public static final float LOCATION_DISTANCE = 0.0F;

        public static final long LOCATION_REQUEST_INTERVAL_5_SEC = 5 * 1000;
        public static final long LOCATION_REQUEST_INTERVAL_10_SEC = 5 * 1000;
        public static final long LOCATION_REQUEST_INTERVAL_1_MINS = 60 * 1000;
        public static final long LOCATION_REQUEST_INTERVAL_2_MINS = 120 * 1000;

        public static final long LOCATION_REQUEST_INTERVAL = LOCATION_REQUEST_INTERVAL_10_SEC;

    }
}
