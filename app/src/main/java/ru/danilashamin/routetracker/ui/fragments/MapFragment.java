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

import javax.inject.Inject;

import ru.danilashamin.routetracker.R;
import ru.danilashamin.routetracker.application.App;
import ru.danilashamin.routetracker.base.view.FragmentBase;
import ru.danilashamin.routetracker.logic.entities.EntityLocation;
import ru.danilashamin.routetracker.logic.mvp.presenters.MapPresenter;
import ru.danilashamin.routetracker.logic.mvp.views.MapView;
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

    @ProvidePresenter
    MapPresenter providePresenter() {
        return new MapPresenter(((RouterProvider) requireParentFragment()).getRouter());
    }

    @Override
    protected void init() {
        initNavbar(R.string.map);
        App.getInstance().getAppComponent().inject(this);
    }

    @Override
    protected int provideLayoutRes() {
        return R.layout.fragment_map;
    }

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
    public void showCurrentLocation(EntityLocation location) {
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location.toLatLng(), DEFAULT_ZOOM));
    }

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
        presenter.onMapClicked(EntityLocation.from(position));
    }

    private boolean onMarkerClick(Marker marker) {

        return false;
    }

    @Override
    public boolean onBackPressed() {
        presenter.onBackPressed();
        return true;
    }

}
