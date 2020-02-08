package ru.danilashamin.routetracker.logic.utils;

import android.content.res.Resources;

import androidx.annotation.DimenRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

public final class ResourcesUtils {

    private final Resources resources;

    public ResourcesUtils(Resources resources) {
        this.resources = resources;
    }

    public int getDimenPx(@DimenRes int dimenRes) {
        return resources.getDimensionPixelSize(dimenRes);
    }

    public String getString(@StringRes int stringRes){
        return resources.getString(stringRes);
    }

    @NonNull
    public String getString(int id, Object... formatArgs) {
        return resources.getString(id, formatArgs);
    }


}
