package ru.danilashamin.routetracker.storage.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import ru.danilashamin.routetracker.storage.config.AppDbConfig;
import ru.danilashamin.routetracker.storage.dao.TrackpointDao;
import ru.danilashamin.routetracker.storage.entities.Trackpoint;


@Database(entities = {Trackpoint.class}, version = AppDbConfig.APP_DATABASE_VERSION)
public abstract class AppDatabase extends RoomDatabase {

    public abstract TrackpointDao trackpointDao();

}