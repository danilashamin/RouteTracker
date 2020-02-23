package ru.danilashamin.routetracker.logic.utils;

import android.content.Context;

import ru.danilashamin.routetracker.logic.framework.services.LocationUpdatesService;
import ru.danilashamin.routetracker.storage.entities.Route;

public final class UtilRoutes {

    private final Context context;

    public UtilRoutes(Context context) {
        this.context = context;
    }

    public void startTracking(Route route) {
        LocationUpdatesService.start(context, route);
    }

    public void stopTracking() {
        LocationUpdatesService.stop(context);
    }
}
