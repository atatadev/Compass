Retrofit like navigation lib.
Usage:
```
interface MainAppNavigation {
    @Fragment(SplashFragment::class)
    fun openSplash()
    @Fragment(MainFragment::class)
    fun openMain(@SharedElement sharedElement: View)
}

 val navigation = Compass.with(activity, containerId)
                .create(MainAppNavigation::class.java)
navigation.openSplash() //simple opens SpalshFragment opens
navigation.openMain(binding.logo) //opens MainFragment with shared transition of logo 
```             
TODO:
* implement more params
* complete description
* add installation guide
