package ru.danilashamin.routetracker.logic.mvp.views;

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import ru.danilashamin.routetracker.base.view.ViewBase;
import ru.danilashamin.routetracker.logic.entities.EntityRouteControl;
import ru.danilashamin.routetracker.logic.entities.LocationPoint;

public interface MapView extends ViewBase {

    @StateStrategyType(AddToEndSingleStrategy.class)
    void loadMap();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void submitRouteControlInfo(EntityRouteControl routeControl);

    void collapseRouteControl();

    //******************* MAP REGION **********************

    @StateStrategyType(OneExecutionStateStrategy.class)
    void showCurrentLocation(LocationPoint location);

    //******************* END MAP REGION **********************

}
