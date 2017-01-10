package com.bsstokes.bsdiy.application.di;

import com.bsstokes.bsdiy.BsDiyApplication;
import com.bsstokes.bsdiy.BuildConfig;
import com.bsstokes.bsdiy.R;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApiModule {
    static final String API_CLIENT = "apiClient";
    static final String BASE_API_CLIENT = "baseApiClient";
    private static final String BASE_CLIENT = "baseClient";
    private static final String USER_AGENT = "userAgent";

//    @Provides
//    @Singleton
//    DaikuApi provideDaikuApi(@Named(API_CLIENT) OkHttpClient okHttpClient) {
//        final Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(DaikuApi.ENDPOINT)
//                .client(okHttpClient)
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .addConverterFactory(MoshiConverterFactory.create())
//                .build();
//        return retrofit.create(DaikuApi.class);
//    }

    @Provides
    @Singleton
    @Named(USER_AGENT)
    String provideUserAgent(BsDiyApplication application) {
        final String appName = application.getString(R.string.app_name);
        return appName + "/" + BuildConfig.VERSION_NAME;
    }

//    @Provides
//    @Singleton
//    @Named(BASE_CLIENT)
//    OkHttpClient provideBaseOkHttpClient(@Named(USER_AGENT) String userAgent) {
//        return new OkHttpClient.Builder()
//                .addInterceptor(new UserAgentInterceptor(userAgent))
//                .build();
//    }
//
//    @Provides
//    @Singleton
//    @Named(ApiModule.BASE_API_CLIENT)
//    OkHttpClient provideBaseApiOkHttpClient(
//            @Named(BASE_CLIENT) OkHttpClient baseOkHttpClient,
//            ApiSignatureInterceptor apiSignatureInterceptor) {
//
//        return baseOkHttpClient.newBuilder()
//                .addInterceptor(apiSignatureInterceptor)
//                .build();
//    }
}
