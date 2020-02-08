package ru.danilashamin.routetracker.di.modules;

import android.content.Context;

import androidx.room.Room;



import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.danilashamin.routetracker.logic.mappers.EntitiesMapper;
import ru.danilashamin.routetracker.storage.config.AppDbConfig;
import ru.danilashamin.routetracker.storage.database.AppDatabase;
import ru.danilashamin.routetracker.storage.gateway.AdapterDatabase;

@Module
public final class DatabaseModule {

    private final AppDatabase database;

    public DatabaseModule(Context context) {
        database = Room.databaseBuilder(context, AppDatabase.class, AppDbConfig.APP_DATABASE_NAME)
                .build();
    }

    @Singleton
    @Provides
    AppDatabase provideDatabase() {
        return database;
    }

    @Singleton
    @Provides
    AdapterDatabase provideAdapterDatabase(AppDatabase database) {
        return new AdapterDatabase(database);
    }

    @Singleton
    @Provides
    EntitiesMapper provideMapper() {
        return new EntitiesMapper();
    }

}
