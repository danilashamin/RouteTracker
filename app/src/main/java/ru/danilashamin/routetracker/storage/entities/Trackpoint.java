package ru.danilashamin.routetracker.storage.entities;

import androidx.core.util.ObjectsCompat;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import org.threeten.bp.LocalDateTime;

import ru.danilashamin.routetracker.logic.entities.LocationPoint;
import ru.danilashamin.routetracker.storage.config.AppDbConfig;
import ru.danilashamin.routetracker.storage.converters.DateTimeTypeConverter;

import static androidx.room.ForeignKey.CASCADE;


@Entity(tableName = AppDbConfig.TRACKPOINT.TABLE_NAME, foreignKeys = {
        @ForeignKey(entity = Route.class, parentColumns = AppDbConfig.ROUTE.ID, childColumns = AppDbConfig.TRACKPOINT.ROUTE_ID,
                onDelete = CASCADE),
        @ForeignKey(entity = RouteSection.class, parentColumns = AppDbConfig.ROUTE_SECTION.ID, childColumns = AppDbConfig.TRACKPOINT.ROUTE_SECTION_ID,
        onDelete = CASCADE)
})
public final class Trackpoint {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = AppDbConfig.TRACKPOINT.ID)
    private final long id;

    @ColumnInfo(name = AppDbConfig.TRACKPOINT.LATITUDE)
    private final double latitude;

    @ColumnInfo(name = AppDbConfig.TRACKPOINT.LONGITUDE)
    private final double longitude;

    @ColumnInfo(name = AppDbConfig.TRACKPOINT.ALTITUDE)
    private final double altitude;

    @ColumnInfo(name = AppDbConfig.TRACKPOINT.BEARING)
    private final float bearing;

    @ColumnInfo(name = AppDbConfig.TRACKPOINT.ACCURACY)
    private final float accuracy;

    @ColumnInfo(name = AppDbConfig.TRACKPOINT.SPEED)
    private final float speed;

    @ColumnInfo(name = AppDbConfig.TRACKPOINT.ROUTE_ID)
    private final long routeId;

    @TypeConverters(value = DateTimeTypeConverter.class)
    @ColumnInfo(name = AppDbConfig.TRACKPOINT.CREATED_AT)
    private final LocalDateTime createdAt;

    @ColumnInfo(name = AppDbConfig.TRACKPOINT.ROUTE_SECTION_ID)
    private final long routeSectionId;

    public Trackpoint(long id, double latitude, double longitude, double altitude, float bearing, float accuracy, float speed, long routeId, LocalDateTime createdAt, long routeSectionId) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.bearing = bearing;
        this.accuracy = accuracy;
        this.speed = speed;
        this.routeId = routeId;
        this.createdAt = createdAt;
        this.routeSectionId = routeSectionId;
    }

    private Trackpoint(Builder builder) {
        id = builder.id;
        latitude = builder.latitude;
        longitude = builder.longitude;
        createdAt = builder.createdAt;
        altitude = builder.altitude;
        bearing = builder.bearing;
        accuracy = builder.accuracy;
        speed = builder.speed;
        routeId = builder.routeId;
        routeSectionId = builder.routeSectionId;
    }

    public long getId() {
        return id;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public double getAltitude() {
        return altitude;
    }

    public float getBearing() {
        return bearing;
    }

    public float getAccuracy() {
        return accuracy;
    }

    public float getSpeed() {
        return speed;
    }

    public long getRouteId() {
        return routeId;
    }

    public long getRouteSectionId() {
        return routeSectionId;
    }

    public static Trackpoint from(LocationPoint locationPoint, long routeId) {
        return new Builder()
                .setAccuracy(locationPoint.getAccuracy())
                .setAltitude(locationPoint.getAltitude())
                .setBearing(locationPoint.getBearing())
                .setCreatedAt(locationPoint.getCreatedAt())
                .setLatitude(locationPoint.getLatitude())
                .setLongitude(locationPoint.getLongitude())
                .setSpeed(locationPoint.getSpeed())
                .setRouteId(routeId)
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Trackpoint)) return false;
        Trackpoint that = (Trackpoint) o;
        return id == that.id &&
                Double.compare(that.latitude, latitude) == 0 &&
                Double.compare(that.longitude, longitude) == 0 &&
                Double.compare(that.altitude, altitude) == 0 &&
                Float.compare(that.bearing, bearing) == 0 &&
                Float.compare(that.accuracy, accuracy) == 0 &&
                Float.compare(that.speed, speed) == 0 &&
                routeId == that.routeId &&
                ObjectsCompat.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return ObjectsCompat.hash(id, latitude, longitude, altitude, bearing, accuracy, speed, routeId, createdAt);
    }

    public static final class Builder extends BaseBuilder<Trackpoint, Builder> {
        private long routeId;
        private double latitude;
        private double longitude;
        private double altitude;
        private float bearing;
        private float accuracy;
        private float speed;

        private LocalDateTime createdAt;
        private long routeSectionId;


        public Builder setLatitude(double latitude) {
            this.latitude = latitude;
            return this;
        }

        public Builder setLongitude(double longitude) {
            this.longitude = longitude;
            return this;
        }

        public Builder setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder setRouteId(long routeId) {
            this.routeId = routeId;
            return this;
        }

        public Builder setAltitude(double altitude) {
            this.altitude = altitude;
            return this;
        }

        public Builder setBearing(float bearing) {
            this.bearing = bearing;
            return this;
        }

        public Builder setAccuracy(float accuracy) {
            this.accuracy = accuracy;
            return this;
        }

        public Builder setSpeed(float speed) {
            this.speed = speed;
            return this;
        }

        public Builder setRouteSectionId(long routeSectionId) {
            this.routeSectionId = routeSectionId;
            return this;
        }

        @Override
        Builder self() {
            return this;
        }

        public Trackpoint build() {
            return new Trackpoint(this);
        }
    }
}
