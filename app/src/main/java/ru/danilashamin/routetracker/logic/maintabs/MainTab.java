package ru.danilashamin.routetracker.logic.maintabs;

import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@Retention(RetentionPolicy.SOURCE)
@StringDef({MainTab.ROUTES_LIST, MainTab.MAP, MainTab.TIME_RANGE})
public @interface MainTab {
    String MAP = "map";
    String ROUTES_LIST = "routes_list";
    String TIME_RANGE = "time_range";
}


