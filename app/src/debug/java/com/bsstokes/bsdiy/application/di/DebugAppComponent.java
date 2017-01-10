package com.bsstokes.bsdiy.application.di;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, DbModule.class, DebugApiModule.class})
interface DebugAppComponent extends AppComponent {
}
