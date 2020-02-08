package ru.danilashamin.routetracker.logic.mvp.presenters;

import com.arellomobile.mvp.InjectViewState;



import javax.inject.Inject;

import ru.danilashamin.routetracker.application.App;
import ru.danilashamin.routetracker.base.presenter.PresenterBase;
import ru.danilashamin.routetracker.logic.mvp.views.MainView;
import ru.terrakok.cicerone.Router;

@InjectViewState
public final class MainPresenter extends PresenterBase<MainView> {

    @Inject
    Router router;

    public MainPresenter(){
        App.getInstance().getAppComponent().inject(this);
    }

    public void onBackPressed(){
        router.exit();
    }
}
