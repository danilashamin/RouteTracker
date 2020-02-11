package ru.danilashamin.routetracker.logic.mvp.presenters;

import ru.danilashamin.routetracker.base.presenter.PresenterBase;
import ru.danilashamin.routetracker.logic.mvp.views.RoutesListView;
import ru.terrakok.cicerone.Router;

public final class RoutesListPresenter extends PresenterBase<RoutesListView> {
    private final Router router;

    public RoutesListPresenter(Router router) {
        this.router = router;
    }

    public void onBackPressed() {
        router.exit();
    }
}
