package ru.danilashamin.routetracker.storage.gateway;


import java.util.Arrays;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import ru.danilashamin.routetracker.logic.entities.LocationPoint;
import ru.danilashamin.routetracker.logic.enums.RouteStatus;
import ru.danilashamin.routetracker.storage.database.AppDatabase;
import ru.danilashamin.routetracker.storage.entities.Route;
import ru.danilashamin.routetracker.storage.entities.Trackpoint;

public final class AdapterDatabase {


    private final AppDatabase database;

    public AdapterDatabase(AppDatabase database) {
        this.database = database;
    }

    public Single<Route> getActiveRouteSingle() {
        List<String> activeRouteStates = Arrays.asList(RouteStatus.ON_ROAD, RouteStatus.PAUSED);
        return database
                .routeDao()
                .getRouteSingleByStates(activeRouteStates)
                .subscribeOn(Schedulers.io());
    }

    public Flowable<Route> subscribeToActiveRoute() {
        List<String> activeRouteStates = Arrays.asList(RouteStatus.ON_ROAD, RouteStatus.PAUSED);
        return database
                .routeDao()
                .getRouteFlowableByStates(activeRouteStates)
                .subscribeOn(Schedulers.io());
    }


    public Single<Route> createRouteAndGet() {
        Route newRoute = Route.createAndStart();
        return database
                .routeDao()
                .add(newRoute)
                .map(id -> newRoute.toBuilder().setId(id).build())
                .subscribeOn(Schedulers.io());
    }

    public Single<Integer> stopRoute(Long routeId) {
        return changeRouteStatus(routeId, RouteStatus.COMPLETED);
    }

    public Single<Integer> pauseRoute(Long routeId) {
        return changeRouteStatus(routeId, RouteStatus.PAUSED);
    }

    public Single<Route> resumeRoute(Long routeId) {
        return database
                .routeDao()
                .getRoute(routeId)
                .map(route -> {
                    route = route.toBuilder().setStatus(RouteStatus.ON_ROAD).build();
                    return route;
                })
                .subscribeOn(Schedulers.io());
    }

    private Single<Integer> changeRouteStatus(Long routeId, @RouteStatus String routeStatus) {
        return database
                .routeDao()
                .getRoute(routeId)
                .flatMap(route -> {
                    route = route.toBuilder().setStatus(routeStatus).build();
                    return database.routeDao().update(route);
                })
                .subscribeOn(Schedulers.io());
    }

    public Single<Long> saveTrackpoint(LocationPoint locationPoint, Long routeId) {
        Trackpoint trackpoint = Trackpoint.from(locationPoint, routeId);
        return database.trackpointDao().add(trackpoint)
                .subscribeOn(Schedulers.io());
    }
}
