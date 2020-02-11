package ru.danilashamin.routetracker.application;


import android.content.Context;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.crashlytics.android.Crashlytics;
import com.jakewharton.threetenabp.AndroidThreeTen;

import io.fabric.sdk.android.Fabric;
import ru.danilashamin.routetracker.di.components.AppComponent;
import ru.danilashamin.routetracker.di.components.DaggerAppComponent;
import ru.danilashamin.routetracker.di.modules.AppModule;
import ru.danilashamin.routetracker.di.modules.DatabaseModule;
import ru.danilashamin.routetracker.di.modules.LocationModule;
import ru.danilashamin.routetracker.di.modules.PermissionsModule;

public class App extends MultiDexApplication {

    private static App instance;

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        AndroidThreeTen.init(this);
        instance = this;

        buildAppComponent();
    }

    public static App getInstance() {
        return instance;
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    private void buildAppComponent(){
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .databaseModule(new DatabaseModule(this))
                .locationModule(new LocationModule())
                .permissionsModule(new PermissionsModule(this))
                .build();
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
