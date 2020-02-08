package ru.danilashamin.routetracker.logic.framework;

import android.app.Notification;
import android.app.NotificationChannel;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


import org.threeten.bp.LocalDateTime;

import ru.danilashamin.routetracker.R;
import ru.danilashamin.routetracker.logic.utils.UtilTime;


public final class NotificationsManager {

    private static final String LOCATION_UPDATES_CHANNEL_ID = "CHANNEL ID";
    private static final String LOCATION_UPDATES_CHANNEL_NAME = "CHANNEL NAME";
    private static final String LOCATION_UPDATES_CHANNEL_DESCRIPTION = "CHANNEL DESCRIPTION";

    private final NotificationManagerCompat notificationManager;
    private final Context context;
    private UtilTime utilTime;

    public NotificationsManager(NotificationManagerCompat notificationManager, Context context, UtilTime utilTime) {
        this.notificationManager = notificationManager;
        this.context = context;
        this.utilTime = utilTime;
    }

    public void setupLocationUpdatedChannel() {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.O) {
            return;
        }
        NotificationChannel adminChannel = new NotificationChannel(LOCATION_UPDATES_CHANNEL_ID, LOCATION_UPDATES_CHANNEL_NAME, android.app.NotificationManager.IMPORTANCE_HIGH);
        adminChannel.setDescription(LOCATION_UPDATES_CHANNEL_DESCRIPTION);
        adminChannel.enableLights(false);
        adminChannel.setLightColor(Color.BLUE);
        adminChannel.enableVibration(false);
        notificationManager.createNotificationChannel(adminChannel);
    }

    public Notification buildLocationForegroundNotification(LocalDateTime startTime) {
        return new NotificationCompat.Builder(context, LOCATION_UPDATES_CHANNEL_ID)
                .setAutoCancel(false)
                .setContentTitle(context.getString(R.string.route_tracking))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(context.getString(R.string.location_updates_service_content_template, utilTime.formatTime(startTime))))
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                .setSmallIcon(R.drawable.ic_location_updates_notification_icon)
                .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
                .build();
    }
}
