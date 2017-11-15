package space.atata.argentum.presentation.splash


import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import space.atata.compassexample.MainActivity
import space.atata.compassexample.MainAppNavigation
import space.atata.compassexample.databinding.FragmentSplashBinding
import javax.inject.Inject


class SplashFragment : Fragment() {
    private var binding: FragmentSplashBinding? = null
    @Inject internal lateinit var navigation: MainAppNavigation

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        val mainActivity = activity as MainActivity
        mainActivity.component.inject(this)
        Handler().postDelayed( this::openMain, 3000)
        return binding?.root
    }

    private fun openMain() {
        binding?.let {
            navigation.openMain(it.logo)
        }
    }
    companion object {
        @JvmStatic
        fun newInstance(): SplashFragment {
            return SplashFragment()
        }
    }
}
