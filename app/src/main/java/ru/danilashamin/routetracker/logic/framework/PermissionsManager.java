package ru.danilashamin.routetracker.logic.framework;

import android.Manifest;

import androidx.core.content.PermissionChecker;

import com.vanniktech.rxpermission.Permission;
import com.vanniktech.rxpermission.RxPermission;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;

public final class PermissionsManager {
    private final RxPermission rxPermission;

    public PermissionsManager(RxPermission rxPermission) {
        this.rxPermission = rxPermission;
    }

    public Observable<PermissionRequest> requestLocationPermissions() {
        Observable<Permission> fineLocationObservable = rxPermission.request(Manifest.permission.ACCESS_FINE_LOCATION).toObservable();
        Observable<Permission> coarseLocationObservable = rxPermission.request(Manifest.permission.ACCESS_COARSE_LOCATION).toObservable();
        Observable<Permission> backgroundLocationObservable = rxPermission.request(Manifest.permission.ACCESS_BACKGROUND_LOCATION).toObservable();
        return Observable.zip(fineLocationObservable, coarseLocationObservable, backgroundLocationObservable, (fineLocationPermission, coarseLocationPermission, backgroundLocationPermission) -> new PermissionRequest(Arrays.asList(fineLocationPermission, coarseLocationPermission, backgroundLocationPermission)));
    }

    public static final class PermissionRequest {
        private final List<Permission> permissions;

        PermissionRequest(List<Permission> permissions) {
            this.permissions = permissions;
        }

        public List<Permission> getPermissions() {
            return permissions;
        }

        public boolean isGranted() {
            for (Permission permission : permissions) {
                if (permission.state() == Permission.State.DENIED) {
                    return false;
                }
            }
            return true;
        }

        public boolean isDeniedNotShowing() {
            for (Permission permission : permissions) {
                if (permission.state() == Permission.State.DENIED_NOT_SHOWN) {
                    return true;
                }
            }
            return false;
        }
    }

}
