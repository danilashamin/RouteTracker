package ru.danilashamin.routetracker.storage.dao;

import androidx.room.Dao;

import ru.danilashamin.routetracker.storage.entities.Route;

@Dao
public interface RouteDao extends BaseDao<Route> {
}
