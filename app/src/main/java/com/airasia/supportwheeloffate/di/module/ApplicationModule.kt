package com.airasia.supportwheeloffate.di.module

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.airasia.supportwheeloffate.R
import com.airasia.supportwheeloffate.RemitApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import uk.co.chrisjenx.calligraphy.CalligraphyConfig


@Module
class ApplicationModule(var remitApplication: RemitApplication) {


    @Provides
    @Singleton
    fun provideApp(): RemitApplication = remitApplication

    @Provides
    @Singleton
    fun provideContext(): Context = remitApplication.applicationContext

    @Provides
    @Singleton
    fun provideSharedPreferences(): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(remitApplication)

    @Provides
    @Singleton
    fun provideCalligraphyDefaultConfig(): CalligraphyConfig {
        return CalligraphyConfig.Builder()
            .setDefaultFontPath("fonts/Gotham Rounded Book.otf")
            .setFontAttrId(R.attr.fontPath)
            .build()
    }
}
