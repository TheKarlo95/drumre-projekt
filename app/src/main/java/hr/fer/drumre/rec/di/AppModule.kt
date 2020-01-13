package hr.fer.drumre.rec.di

import android.content.Context
import dagger.Module
import dagger.Provides
import hr.fer.drumre.rec.App

@Module
class AppModule {

    @Provides
    fun provideContext(application: App): Context = application.applicationContext
}
