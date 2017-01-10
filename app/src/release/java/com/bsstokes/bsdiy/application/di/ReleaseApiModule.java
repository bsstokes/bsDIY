package com.bsstokes.bsdiy.application.di;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

import static com.bsstokes.bsdiy.application.di.ApiModule.BASE_API_CLIENT;

@Module(includes = ApiModule.class)
class ReleaseApiModule {

    @Provides
    @Singleton
    @Named(ApiModule.API_CLIENT)
    OkHttpClient provideApiOkHttpClient(@Named(BASE_API_CLIENT) OkHttpClient baseApiOkHttpClient) {
        return baseApiOkHttpClient.newBuilder()
                .build();
    }
}
