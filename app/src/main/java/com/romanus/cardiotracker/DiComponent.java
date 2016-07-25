package com.romanus.cardiotracker;

import com.romanus.cardiotracker.modules.AppModule;
import com.romanus.cardiotracker.modules.UtilsModule;
import com.romanus.cardiotracker.ui.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by roman on 6/12/16.
 */
@Singleton
@Component(modules = {AppModule.class, UtilsModule.class})
public interface DiComponent {
    void inject(MainActivity mainActivity);
}
