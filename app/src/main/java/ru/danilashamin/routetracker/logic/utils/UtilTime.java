package ru.danilashamin.routetracker.logic.utils;

import android.content.res.Resources;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import ru.danilashamin.routetracker.R;
import ru.danilashamin.routetracker.application.AppConfig;

public final class UtilTime {

    private final Resources resources;
    private final DateTimeFormatter mainDateTimeFormatter;

    public UtilTime(Resources resources) {
        this.resources = resources;
        mainDateTimeFormatter = DateTimeFormatter.ofPattern("dd LLLL yyyy, HH:mm:ss", AppConfig.Locales.RU);
    }

    @NonNull
    public String formatTime(@Nullable LocalDateTime time) {
        if (time == null) {
            return resources.getString(R.string.undefined);
        }
        return time.format(mainDateTimeFormatter);
    }
}
