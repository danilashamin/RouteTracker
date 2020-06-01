package ru.danilashamin.routetracker.storage.config;

public final class AppDbConfig {
    private AppDbConfig() {
        throw new AssertionError();
    }

    public static final int APP_DATABASE_VERSION = 1;

    public static final String APP_DATABASE_NAME = "AppDatabase";

    public static final class TRACKPOINT {

        private TRACKPOINT() {
            throw new AssertionError();
        }

        public static final String TABLE_NAME = "trackpoint";

        public static final String ID = "id";

        public static final String LATITUDE = "latitude";
        public static final String LONGITUDE = "longitude";
        public static final String CREATED_AT = "created_at";
        public static final String ALTITUDE = "altitude";
        public static final String BEARING = "bearing";
        public static final String ACCURACY = "accuracy";
        public static final String SPEED = "speed";
        public static final String ROUTE_ID = "route_id";
        public static final String ROUTE_SECTION_ID = "route_section_id";
    }


    public static final class ROUTE {
        private ROUTE() {
            throw new AssertionError();
        }

        public static final String TABLE_NAME = "route";

        public static final String ID = "id";

        public static final String STARTED_AT = "started_at";
        public static final String FINISHED_AT = "finished_at";
        public static final String STATUS = "status";
    }

    public static final class ROUTE_SECTION {
        private ROUTE_SECTION() {
            throw new AssertionError();
        }

        public static final String TABLE_NAME = "route_section";
        public static final String ID = "id";

        public static final String ROUTE_ID = "route_id";
    }

}
