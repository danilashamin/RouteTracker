package ru.danilashamin.routetracker.base.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.elogroup.tracker.logic.usermessages.Message;

@StateStrategyType(SkipStrategy.class)
public interface ViewBase extends MvpView {

    void showMessage(String message);

    void showMessage(Message message);
}
