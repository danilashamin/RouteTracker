package ru.danilashamin.routetracker.logic.mvp.presenters;

import ru.danilashamin.routetracker.base.presenter.PresenterBase;
import ru.danilashamin.routetracker.logic.mvp.views.TimeRangeView;
import ru.terrakok.cicerone.Router;

public final class TimeRangePresenter extends PresenterBase<TimeRangeView> {
    private final Router router;

    public TimeRangePresenter(Router router) {
        this.router = router;
    }

    public void onBackPressed() {
        router.exit();
    }
}
