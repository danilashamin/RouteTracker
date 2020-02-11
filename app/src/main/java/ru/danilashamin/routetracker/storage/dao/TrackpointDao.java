package ru.danilashamin.routetracker.storage.dao;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.TypeConverters;



import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import ru.danilashamin.routetracker.storage.config.AppDbConfig;
import ru.danilashamin.routetracker.storage.converters.DateTimeTypeConverter;
import ru.danilashamin.routetracker.storage.entities.Trackpoint;

@Dao
public interface TrackpointDao extends BaseDao<Trackpoint> {

    @Query("SELECT COUNT(*) FROM " + AppDbConfig.TRACKPOINT.TABLE_NAME)
    Flowable<Integer> getTrackpointsCount();

    @TypeConverters(DateTimeTypeConverter.class)
    @Query("SELECT * FROM " + AppDbConfig.TRACKPOINT.TABLE_NAME + " ORDER BY createdAt ASC LIMIT :limit")
    Observable<List<Trackpoint>> getTrackpoints(int limit);

    @TypeConverters(DateTimeTypeConverter.class)
    @Query("SELECT * FROM " + AppDbConfig.TRACKPOINT.TABLE_NAME + " ORDER BY createdAt ASC")
    Observable<List<Trackpoint>> getTrackpointsByOrderServerId();
}
