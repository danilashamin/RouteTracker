package ru.danilashamin.routetracker.ui.fragments;

import android.os.Bundle;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import ru.danilashamin.routetracker.R;
import ru.danilashamin.routetracker.base.view.FragmentBase;
import ru.danilashamin.routetracker.logic.mvp.presenters.TimeRangePresenter;
import ru.danilashamin.routetracker.logic.mvp.views.TimeRangeView;
import ru.danilashamin.routetracker.ui.navigation.BackButtonListener;
import ru.danilashamin.routetracker.ui.navigation.RouterProvider;

public final class TimeRangeFragment extends FragmentBase implements TimeRangeView, BackButtonListener {

    public static TimeRangeFragment newInstance() {

        Bundle args = new Bundle();

        TimeRangeFragment fragment = new TimeRangeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @InjectPresenter
    TimeRangePresenter presenter;

    @ProvidePresenter
    TimeRangePresenter providePresenter(){
        return new TimeRangePresenter(((RouterProvider)requireParentFragment()).getRouter());
    }

    @Override
    protected int provideLayoutRes() {
        return R.layout.fragment_time_range;
    }

    @Override
    public boolean onBackPressed() {
        presenter.onBackPressed();
        return true;
    }
}
