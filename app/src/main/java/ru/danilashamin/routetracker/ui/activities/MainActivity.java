package ru.danilashamin.routetracker.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.arellomobile.mvp.presenter.InjectPresenter;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import ru.danilashamin.routetracker.R;
import ru.danilashamin.routetracker.application.App;
import ru.danilashamin.routetracker.base.view.ActivityBase;
import ru.danilashamin.routetracker.logic.maintabs.MainTab;
import ru.danilashamin.routetracker.logic.mvp.presenters.MainPresenter;
import ru.danilashamin.routetracker.logic.mvp.views.MainView;
import ru.danilashamin.routetracker.ui.navigation.BackButtonListener;
import ru.danilashamin.routetracker.ui.navigation.RouterProvider;
import ru.danilashamin.routetracker.ui.navigation.Screens;
import ru.terrakok.cicerone.Router;

public final class MainActivity extends ActivityBase implements MainView, BottomNavigationView.OnNavigationItemSelectedListener, RouterProvider {

    @BindView(R.id.main_bottom_navigation_view)
    BottomNavigationView bottomNavigationView;

    @InjectPresenter
    MainPresenter presenter;

    @Inject
    Router router;


    public static Intent starterIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void inject() {
        App.getInstance().getAppComponent().inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            selectTab(MainTab.ROUTES_LIST);
        }
    }

    @Override
    protected void init() {
        super.init();
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    protected int provideLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.routes_list:
                selectTab(MainTab.ROUTES_LIST);
                return true;

            case R.id.map_fragment:
                selectTab(MainTab.MAP);
                return true;

            case R.id.time_range:
                selectTab(MainTab.TIME_RANGE);
                return true;
        }
        return false;
    }

    @Override
    public Router getRouter() {
        return router;
    }

    private void selectTab(@MainTab String tab) {
        FragmentManager fm = getSupportFragmentManager();
        Fragment currentFragment = null;
        List<Fragment> fragments = fm.getFragments();
        for (Fragment f : fragments) {
            if (f.isVisible()) {
                currentFragment = f;
                break;
            }
        }
        Fragment newFragment = fm.findFragmentByTag(tab);

        if (newFragment != null && currentFragment == newFragment) return;

        FragmentTransaction transaction = fm.beginTransaction();
        if (newFragment == null) {
            transaction.add(R.id.container, new Screens.MainTabScreen(tab).getFragment(), tab);
        }

        if (currentFragment != null) {
            transaction.hide(currentFragment);
        }

        if (newFragment != null) {
            transaction.show(newFragment);
        }
        transaction.commitNow();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = null;
        List<Fragment> fragments = fm.getFragments();
        for (Fragment f : fragments) {
            if (f.isVisible()) {
                fragment = f;
                break;
            }
        }
        if (!(fragment instanceof BackButtonListener) || !((BackButtonListener) fragment).onBackPressed()) {
            presenter.onBackPressed();
        }
    }
}
