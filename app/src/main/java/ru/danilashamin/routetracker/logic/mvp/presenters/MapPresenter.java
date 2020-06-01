package ru.danilashamin.routetracker.logic.mvp.presenters;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.crashlytics.android.Crashlytics;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.danilashamin.routetracker.application.App;
import ru.danilashamin.routetracker.base.presenter.PresenterBase;
import ru.danilashamin.routetracker.logic.entities.EntityRouteControl;
import ru.danilashamin.routetracker.logic.entities.LocationPoint;
import ru.danilashamin.routetracker.logic.framework.PermissionsManager;
import ru.danilashamin.routetracker.logic.location.LocationManager;
import ru.danilashamin.routetracker.logic.mappers.EntitiesMapper;
import ru.danilashamin.routetracker.logic.mvp.views.MapView;
import ru.danilashamin.routetracker.logic.utils.UtilRoutes;
import ru.danilashamin.routetracker.storage.gateway.AdapterDatabase;
import ru.terrakok.cicerone.Router;


@InjectViewState
public final class MapPresenter extends PresenterBase<MapView> {

    private static final String TAG = MapPresenter.class.getSimpleName();

    @Inject
    PermissionsManager permissionsManager;

    @Inject
    LocationManager locationManager;

    @Inject
    AdapterDatabase adapterDatabase;

    @Inject
    EntitiesMapper mapper;

    @Inject
    UtilRoutes utilRoutes;

    private final Router router;

    public MapPresenter(Router router) {
        this.router = router;
        App.getInstance().getAppComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        requestPermissions();
        subscribeToRouteControlInfo();
    }

    private void subscribeToRouteControlInfo() {
        addDisposable(adapterDatabase.subscribeToActiveRoute()
                .observeOn(AndroidSchedulers.mainThread())
                .map(EntityRouteControl::from)
                .subscribe(routeControl -> getViewState().submitRouteControlInfo(routeControl),
                        error -> {
                            Crashlytics.logException(error);
                            error.printStackTrace();
                        }));
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
        getViewState().collapseRouteControl();
    }

    public void onStartRouteClicked() {
        addDisposable(adapterDatabase.createRouteAndGet()
                .subscribe(utilRoutes::startTracking,
                        error -> {
                            Crashlytics.logException(error);
                            error.printStackTrace();
                        }));
    }

    public void onStopRouteClicked(long routeId) {
        utilRoutes.stopTracking();

        addDisposable(locationManager.getCurrentLocation()
                .flatMapSingle(locationPoint -> adapterDatabase.saveTrackpoint(locationPoint, routeId))
                .flatMapSingle(trackpointId -> adapterDatabase.stopRoute(routeId))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(rowCount -> {
                    getViewState().submitRouteControlInfo(EntityRouteControl.startRouteControl());
                    Log.d(TAG, "Route with id " + routeId + " successfully completed");
                }, error -> {
                    Crashlytics.logException(error);
                    error.printStackTrace();
                }));
    }

    public void onPauseRouteClicked(long routeId) {
        utilRoutes.stopTracking();

        addDisposable(locationManager.getCurrentLocation()
                .flatMapSingle(locationPoint -> adapterDatabase.saveTrackpoint(locationPoint, routeId))
                .flatMapSingle(trackpointId -> adapterDatabase.pauseRoute(routeId))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(rowCount -> {
                    Log.d(TAG, "Route with id " + routeId + " successfully paused");
                }, error -> {
                    Crashlytics.logException(error);
                    error.printStackTrace();
                }));
    }

    public void onResumeRouteClicked(long routeId) {
        addDisposable(adapterDatabase.resumeRoute(routeId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(route -> {
                    Log.d(TAG, "Route with id " + routeId + " successfully resumed");
                    utilRoutes.startTracking(route);
                }, error -> {
                    Crashlytics.logException(error);
                    error.printStackTrace();
                }));
    }

    // ********************************* INTERACTORS REGION **********************************//


    // ********************************* INTERACTORS END REGION **********************************//


}
