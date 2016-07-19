package com.romanus.cardiotracker;

import com.romanus.cardiotracker.domain.AppModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by roman on 6/12/16.
 */
@Singleton
@Component(modules = {AppModule.class})
public interface DiComponent {

}
