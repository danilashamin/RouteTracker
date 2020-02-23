package ru.danilashamin.routetracker.logic.entities;

import androidx.core.util.ObjectsCompat;

import org.threeten.bp.LocalDateTime;

import ru.danilashamin.routetracker.logic.enums.RouteStatus;
import ru.danilashamin.routetracker.storage.entities.Route;

public final class EntityRouteControl {

    private final long routeId;

    @RouteStatus
    private final String routeStatus;

    private final LocalDateTime routeStartTime;

    private EntityRouteControl(long routeId, String routeStatus, LocalDateTime routeStartTime) {
        this.routeId = routeId;
        this.routeStatus = routeStatus;
        this.routeStartTime = routeStartTime;
    }

    public long getRouteId() {
        return routeId;
    }

    @RouteStatus
    public String getRouteStatus() {
        return routeStatus;
    }

    public LocalDateTime getRouteStartTime() {
        return routeStartTime;
    }

    public boolean isEmpty() {
        return routeId == 0 || routeStatus == null || routeStartTime == null;
    }

    public boolean isCompleted(){
        return RouteStatus.COMPLETED.equals(routeStatus);
    }

    public static EntityRouteControl from(Route route) {
        return new EntityRouteControl(route.getId(), route.getStatus(), route.getStartedAt());
    }

    public static EntityRouteControl startRouteControl() {
        return new EntityRouteControl(0, null, null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EntityRouteControl)) return false;
        EntityRouteControl that = (EntityRouteControl) o;
        return routeId == that.routeId &&
                ObjectsCompat.equals(routeStatus, that.routeStatus) &&
                ObjectsCompat.equals(routeStartTime, that.routeStartTime);
    }

    @Override
    public int hashCode() {
        return ObjectsCompat.hash(routeId, routeStatus, routeStartTime);
    }
}
