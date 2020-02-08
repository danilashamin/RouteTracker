package ru.danilashamin.routetracker.di.modules;

import android.content.Context;


import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.danilashamin.routetracker.logic.location.LocationManager;

@Module
public final class LocationModule {

    @Singleton
    @Provides
    LocationManager provideLocationManager(Context context) {
        return new LocationManager(context);
    }
}
