package ru.danilashamin.routetracker.logic.entities;

import androidx.core.util.ObjectsCompat;

import org.threeten.bp.LocalDateTime;

public final class EntityRouteSection {
    private final long id;
    private final long routeId;
    private final LocalDateTime routeStartTime;

    private EntityRouteSection(Builder builder) {
        id = builder.id;
        routeId = builder.routeId;
        routeStartTime = builder.routeStartTime;
    }

    public long getId() {
        return id;
    }

    public long getRouteId() {
        return routeId;
    }

    public LocalDateTime getRouteStartTime() {
        return routeStartTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EntityRouteSection)) return false;
        EntityRouteSection that = (EntityRouteSection) o;
        return id == that.id &&
                routeId == that.routeId &&
                ObjectsCompat.equals(routeStartTime, that.routeStartTime);
    }

    @Override
    public int hashCode() {
        return ObjectsCompat.hash(id, routeId, routeStartTime);
    }

    public static class Builder {
        private long id;
        private long routeId;
        private LocalDateTime routeStartTime;

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setRouteId(long routeId) {
            this.routeId = routeId;
            return this;
        }

        public Builder setRouteStartTime(LocalDateTime routeStartTime) {
            this.routeStartTime = routeStartTime;
            return this;
        }

        public EntityRouteSection build() {
            return new EntityRouteSection(this);
        }
    }
}
