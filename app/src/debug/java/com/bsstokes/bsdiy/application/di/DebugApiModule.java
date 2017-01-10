package com.bsstokes.bsdiy.application.di;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

import static com.bsstokes.bsdiy.application.di.ApiModule.BASE_API_CLIENT;

@Module(includes = ApiModule.class)
class DebugApiModule {

    @Provides
    @Singleton
    @Named(ApiModule.API_CLIENT)
    OkHttpClient provideApiOkHttpClient(
            @Named(BASE_API_CLIENT) OkHttpClient baseApiOkHttpClient,
            HttpLoggingInterceptor httpLoggingInterceptor,
            StethoInterceptor stethoInterceptor) {

        return baseApiOkHttpClient.newBuilder()
                .addNetworkInterceptor(stethoInterceptor)
                .addInterceptor(httpLoggingInterceptor)
                .build();
    }

    @Provides
    @Singleton
    HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        final HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;
    }

    @Provides
    @Singleton
    StethoInterceptor provideStethoInterceptor() {
        return new StethoInterceptor();
    }
}
