package ru.danilashamin.routetracker.storage.config;

public final class AppDbConfig {
    private AppDbConfig() {
        throw new AssertionError();
    }

    public static final int APP_DATABASE_VERSION = 1;

    public static final String APP_DATABASE_NAME = "AppDatabase";

    public static final class ORDER {
        private ORDER() {
            throw new AssertionError();
        }

        public static final String TABLE_NAME = "orders";

        public static final String ID = "id";
        public static final String SERVER_ID = "server_id";
        public static final String COST = "cost";
        public static final String LATITUDE_FROM = "latitude_from";
        public static final String LONGITUDE_FROM = "longitude_from";
        public static final String ADDRESS_FROM = "address_from";
        public static final String LATITUDE_TO = "latitude_to";
        public static final String LONGITUDE_TO = "longitude_to";
        public static final String ADDRESS_TO = "address_to";
        public static final String STATUS = "status";
    }

    public static final class USER {
        private USER() {
            throw new AssertionError();
        }

        public static final String TABLE_NAME = "user";

        public static final String ID = "id";
        public static final String FIRST_NAME = "first_name";
        public static final String LAST_NAME = "last_name";
        public static final String MIDDLE_NAME = "middle_name";

        public static final String PHONE = "phone";
        public static final String EMAIL = "email";
        public static final String USERNAME = "username";
        public static final String SERVER_ID = "server_id";
        public static final String COMPANY_ID = "company_id";
        public static final String COMPANY_SERVER_ID = "company_server_id";
        public static final String LAST_TIME_SYNC = "last_time_sync";
    }

    public static final class COMPANY {
        private COMPANY() {
            throw new AssertionError();
        }

        public static final String TABLE_NAME = "company";

        public static final String ID = "id";
        public static final String SERVER_ID = "server_id";
        public static final String NAME = "name";
        public static final String ADDRESS = "address";
        public static final String LAST_TIME_SYNC = "last_time_sync";

    }

    public static final class TRACKPOINT {
        private TRACKPOINT() {
            throw new AssertionError();
        }

        public static final String TABLE_NAME = "trackpoint";

        public static final String ID = "id";

        public static final String LATITUDE = "latitude";
        public static final String LONGITUDE = "longitude";
        public static final String CREATED_AT = "createdAt";
        public static final String ORDER_ID = "order_id";
        public static final String ORDER_SERVER_ID = "order_server_id";
        public static final String ADDRESS = "address";
        public static final String STATUS = "status";
    }

    public static final class ORDER_RATE {
        private ORDER_RATE() {
            throw new AssertionError();
        }

        public static final String TABLE_NAME = "order_rate";

        public static final String ID = "id";
        public static final String SERVER_ID = "server_id";
        public static final String ORDER_SERVER_ID = "order_server_id";
        public static final String ORDER_ID = "order_id";
        public static final String COMMENT = "comment";
        public static final String RATE = "rate";

    }
}
