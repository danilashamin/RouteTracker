package ru.danilashamin.routetracker.di.modules;

import com.elogroup.tracker.BuildConfig;
import com.elogroup.tracker.api.implementation.EloApiImpl;
import com.elogroup.tracker.api.interfaces.EloApi;
import com.elogroup.tracker.application.AppConfig;
import com.elogroup.tracker.logic.sharedprefs.Preferences;
import com.elogroup.tracker.network.api.EloApiGateway;
import com.elogroup.tracker.network.interceptors.TokenInterceptor;
import com.elogroup.tracker.storage.gateway.AdapterDatabase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ihsanbal.logging.Level;
import com.ihsanbal.logging.LoggingInterceptor;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.internal.platform.Platform;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public final class ApiModule {

    private static final String TOKEN_INTERCEPTOR_NAME = "TokenInterceptor";
    private static final String LOGGER_INTERCEPTOR_NAME = "LoggerInterceptor";

    private static final String API_LOG_TAG = "EloTracking";

    @Provides
    @Singleton
    EloApi provideEloApi(EloApiGateway gateway, Preferences preferences, Gson gson, AdapterDatabase adapterDatabase) {
        return new EloApiImpl(gateway, preferences, gson, adapterDatabase);
    }

    @Provides
    @Singleton
    EloApiGateway provideEloApiGateway(OkHttpClient okHttpClient, CallAdapter.Factory callAdapterFactory, Converter.Factory converterFactory) {
        return new Retrofit.Builder()
                .baseUrl(AppConfig.EloApiConfig.ENDPOINT)
                .addCallAdapterFactory(callAdapterFactory)
                .addConverterFactory(converterFactory)
                .client(okHttpClient)
                .build()
                .create(EloApiGateway.class);
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(@Named(TOKEN_INTERCEPTOR_NAME) Interceptor tokenInterceptor, @Named(LOGGER_INTERCEPTOR_NAME) Interceptor loggerInterceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(tokenInterceptor)
                .addInterceptor(loggerInterceptor)
                .build();
    }

    @Provides
    @Singleton
    CallAdapter.Factory provideCallAdapterFactory() {
        return RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io());
    }

    @Provides
    @Singleton
    Converter.Factory provideConverterFactory(Gson gson) {
        return GsonConverterFactory.create(gson);
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder()
                .create();
    }

    @Named(TOKEN_INTERCEPTOR_NAME)
    @Provides
    @Singleton
    Interceptor providesTokenInterceptor() {
        return new TokenInterceptor();
    }

    @Named(LOGGER_INTERCEPTOR_NAME)
    @Provides
    @Singleton
    Interceptor provideLoggerInterceptor() {
        return new LoggingInterceptor.Builder()
                .loggable(BuildConfig.DEBUG)
                .setLevel(Level.BODY)
                .log(Platform.INFO)
                .tag(API_LOG_TAG)
                .build();
    }
}
