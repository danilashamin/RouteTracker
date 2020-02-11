package ru.danilashamin.routetracker.logic.entities;

import android.location.Location;

import androidx.core.util.ObjectsCompat;

import com.google.android.gms.maps.model.LatLng;

import org.threeten.bp.LocalDateTime;

import javax.inject.Inject;

import ru.danilashamin.routetracker.application.App;
import ru.danilashamin.routetracker.storage.converters.DateTimeTypeConverter;
import ru.danilashamin.routetracker.storage.entities.Trackpoint;

public final class LocationPoint {

    private final double latitude;
    private final double longitude;
    private final double altitude;

    private final float bearing;
    private final float accuracy;
    private final float speed;

    private final LocalDateTime createdAt;

    public LocationPoint(Builder builder) {
        latitude = builder.latitude;
        longitude = builder.longitude;
        createdAt = builder.createdAt;
        bearing = builder.bearing;
        accuracy = builder.accuracy;
        altitude = builder.altitude;
        speed = builder.speed;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
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

    public LatLng toLatLng(){
        return new LatLng(latitude, longitude);
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public static LocationPoint from(Location location) {
        return new Builder()
                .setLatitude(location.getLatitude())
                .setLongitude(location.getLongitude())
                .setCreatedAt(location.getTime())
                .build();
    }

    public static LocationPoint from(LatLng latLng){
        return new Builder()
                .setLatitude(latLng.latitude)
                .setLongitude(latLng.longitude)
                .setCreatedAt(LocalDateTime.now())
                .build();
    }

    public static LocationPoint from(Trackpoint trackpoint){
        return new Builder()
                .setCreatedAt(trackpoint.getCreatedAt())
                .setLongitude(trackpoint.getLongitude())
                .setLatitude(trackpoint.getLatitude())
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LocationPoint)) return false;
        LocationPoint that = (LocationPoint) o;
        return Double.compare(that.latitude, latitude) == 0 &&
                Double.compare(that.longitude, longitude) == 0 &&
                Float.compare(that.bearing, bearing) == 0 &&
                Float.compare(that.accuracy, accuracy) == 0 &&
                Double.compare(that.altitude, altitude) == 0 &&
                Float.compare(that.speed, speed) == 0 &&
                ObjectsCompat.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return ObjectsCompat.hash(latitude, longitude, bearing, accuracy, altitude, speed, createdAt);
    }

    public static class Builder {

        @Inject
        DateTimeTypeConverter dateTimeTypeConverter;

        private double latitude;
        private double longitude;
        private double altitude;

        private float bearing;
        private float accuracy;
        private float speed;

        private LocalDateTime createdAt;

        public Builder(){
            App.getInstance().getAppComponent().inject(this);
        }

        public Builder setLatitude(double latitude) {
            this.latitude = latitude;
            return this;
        }

        public Builder setLongitude(double longitude) {
            this.longitude = longitude;
            return this;
        }

        public Builder setCreatedAt(long createdAt) {
            this.createdAt = dateTimeTypeConverter.fromTimestamp(createdAt);
            return this;
        }

        public Builder setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
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

        public Builder setAltitude(double altitude) {
            this.altitude = altitude;
            return this;
        }

        public Builder setSpeed(float speed) {
            this.speed = speed;
            return this;
        }

        public LocationPoint build() {
            return new LocationPoint(this);
        }
    }
}
