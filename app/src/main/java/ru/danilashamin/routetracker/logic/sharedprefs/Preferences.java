package ru.danilashamin.routetracker.logic.sharedprefs;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;


import org.threeten.bp.LocalDateTime;

import ru.danilashamin.routetracker.storage.converters.DateTimeTypeConverter;

public final class Preferences {

    private static final String PREFERENCES_NAME = "preferences";

    private final DateTimeTypeConverter dateTimeTypeConverter;

    private final SharedPreferences preferences;

    public Preferences(DateTimeTypeConverter dateTimeTypeConverter, @NonNull Context context) {
        this.dateTimeTypeConverter = dateTimeTypeConverter;

        preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }


}
