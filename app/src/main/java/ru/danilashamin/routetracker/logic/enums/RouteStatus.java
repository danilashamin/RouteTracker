package ru.danilashamin.routetracker.logic.enums;

import androidx.annotation.StringDef;

@StringDef(value = {RouteStatus.ON_ROAD, RouteStatus.PAUSED, RouteStatus.COMPLETED})
public @interface RouteStatus {
    String ON_ROAD = "ON_ROAD";
    String PAUSED = "PAUSED";
    String COMPLETED = "COMPLETED";
}
