package space.atata.compassexample.di

import dagger.Component
import space.atata.argentum.presentation.main.MainFragment
import space.atata.argentum.presentation.splash.SplashFragment
import space.atata.compassexample.MainActivity
import space.atata.compassexample.MainAppNavigation
import javax.inject.Singleton

/**
 * Created by atata on 14/11/2017.
 */
@Singleton
@Component(modules = arrayOf(NavigationModule::class))
interface NavigationComponent {
    fun inject(splashFragment: SplashFragment)
    fun inject(mainFragment: MainFragment)
    fun inject(mainActivity: MainActivity)
    fun mainAppNavigation(): MainAppNavigation
}
