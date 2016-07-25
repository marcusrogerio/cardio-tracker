package com.romanus.cardiotracker.modules;

import android.content.Context;

import com.romanus.cardiotracker.util.Utils;
import com.romanus.cardiotracker.util.UtilsImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by roman on 7/25/16.
 */
@Module(includes = {AppModule.class})
public class UtilsModule {

    @Provides
    @Singleton
    public Utils provideUtils(Context context) {
        return new UtilsImpl(context);
    }
}
