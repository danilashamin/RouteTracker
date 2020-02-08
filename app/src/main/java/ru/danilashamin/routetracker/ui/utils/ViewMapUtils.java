package ru.danilashamin.routetracker.ui.utils;

import android.annotation.SuppressLint;
import android.os.Build;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import ru.danilashamin.routetracker.R;
import ru.danilashamin.routetracker.logic.utils.ResourcesUtils;

public final class ViewMapUtils {

    private final ImageUtils imageUtils;
    private final ResourcesUtils resourcesUtils;

    private BitmapDescriptor startRouteMarkerDescriptor;

    private BitmapDescriptor endRouteMarkerDescriptor;

    public ViewMapUtils(ImageUtils imageUtils, ResourcesUtils resourcesUtils) {
        this.imageUtils = imageUtils;
        this.resourcesUtils = resourcesUtils;
    }

    public MarkerOptions createStartRouteMarker(LatLng position) {
        return new MarkerOptions()
                .position(position)
                .icon(getStartRouteMarkerDescriptor());
    }

    public MarkerOptions createEndRouteMarker(LatLng position) {
        return new MarkerOptions()
                .position(position)
                .icon(getEndRouteMarkerDescriptor());
    }

    private BitmapDescriptor getStartRouteMarkerDescriptor() {
        if (startRouteMarkerDescriptor == null) {
//            startRouteMarkerDescriptor = createDescriptor(R.drawable.ic_marker_start_location);
        }
        return startRouteMarkerDescriptor;
    }

    private BitmapDescriptor getEndRouteMarkerDescriptor() {
        if (endRouteMarkerDescriptor == null) {
//            endRouteMarkerDescriptor = createDescriptor(R.drawable.ic_marker_finish_location);
        }
        return endRouteMarkerDescriptor;
    }

    private BitmapDescriptor createDescriptor(@DrawableRes int drawableRes) {
        return BitmapDescriptorFactory.fromBitmap(imageUtils.vectorToBitmap(drawableRes));
    }

    public void configureMapUi(@Nullable SupportMapFragment mapFragment){
        if(mapFragment == null){
            return;
        }
        setZoomControllersGravity(mapFragment);
        setMyLocationButtonGravity(mapFragment);
    }

    @SuppressLint("ResourceType")
    private void setZoomControllersGravity(@NonNull SupportMapFragment mapFragment) {
        View root = mapFragment.getView();
        if (root == null || root.findViewById(0x1) == null) {
            return;
        }
        View zoom = root.findViewById(0x1);
        int margin = resourcesUtils.getDimenPx(R.dimen.margin_map_zoom_controls);
        setGravityAndMarginsOnView(zoom, margin, margin, margin, margin);
    }

    @SuppressLint("ResourceType")
    private void setMyLocationButtonGravity(SupportMapFragment mapFragment) {
        View root = mapFragment.getView();
        if (root == null || root.findViewById(0x2) == null) {
            return;
        }
        View myLocation = root.findViewById(0x2);
        int margin = resourcesUtils.getDimenPx(R.dimen.margin_map_zoom_controls);
        int marginTop = resourcesUtils.getDimenPx(R.dimen.margin_map_my_location);
        setGravityAndMarginsOnView(myLocation, margin, marginTop, margin, margin);
    }

    private void setGravityAndMarginsOnView(@Nullable View view, int marginLeft, int marginTop, int marginRight, int marginBottom) {
        if (view == null || !(view.getLayoutParams() instanceof RelativeLayout.LayoutParams)) {
            return;
        }
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();

        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            params.addRule(RelativeLayout.ALIGN_PARENT_END);
        } else {
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        }

        params.setMargins(marginLeft, marginTop, marginRight, marginBottom);
    }


}
