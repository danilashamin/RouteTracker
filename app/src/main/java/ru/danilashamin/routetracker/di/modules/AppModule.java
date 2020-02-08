package ru.danilashamin.routetracker.di.modules;

import android.content.Context;
import android.content.res.Resources;

import androidx.core.app.NotificationManagerCompat;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.danilashamin.routetracker.logic.framework.NotificationsManager;
import ru.danilashamin.routetracker.logic.sharedprefs.Preferences;
import ru.danilashamin.routetracker.logic.utils.ResourcesUtils;
import ru.danilashamin.routetracker.storage.converters.DateTimeTypeConverter;

@Module
public final class AppModule {

    private final Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @Singleton
    @Provides
    Context provideContext() {
        return context;
    }

    @Singleton
    @Provides
    Preferences providePreferences(DateTimeTypeConverter dateTimeTypeConverter) {
        return new Preferences(dateTimeTypeConverter, context);
    }

    @Singleton
    @Provides
    Resources provideResources() {
        return context.getResources();
    }

    @Singleton
    @Provides
    ResourcesUtils provideResourcesUtils(Resources resources){
        return new ResourcesUtils(resources);
    }

    @Singleton
    @Provides
    NotificationManagerCompat provideNotificationManager(){
        return NotificationManagerCompat.from(context);
    }

    @Singleton
    @Provides
    NotificationsManager provideNotificationsManager(NotificationManagerCompat notificationManager, Context context){
        return new NotificationsManager(notificationManager, context);
    }

}
