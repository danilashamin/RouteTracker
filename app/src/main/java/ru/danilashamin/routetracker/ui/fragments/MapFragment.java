package ru.danilashamin.routetracker.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.Polyline;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ru.danilashamin.routetracker.R;
import ru.danilashamin.routetracker.application.App;
import ru.danilashamin.routetracker.base.view.FragmentBase;
import ru.danilashamin.routetracker.logic.entities.EntityRouteControl;
import ru.danilashamin.routetracker.logic.entities.LocationPoint;
import ru.danilashamin.routetracker.logic.mvp.presenters.MapPresenter;
import ru.danilashamin.routetracker.logic.mvp.views.MapView;
import ru.danilashamin.routetracker.ui.bottomsheets.RouteControlBottomSheet;
import ru.danilashamin.routetracker.ui.navigation.BackButtonListener;
import ru.danilashamin.routetracker.ui.navigation.RouterProvider;
import ru.danilashamin.routetracker.ui.utils.ViewMapUtils;

public final class MapFragment extends FragmentBase implements MapView, BackButtonListener {

    private static final float DEFAULT_ZOOM = 13F;

    public static MapFragment newInstance() {
        Bundle args = new Bundle();

        MapFragment fragment = new MapFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @InjectPresenter
    MapPresenter presenter;

    @Inject
    ViewMapUtils viewMapUtils;


    private GoogleMap googleMap;

    private RouteControlBottomSheet routeControlBottomSheet;

    private List<Polyline> routePolylines;

    @ProvidePresenter
    MapPresenter providePresenter() {
        return new MapPresenter(((RouterProvider) requireParentFragment()).getRouter());
    }

    @Override
    protected void init() {
        initNavbar(R.string.map);
        routePolylines = new ArrayList<>();
        App.getInstance().getAppComponent().inject(this);
        routeControlBottomSheet = new RouteControlBottomSheet(requireView());
        routeControlBottomSheet.setActionListener(routeControlActionListener)
                .collapse();
    }

    @Override
    protected int provideLayoutRes() {
        return R.layout.fragment_map;
    }

    // **************************** VIEW REGION **************************** //
    @Override
    public void loadMap() {
        Fragment fragment = getChildFragmentManager().findFragmentById(R.id.map_fragment);
        if (fragment == null) {
            return;
        }
        SupportMapFragment mapFragment = (SupportMapFragment) fragment;
        viewMapUtils.configureMapUi(mapFragment);
        mapFragment.getMapAsync(this::onMapReady);
    }

    @Override
    public void showCurrentLocation(LocationPoint location) {
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location.toLatLng(), DEFAULT_ZOOM));
    }

    @Override
    public void submitRouteControlInfo(EntityRouteControl routeControl) {
        routeControlBottomSheet.submitControlInfo(routeControl);
    }

    @Override
    public void collapseRouteControl() {
        routeControlBottomSheet.collapse();
    }

    // **************************** VIEW REGION END **************************** //

    private void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        googleMap.setMyLocationEnabled(true);
        UiSettings uiSettings = googleMap.getUiSettings();
        uiSettings.setAllGesturesEnabled(true);
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setCompassEnabled(true);
        googleMap.setOnMarkerClickListener(this::onMarkerClick);
        googleMap.setOnMapClickListener(this::onMapClicked);
        presenter.onMapReady();
    }

    private void onMapClicked(LatLng position) {
        presenter.onMapClicked(LocationPoint.from(position));
    }

    private boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public boolean onBackPressed() {
        presenter.onBackPressed();
        return true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        routeControlBottomSheet.unbind();
    }

    private RouteControlBottomSheet.ActionListener routeControlActionListener = new RouteControlBottomSheet.ActionListener() {
        @Override
        public void onStartRouteClicked() {
            presenter.onStartRouteClicked();
        }

        @Override
        public void onStopRouteClicked(long routeId) {
            presenter.onStopRouteClicked(routeId);

        }

        @Override
        public void onPauseRouteClicked(long routeId) {
            presenter.onPauseRouteClicked(routeId);

        }

        @Override
        public void onResumeRouteClicked(long routeId) {
            presenter.onResumeRouteClicked(routeId);
        }
    };
}
