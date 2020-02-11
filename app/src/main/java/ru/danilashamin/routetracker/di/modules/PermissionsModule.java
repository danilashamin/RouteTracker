package ru.danilashamin.routetracker.di.modules;

import android.content.Context;

import com.vanniktech.rxpermission.RealRxPermission;
import com.vanniktech.rxpermission.RxPermission;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.danilashamin.routetracker.logic.framework.PermissionsManager;

@Module
public final class PermissionsModule {
    private final RxPermission rxPermission;

    public PermissionsModule(Context context) {
        rxPermission = RealRxPermission.getInstance(context);
    }

    @Singleton
    @Provides
    RxPermission provideRxPermission(){
        return rxPermission;
    }

    @Singleton
    @Provides
    PermissionsManager providePermissionManager(RxPermission rxPermission){
        return new PermissionsManager(rxPermission);
    }
}
