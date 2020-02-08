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

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import ru.danilashamin.routetracker.application.App;
import ru.danilashamin.routetracker.logic.framework.NotificationsManager;
import ru.danilashamin.routetracker.logic.location.LocationManager;
import ru.danilashamin.routetracker.storage.gateway.AdapterDatabase;

import static android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_LOCATION;

public final class LocationUpdatesService extends Service {

    private static final String TAG = LocationUpdatesService.class.getSimpleName();

    private static final String KEY_ORDER_SERVER_ID = "KEY_ORDER_SERVER_ID";
    private static final int FOREGROUND_ID = 1;

    public static void start(Context context, String orderServerId) {
        Intent starter = new Intent(context, LocationUpdatesService.class);
        starter.putExtra(KEY_ORDER_SERVER_ID, orderServerId);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(starter);
        } else {
            context.startService(starter);
        }
    }

    public static void stop(Context context){
        Intent stopper = new Intent(context, LocationUpdatesService.class);
        context.stopService(stopper);
    }

    @Inject
    LocationManager locationManager;

    @Inject
    AdapterDatabase adapterDatabase;

    @Inject
    NotificationsManager notificationsManager;



    private String orderServerId;

    private Disposable locationUpdatesSubscription;

    @Override
    public void onCreate() {
        App.getInstance().getAppComponent().inject(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        orderServerId = intent.getStringExtra(KEY_ORDER_SERVER_ID);
        configureService();
        configureLocationUpdates();
        return START_REDELIVER_INTENT;
    }

    private void configureService() {
        notificationsManager.setupLocationUpdatedChannel();
        Notification notification = notificationsManager.buildLocationForegroundNotification(orderServerId);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            startForeground(FOREGROUND_ID, notification, FOREGROUND_SERVICE_TYPE_LOCATION);
        } else {
            startForeground(FOREGROUND_ID, notification);
        }
    }

    private void configureLocationUpdates() {
        locationUpdatesSubscription = locationManager.startLocationUpdates()
                .subscribe(response -> {
                    Log.d(TAG, "Success on trackpoint send to server");
                }, error -> {
                    Crashlytics.logException(error);
                    error.printStackTrace();
                });

    }

    @Override
    public void onDestroy() {
        if (!locationUpdatesSubscription.isDisposed()) {
            locationUpdatesSubscription.dispose();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
