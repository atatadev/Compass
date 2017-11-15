package space.atata.compassexample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import space.atata.compassexample.di.DaggerNavigationComponent
import space.atata.compassexample.di.NavigationComponent
import space.atata.compassexample.di.NavigationModule
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    lateinit var component: NavigationComponent
    @Inject internal lateinit var navigation: MainAppNavigation
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        component = DaggerNavigationComponent.builder()
                .navigationModule(NavigationModule(this, R.id.container))
                .build()
        component.inject(this)
        if (savedInstanceState == null) {
            navigation.openSplash()
        }
    }
}
