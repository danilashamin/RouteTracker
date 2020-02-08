package ru.danilashamin.routetracker.base.presenter;

import androidx.annotation.CallSuper;

import com.arellomobile.mvp.MvpPresenter;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import ru.danilashamin.routetracker.base.view.ViewBase;


public class PresenterBase<V extends ViewBase> extends MvpPresenter<V> {

    private final CompositeDisposable disposablesContainer;

    public PresenterBase() {
        disposablesContainer = new CompositeDisposable();
    }

    public void addDisposable(Disposable d){
        disposablesContainer.add(d);
    }

    @CallSuper
    @Override
    public void onDestroy() {
        disposablesContainer.clear();
    }
}
