package ru.danilashamin.routetracker.storage.dao;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;
import ru.danilashamin.routetracker.storage.config.AppDbConfig;
import ru.danilashamin.routetracker.storage.entities.Route;

@Dao
public interface RouteDao extends BaseDao<Route> {

    @Query("SELECT * FROM " + AppDbConfig.ROUTE.TABLE_NAME + " WHERE status IN (:states)")
    Flowable<Route> getRouteFlowableByStates(List<String> states);

    @Query("SELECT * FROM " + AppDbConfig.ROUTE.TABLE_NAME + " WHERE status IN (:states)")
    Single<Route> getRouteSingleByStates(List<String> states);

    @Query("SELECT * FROM " + AppDbConfig.ROUTE.TABLE_NAME + " WHERE id = :id")
    Single<Route> getRoute(Long id);
}
