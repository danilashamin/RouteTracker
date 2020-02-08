package ru.danilashamin.routetracker.storage.converters;

import androidx.room.TypeConverter;

import org.threeten.bp.Instant;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneId;

public final class DateTimeTypeConverter {

    @TypeConverter
    public LocalDateTime fromTimestamp(Long timestamp) {
        return timestamp == null ?
            null : Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    @TypeConverter
    public Long dateToTimestamp(LocalDateTime date) {
        return date == null ?
            null : date.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
}