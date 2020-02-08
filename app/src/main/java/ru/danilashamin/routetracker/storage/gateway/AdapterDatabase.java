package ru.danilashamin.routetracker.storage.gateway;


import ru.danilashamin.routetracker.storage.database.AppDatabase;

public final class AdapterDatabase {

    public static final int TRACKPOINT_LIMIT = 50;

    private final AppDatabase database;

    public AdapterDatabase(AppDatabase database) {
        this.database = database;
    }


}
