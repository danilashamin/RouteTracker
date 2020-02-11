package ru.danilashamin.routetracker.storage.entities;

import androidx.core.util.ObjectsCompat;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import org.threeten.bp.LocalDateTime;

import ru.danilashamin.routetracker.storage.config.AppDbConfig;
import ru.danilashamin.routetracker.storage.converters.DateTimeTypeConverter;


@Entity(tableName = AppDbConfig.TRACKPOINT.TABLE_NAME)
public final class Trackpoint {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = AppDbConfig.TRACKPOINT.ID)
    private final long id;

    @ColumnInfo(name = AppDbConfig.TRACKPOINT.LATITUDE)
    private final double latitude;

    @ColumnInfo(name = AppDbConfig.TRACKPOINT.LONGITUDE)
    private final double longitude;

    @TypeConverters(value = DateTimeTypeConverter.class)
    @ColumnInfo(name = AppDbConfig.TRACKPOINT.CREATED_AT)
    private final LocalDateTime createdAt;

    public Trackpoint(long id, double latitude, double longitude, LocalDateTime createdAt) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.createdAt = createdAt;

    }

    public Trackpoint(Builder builder) {
        id = builder.id;
        latitude = builder.latitude;
        longitude = builder.longitude;
        createdAt = builder.createdAt;
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

    @Override
    public int hashCode() {
        return ObjectsCompat.hash(id, latitude, longitude, createdAt);
    }

    public static final class Builder extends BaseBuilder<Trackpoint, Builder> {
        private double latitude;
        private double longitude;
        private LocalDateTime createdAt;


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

        @Override
        Builder self() {
            return this;
        }

        public Trackpoint build() {
            return new Trackpoint(this);
        }
    }
}
