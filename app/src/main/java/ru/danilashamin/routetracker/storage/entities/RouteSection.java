package ru.danilashamin.routetracker.storage.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import ru.danilashamin.routetracker.storage.config.AppDbConfig;

@Entity(tableName = AppDbConfig.ROUTE_SECTION.TABLE_NAME, foreignKeys = {
        @ForeignKey(entity = Route.class, parentColumns = AppDbConfig.ROUTE.ID,
                childColumns = AppDbConfig.ROUTE_SECTION.ROUTE_ID)
})
public final class RouteSection {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = AppDbConfig.ROUTE_SECTION.ID)
    private final long id;

    @ColumnInfo(name = AppDbConfig.ROUTE_SECTION.ROUTE_ID)
    private final long routeId;

    public RouteSection(long id, long routeId) {
        this.id = id;
        this.routeId = routeId;
    }

    private RouteSection(Builder builder){
        id = builder.id;
        routeId = builder.routeId;
    }

    public long getId() {
        return id;
    }

    public long getRouteId() {
        return routeId;
    }


    public static class Builder extends BaseBuilder<RouteSection, Builder> {
        private long routeId;


        public Builder setRouteId(long routeId) {
            this.routeId = routeId;
            return this;
        }

        @Override
        Builder self() {
            return this;
        }

        public RouteSection build() {
            return new RouteSection(this);
        }
    }
}
