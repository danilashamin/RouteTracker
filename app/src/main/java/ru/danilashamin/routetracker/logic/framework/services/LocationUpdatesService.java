package ru.danilashamin.routetracker.logic.framework.services;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.crashlytics.android.Crashlytics;

import org.threeten.bp.LocalDateTime;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ru.danilashamin.routetracker.application.App;
import ru.danilashamin.routetracker.logic.enums.RouteStatus;
import ru.danilashamin.routetracker.logic.framework.NotificationsManager;
import ru.danilashamin.routetracker.logic.location.LocationManager;
import ru.danilashamin.routetracker.storage.entities.Route;
import ru.danilashamin.routetracker.storage.gateway.AdapterDatabase;

import static android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_LOCATION;

public final class LocationUpdatesService extends Service {

    private static final String TAG = LocationUpdatesService.class.getSimpleName();

    private static final String KEY_ROUTE_TRACKING_START_TIME = "KEY_ROUTE_TRACKING_START_TIME";
    private static final String KEY_ROUTE_ID = "ROUTE_ID";
    private static final int FOREGROUND_ID = 1;

    public static void start(Context context, Route route) {
        Long routeId = route.getId();
        LocalDateTime routeTrackingStartTime = route.getStartedAt();
        start(context, routeId, routeTrackingStartTime);
    }

    public static void start(Context context, Long routeId, LocalDateTime routeTrackingStartTime) {
        Intent starter = new Intent(context, LocationUpdatesService.class);
        starter.putExtra(KEY_ROUTE_TRACKING_START_TIME, routeTrackingStartTime);
        starter.putExtra(KEY_ROUTE_ID, routeId);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(starter);
        } else {
            context.startService(starter);
        }
    }

    public static void stop(Context context) {
        Intent stopper = new Intent(context, LocationUpdatesService.class);
        context.stopService(stopper);
    }

    @Inject
    LocationManager locationManager;

    @Inject
    AdapterDatabase adapterDatabase;

    @Inject
    NotificationsManager notificationsManager;

    private LocalDateTime routeTrackingStartTime;

    private Long routeId;

    private Disposable locationUpdatesSubscription;

    @Override
    public void onCreate() {
        App.getInstance().getAppComponent().inject(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        routeTrackingStartTime = (LocalDateTime) intent.getSerializableExtra(KEY_ROUTE_TRACKING_START_TIME);
        routeId = intent.getLongExtra(KEY_ROUTE_ID, 0L);
        configureService();
        configureLocationUpdates();
        return START_REDELIVER_INTENT;
    }

    private void configureService() {
        notificationsManager.setupLocationUpdatedChannel();
        Notification notification = notificationsManager.buildLocationForegroundNotification(routeTrackingStartTime);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            startForeground(FOREGROUND_ID, notification, FOREGROUND_SERVICE_TYPE_LOCATION);
        } else {
            startForeground(FOREGROUND_ID, notification);
        }
    }

    private void configureLocationUpdates() {
        locationUpdatesSubscription = locationManager.startLocationUpdates()
                .map(locationPoint -> adapterDatabase.saveTrackpoint(locationPoint, routeId, RouteStatus.ON_ROAD))
                .subscribe(trackpointId -> Log.d(TAG, "Trackpoint saved, id = " + trackpointId + " , routeId = " + routeId), error -> {
                    Crashlytics.logException(error);
                    error.printStackTrace();
                });

    }

    @Override
    public void onDestroy() {
        if (locationUpdatesSubscription != null && !locationUpdatesSubscription.isDisposed()) {
            locationUpdatesSubscription.dispose();
            locationUpdatesSubscription = null;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
