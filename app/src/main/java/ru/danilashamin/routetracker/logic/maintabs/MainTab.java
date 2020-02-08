package ru.danilashamin.routetracker.logic.maintabs;

import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@Retention(RetentionPolicy.SOURCE)
@StringDef({MainTab.ORDERS_LIST, MainTab.MAP, MainTab.ARCHIVE, MainTab.SYNC, MainTab.PROFILE})
public @interface MainTab {
    String ORDERS_LIST = "orders_list";
    String MAP = "map";
    String ARCHIVE = "archive";
    String SYNC = "sync";
    String PROFILE = "profile";
}


