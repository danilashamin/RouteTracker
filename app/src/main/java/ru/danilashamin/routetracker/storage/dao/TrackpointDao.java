package ru.danilashamin.routetracker.storage.dao;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.TypeConverters;

import com.elogroup.tracker.storage.config.AppDbConfig;
import com.elogroup.tracker.storage.converters.DateTimeTypeConverter;
import com.elogroup.tracker.storage.entities.Trackpoint;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;

@Dao
public interface TrackpointDao extends BaseDao<Trackpoint> {

    @Query("SELECT COUNT(*) FROM " + AppDbConfig.TRACKPOINT.TABLE_NAME)
    Flowable<Integer> getTrackpointsCount();

    @TypeConverters(DateTimeTypeConverter.class)
    @Query("SELECT * FROM " + AppDbConfig.TRACKPOINT.TABLE_NAME + " ORDER BY createdAt ASC LIMIT :limit")
    Observable<List<Trackpoint>> getTrackpoints(int limit);

    @TypeConverters(DateTimeTypeConverter.class)
    @Query("SELECT * FROM " + AppDbConfig.TRACKPOINT.TABLE_NAME + " WHERE order_server_id = :orderServerId ORDER BY createdAt ASC")
    Observable<List<Trackpoint>> getTrackpointsByOrderServerId(String orderServerId);
}
