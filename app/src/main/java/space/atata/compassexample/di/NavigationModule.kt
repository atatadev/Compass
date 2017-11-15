package space.atata.compassexample.di

import android.support.v7.app.AppCompatActivity
import dagger.Module
import dagger.Provides
import space.atata.compass.Compass
import space.atata.compassexample.MainAppNavigation
import javax.inject.Singleton

/**
 * Created by atata on 14/11/2017.
 */
@Module
class NavigationModule(val activity: AppCompatActivity, val containerId: Int) {
    @Singleton
    @Provides
    fun providesNavigation(): MainAppNavigation {
        return Compass.with(activity, containerId)
                .create(MainAppNavigation::class.java)
    }
}
