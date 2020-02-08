package ru.danilashamin.routetracker.logic.entities;

import android.location.Location;

import androidx.core.util.ObjectsCompat;

import com.elogroup.tracker.application.App;
import com.elogroup.tracker.storage.converters.DateTimeTypeConverter;
import com.elogroup.tracker.storage.entities.Trackpoint;
import com.google.android.gms.maps.model.LatLng;

import org.threeten.bp.LocalDateTime;

import javax.inject.Inject;

public final class EntityLocation {

    private final double latitude;
    private final double longitude;
    private final LocalDateTime createdAt;

    public EntityLocation(Builder builder) {
        latitude = builder.latitude;
        longitude = builder.longitude;
        createdAt = builder.createdAt;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public LatLng toLatLng(){
        return new LatLng(latitude, longitude);
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public static EntityLocation from(Location location) {
        return new Builder()
                .setLatitude(location.getLatitude())
                .setLongitude(location.getLongitude())
                .setCreatedAt(location.getTime())
                .build();
    }

    public static EntityLocation from(Trackpoint trackpoint){
        return new Builder()
                .setCreatedAt(trackpoint.getCreatedAt())
                .setLongitude(trackpoint.getLongitude())
                .setLatitude(trackpoint.getLatitude())
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EntityLocation)) return false;
        EntityLocation that = (EntityLocation) o;
        return Double.compare(that.latitude, latitude) == 0 &&
                Double.compare(that.longitude, longitude) == 0 &&
                ObjectsCompat.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return ObjectsCompat.hash(latitude, longitude, createdAt);
    }

    public static class Builder {

        @Inject
        DateTimeTypeConverter dateTimeTypeConverter;

        private double latitude;
        private double longitude;
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

        public EntityLocation build() {
            return new EntityLocation(this);
        }
    }
}
