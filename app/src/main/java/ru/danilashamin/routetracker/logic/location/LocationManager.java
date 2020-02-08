package ru.danilashamin.routetracker.logic.location;

import android.content.Context;


import io.nlopez.smartlocation.SmartLocation;
import io.nlopez.smartlocation.location.LocationProvider;
import io.nlopez.smartlocation.location.config.LocationAccuracy;
import io.nlopez.smartlocation.location.config.LocationParams;
import io.nlopez.smartlocation.location.providers.MultiFallbackProvider;
import io.nlopez.smartlocation.rx.ObservableFactory;
import io.reactivex.Observable;
import ru.danilashamin.routetracker.logic.entities.EntityLocation;

import static ru.danilashamin.routetracker.application.AppConfig.Location.LOCATION_DISTANCE;
import static ru.danilashamin.routetracker.application.AppConfig.Location.LOCATION_REQUEST_INTERVAL;

public final class LocationManager {
    private final Context context;

    public LocationManager(Context context) {
        this.context = context;
    }

    public Observable<EntityLocation> getCurrentLocation() {
        return ObservableFactory
                .from(SmartLocation.with(context)
                        .location(buildLocationProvider())
                        .config(buildLocationParams())
                        .oneFix())
                .map(EntityLocation::from);
    }

    public Observable<EntityLocation> startLocationUpdates() {
        return ObservableFactory
                .from(SmartLocation.with(context)
                        .location(buildLocationProvider())
                        .config(buildLocationParams())
                        .continuous())
                .map(EntityLocation::from);
    }

    private LocationParams buildLocationParams(){
        return new LocationParams
                .Builder()
                .setAccuracy(LocationAccuracy.HIGH)
                .setDistance(LOCATION_DISTANCE)
                .setInterval(LOCATION_REQUEST_INTERVAL)
                .build();
    }

    private LocationProvider buildLocationProvider(){
        return new MultiFallbackProvider.Builder()
                .withGooglePlayServicesProvider()
                .withDefaultProvider()
                .build();

    }

}
