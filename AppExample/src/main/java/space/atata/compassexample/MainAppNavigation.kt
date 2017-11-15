package space.atata.compassexample

import android.view.View
import space.atata.argentum.presentation.main.MainFragment
import space.atata.argentum.presentation.splash.SplashFragment
import space.atata.compass.settings.Fragment
import space.atata.compass.settings.SharedElement

/**
 * Created by atata on 14/11/2017.
 */
interface MainAppNavigation {
    @Fragment(SplashFragment::class)
    fun openSplash()
    @Fragment(MainFragment::class)
    fun openMain(@SharedElement sharedElement: View)
}