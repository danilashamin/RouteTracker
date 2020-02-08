package ru.danilashamin.routetracker.base.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;



import javax.inject.Inject;

import ru.danilashamin.routetracker.R;
import ru.danilashamin.routetracker.application.App;
import ru.danilashamin.routetracker.logic.maintabs.MainTab;
import ru.danilashamin.routetracker.ui.navigation.BackButtonListener;
import ru.danilashamin.routetracker.ui.navigation.LocalCiceroneHolder;
import ru.danilashamin.routetracker.ui.navigation.RouterProvider;
import ru.danilashamin.routetracker.ui.navigation.Screens;
import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.Router;
import ru.terrakok.cicerone.android.support.SupportAppNavigator;

public final class MainTabContainerFragment extends Fragment implements RouterProvider, BackButtonListener {

    private static final String TAB_NAME = "tab_name";

    private Navigator navigator;

    @Inject
    LocalCiceroneHolder ciceroneHolder;

    public static MainTabContainerFragment newInstance(@MainTab String tabName) {
        MainTabContainerFragment fragment = new MainTabContainerFragment();
        Bundle arguments = new Bundle();
        arguments.putString(TAB_NAME, tabName);
        fragment.setArguments(arguments);

        return fragment;
    }

    private String getTabName() {
        return requireArguments().getString(TAB_NAME);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        App.getInstance().getAppComponent().inject(this);
        super.onCreate(savedInstanceState);
    }

    private Cicerone<Router> getCicerone() {
        return ciceroneHolder.getCicerone(getTabName());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_tab_container, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getChildFragmentManager().findFragmentById(R.id.tab_container) == null) {
            getCicerone().getRouter().replaceScreen(Screens.getMainTab(getTabName()));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getCicerone().getNavigatorHolder().setNavigator(getNavigator());
    }

    @Override
    public void onPause() {
        getCicerone().getNavigatorHolder().removeNavigator();
        super.onPause();
    }

    private Navigator getNavigator() {
        if (navigator == null) {
            navigator = new SupportAppNavigator(getActivity(), getChildFragmentManager(), R.id.tab_container);
        }
        return navigator;
    }

    @Override
    public Router getRouter() {
        return getCicerone().getRouter();
    }

    @Override
    public boolean onBackPressed() {
        Fragment fragment = getChildFragmentManager().findFragmentById(R.id.tab_container);
        if (fragment instanceof BackButtonListener
                && ((BackButtonListener) fragment).onBackPressed()) {
            return true;
        } else {
            ((RouterProvider) requireActivity()).getRouter().exit();
            return true;
        }
    }
}