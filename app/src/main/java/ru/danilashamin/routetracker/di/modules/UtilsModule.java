package ru.danilashamin.routetracker.di.modules;

import android.content.Context;
import android.content.res.Resources;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.danilashamin.routetracker.logic.utils.ResourcesUtils;
import ru.danilashamin.routetracker.logic.utils.UtilText;
import ru.danilashamin.routetracker.logic.utils.UtilTime;
import ru.danilashamin.routetracker.storage.converters.DateTimeTypeConverter;
import ru.danilashamin.routetracker.ui.utils.ImageUtils;
import ru.danilashamin.routetracker.ui.utils.ViewMapUtils;

@Module
public final class UtilsModule {

    @Singleton
    @Provides
    public UtilText provideTextUtil() {
        return new UtilText();
    }

    @Singleton
    @Provides
    UtilTime provideTimeUtils(Resources resources) {
        return new UtilTime(resources);
    }

    @Singleton
    @Provides
    DateTimeTypeConverter provideDateTimeTypeConverter(){
        return new DateTimeTypeConverter();
    }

    @Singleton
    @Provides
    ImageUtils provideImageUtils(Context context){
        return new ImageUtils(context);
    }

    @Singleton
    @Provides
    ViewMapUtils provideViewMapUtils(ImageUtils imageUtils, ResourcesUtils resourcesUtils){
        return new ViewMapUtils(imageUtils, resourcesUtils);
    }
}
