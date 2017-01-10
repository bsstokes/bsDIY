package com.bsstokes.bsdiy.application.di;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, ReleaseApiModule.class})
interface ReleaseAppComponent extends AppComponent {
}
