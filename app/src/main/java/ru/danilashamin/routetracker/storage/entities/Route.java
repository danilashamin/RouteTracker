package ru.danilashamin.routetracker.storage.entities;

import androidx.core.util.ObjectsCompat;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import org.threeten.bp.LocalDateTime;

import ru.danilashamin.routetracker.logic.enums.RouteStatus;
import ru.danilashamin.routetracker.storage.config.AppDbConfig;
import ru.danilashamin.routetracker.storage.converters.DateTimeTypeConverter;

@Entity(tableName = AppDbConfig.ROUTE.TABLE_NAME)
public final class Route {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = AppDbConfig.ROUTE.ID)
    private final long id;

    @TypeConverters(DateTimeTypeConverter.class)
    @ColumnInfo(name = AppDbConfig.ROUTE.STARTED_AT)
    private final LocalDateTime startedAt;

    @TypeConverters(DateTimeTypeConverter.class)
    @ColumnInfo(name = AppDbConfig.ROUTE.FINISHED_AT)
    private final LocalDateTime finishedAt;

    @RouteStatus
    @ColumnInfo(name = AppDbConfig.ROUTE.STATUS)
    private final String status;

    public Route(long id, LocalDateTime startedAt, LocalDateTime finishedAt, String status) {
        this.id = id;
        this.startedAt = startedAt;
        this.finishedAt = finishedAt;
        this.status = status;
    }

    private Route(Builder builder){
        id = builder.id;
        startedAt = builder.startedAt;
        finishedAt = builder.finishedAt;
        status = builder.status;
    }

    public long getId() {
        return id;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public LocalDateTime getFinishedAt() {
        return finishedAt;
    }

    @RouteStatus
    public String getStatus() {
        return status;
    }

    public Builder toBuilder(){
        return new Builder()
                .setStatus(status)
                .setStartedAt(startedAt)
                .setFinishedAt(finishedAt)
                .setId(id);
    }
    public static Route createAndStart(){
        return new Builder()
                .setStartedAt(LocalDateTime.now())
                .setStatus(RouteStatus.ON_ROAD)
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Route)) return false;
        Route route = (Route) o;
        return id == route.id &&
                ObjectsCompat.equals(startedAt, route.startedAt) &&
                ObjectsCompat.equals(finishedAt, route.finishedAt) &&
                ObjectsCompat.equals(status, route.status);
    }

    @Override
    public int hashCode() {
        return ObjectsCompat.hash(id, startedAt, finishedAt, status);
    }

    public static class Builder extends BaseBuilder<Route, Builder> {
        private LocalDateTime startedAt;
        private LocalDateTime finishedAt;

        @RouteStatus
        private String status;

        public Builder setStartedAt(LocalDateTime startedAt) {
            this.startedAt = startedAt;
            return this;
        }

        public Builder setFinishedAt(LocalDateTime finishedAt) {
            this.finishedAt = finishedAt;
            return this;
        }

        public Builder setStatus(@RouteStatus String status) {
            this.status = status;
            return this;
        }

        @Override
        Builder self() {
            return this;
        }

        public Route build() {
            return new Route(this);
        }
    }
}
