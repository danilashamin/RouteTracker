package ru.danilashamin.routetracker.di.components;


import javax.inject.Singleton;

import dagger.Component;
import ru.danilashamin.routetracker.base.view.MainTabContainerFragment;
import ru.danilashamin.routetracker.di.modules.AppModule;
import ru.danilashamin.routetracker.di.modules.DatabaseModule;
import ru.danilashamin.routetracker.di.modules.InteractorsModule;
import ru.danilashamin.routetracker.di.modules.LocationModule;
import ru.danilashamin.routetracker.di.modules.NavigationModule;
import ru.danilashamin.routetracker.di.modules.PermissionsModule;
import ru.danilashamin.routetracker.di.modules.UtilsModule;
import ru.danilashamin.routetracker.logic.framework.services.LocationUpdatesService;
import ru.danilashamin.routetracker.logic.mvp.presenters.MainPresenter;
import ru.danilashamin.routetracker.ui.activities.MainActivity;
import ru.danilashamin.routetracker.ui.utils.MarginDividerDecoration;

@Singleton
@Component(modules = {
        AppModule.class,
        DatabaseModule.class,
        NavigationModule.class,
        UtilsModule.class,
        LocationModule.class,
        PermissionsModule.class,
        InteractorsModule.class})
public interface AppComponent {

    //Presenters
    void inject(MainPresenter presenter);

    //Activities
    void inject(MainActivity activity);

    //utils
    void inject(MarginDividerDecoration.Builder builder);

    //Fragments TODO вынести в отдельный компонент живущий во время работы фрагмента
    void inject(MainTabContainerFragment fragment);

    //DialogFragments TODO вынести в отдельный компонент живущий во время работы диалога


    //View


    //entities

    //Services
    void inject(LocationUpdatesService service);
}
