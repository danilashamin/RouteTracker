package ru.danilashamin.routetracker.ui.navigation;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.fragment.app.Fragment;

import java.util.Locale;

import ru.danilashamin.routetracker.base.view.MainTabContainerFragment;
import ru.danilashamin.routetracker.logic.entities.EntityLocation;
import ru.danilashamin.routetracker.logic.maintabs.MainTab;
import ru.danilashamin.routetracker.ui.activities.MainActivity;
import ru.danilashamin.routetracker.ui.fragments.MapFragment;
import ru.danilashamin.routetracker.ui.fragments.RoutesListFragment;
import ru.danilashamin.routetracker.ui.fragments.TimeRangeFragment;
import ru.terrakok.cicerone.Screen;
import ru.terrakok.cicerone.android.support.SupportAppScreen;


public final class Screens {
    private Screens() {
        throw new AssertionError();
    }

    public static Screen getMainTab(@MainTab String tabName) {
        switch (tabName){
            case MainTab.ROUTES_LIST:
                return new RoutesListScreen();
            case MainTab.MAP:
                return new MapScreen();
            case MainTab.TIME_RANGE:
                return new TimeRangeScreen();
                default:
                    throw new IllegalArgumentException("Illegal main tab: " + tabName);
        }
    }

    public static final class MainScreen extends SupportAppScreen {
        @Override
        public Intent getActivityIntent(Context context) {
            return MainActivity.starterIntent(context);
        }
    }

    public static final class MainTabScreen extends SupportAppScreen {
        @MainTab
        private final String tabName;

        public MainTabScreen(@MainTab String tabName) {
            this.tabName = tabName;
        }

        @Override
        public Fragment getFragment() {
            return MainTabContainerFragment.newInstance(tabName);
        }
    }

    public static final class MapScreen extends SupportAppScreen {
        @Override
        public Fragment getFragment() {
            return MapFragment.newInstance();
        }
    }

    public static final class RoutesListScreen extends SupportAppScreen {
        @Override
        public Fragment getFragment() {
            return RoutesListFragment.newInstance();
        }
    }

    public static final class TimeRangeScreen extends SupportAppScreen {
        @Override
        public Fragment getFragment() {
            return TimeRangeFragment.newInstance();
        }
    }


    public static final class LocationProcessorScreen extends SupportAppScreen {

        private Uri intentUri;

        public LocationProcessorScreen(EntityLocation location) {
            intentUri = Uri.parse(String.format(Locale.ENGLISH,"geo:0,0?q=%f, %f", location.getLatitude(), location.getLongitude()));
        }

        @Override
        public Intent getActivityIntent(Context context) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(intentUri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            return intent;
        }
    }
}
