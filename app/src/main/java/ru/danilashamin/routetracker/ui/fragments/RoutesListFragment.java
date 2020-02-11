package ru.danilashamin.routetracker.ui.fragments;

import android.os.Bundle;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import ru.danilashamin.routetracker.R;
import ru.danilashamin.routetracker.base.view.FragmentBase;
import ru.danilashamin.routetracker.logic.mvp.presenters.RoutesListPresenter;
import ru.danilashamin.routetracker.logic.mvp.views.RoutesListView;
import ru.danilashamin.routetracker.ui.navigation.BackButtonListener;
import ru.danilashamin.routetracker.ui.navigation.RouterProvider;

public final class RoutesListFragment extends FragmentBase implements RoutesListView, BackButtonListener {

    public static RoutesListFragment newInstance() {

        Bundle args = new Bundle();

        RoutesListFragment fragment = new RoutesListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @InjectPresenter
    RoutesListPresenter presenter;

    @ProvidePresenter
    RoutesListPresenter providePresenter(){
        return new RoutesListPresenter(((RouterProvider) requireParentFragment()).getRouter());
    }

    @Override
    protected int provideLayoutRes() {
        return R.layout.fragment_routes_list;
    }

    @Override
    public boolean onBackPressed() {
        presenter.onBackPressed();
        return true;
    }
}
