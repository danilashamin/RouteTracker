package ru.danilashamin.routetracker.logic.mvp.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.crashlytics.android.Crashlytics;


import javax.inject.Inject;

import ru.danilashamin.routetracker.application.App;
import ru.danilashamin.routetracker.base.presenter.PresenterBase;
import ru.danilashamin.routetracker.logic.entities.LocationPoint;
import ru.danilashamin.routetracker.logic.framework.PermissionsManager;
import ru.danilashamin.routetracker.logic.location.LocationManager;
import ru.danilashamin.routetracker.logic.mappers.EntitiesMapper;
import ru.danilashamin.routetracker.logic.mvp.views.MapView;
import ru.danilashamin.routetracker.storage.gateway.AdapterDatabase;
import ru.terrakok.cicerone.Router;


@InjectViewState
public final class MapPresenter extends PresenterBase<MapView> {

    @Inject
    PermissionsManager permissionsManager;

    @Inject
    LocationManager locationManager;

    @Inject
    AdapterDatabase adapterDatabase;

    @Inject
    EntitiesMapper mapper;

    private final Router router;

    public MapPresenter(Router router) {
        this.router = router;
        App.getInstance().getAppComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        requestPermissions();
    }

    private void requestPermissions() {
        addDisposable(permissionsManager.requestLocationPermissions()
                .subscribe(permissionRequest -> {
                    if (permissionRequest.isGranted()) {
                        getViewState().loadMap();
                    }
                }, error -> {
                    Crashlytics.logException(error);
                    error.printStackTrace(); //TODO add handling error
                }));
    }

    public void onMapReady() {
        showCurrentLocation();
    }

    private void showCurrentLocation() {
        addDisposable(locationManager.getCurrentLocation()
                .subscribe(location -> getViewState().showCurrentLocation(location),
                        error -> {
                            Crashlytics.logException(error);
                            error.printStackTrace(); //TODO add handling error
                        }));
    }


    public void onBackPressed() {
        router.exit();
    }

    public void onMapClicked(LocationPoint location) {

    }

    // ********************************* INTERACTORS REGION **********************************//




    // ********************************* INTERACTORS END REGION **********************************//


}
