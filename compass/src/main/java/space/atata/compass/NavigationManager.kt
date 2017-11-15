package space.atata.compass

import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.transition.Fade
import android.transition.TransitionInflater
import android.transition.TransitionSet
import android.view.View

/**
 * Created by atata on 11/11/2017.
 */
class NavigationManager(val activity: AppCompatActivity, val container: Int) {
    private val defaultEnterDuration = 300L
    private val defaultExitDuration = 300L
    private val defaultMoveDuration = 300L
    protected var currentFragment: Fragment? = null
    private val fragmentManager = activity.supportFragmentManager
    private var fragmentIdIterator = 0
    init {
        fragmentManager.addOnBackStackChangedListener {
            fragmentManager.fragments
                    .first()
                    ?.let { currentFragment = it }
        }
    }

    fun navigateTo(fragment: Fragment) {
        navigateTo(fragment, true)
    }

    fun navigateTo(fragment: Fragment, addToBackStack: Boolean) {
        checkPreconditions();
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(container, fragment)
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(fragment.javaClass.name + fragmentIdIterator++)
        }
        fragmentTransaction.commit()
        currentFragment = fragment;
    }

    private fun checkPreconditions() {
    }

    fun navigateTo(fragment: Fragment,
                   sharedElement: View) {
        navigateTo(fragment,true,  sharedElement)
    }

    fun navigateTo(fragment: Fragment,
                   addToBackStack: Boolean,
                   sharedElement: View) {
        navigateTo(fragment, addToBackStack, android.R.transition.move, sharedElement)
    }
    fun navigateTo(fragment: Fragment,
                   transition: Int,
                   sharedElement: View) {
        navigateTo(fragment, true, transition, sharedElement)

    }
    fun navigateTo(fragment: Fragment,
                   addToBackStack: Boolean,
                   transition: Int,
                   sharedElement: View) {
        val nextFragment = fragment
        val fragmentTransaction = fragmentManager.beginTransaction()

        // 1. Exit for Previous Fragment


        currentFragment?.let {
            val exitFade = Fade()
            exitFade.duration = defaultExitDuration
            it.setExitTransition(exitFade)
        }

        // 2. Shared Elements TransitionId
        val enterTransitionSet = TransitionSet()
        enterTransitionSet.addTransition(TransitionInflater.from(activity).inflateTransition(transition))
        enterTransitionSet.duration = defaultMoveDuration
        enterTransitionSet.startDelay = defaultExitDuration
        nextFragment.sharedElementEnterTransition = enterTransitionSet

        // 3. Enter TransitionId for New Fragment
        val enterFade = Fade()
        enterFade.startDelay = defaultMoveDuration + defaultExitDuration
        enterFade.duration = defaultEnterDuration
        nextFragment.enterTransition = enterFade

        fragmentTransaction.addSharedElement(sharedElement, sharedElement.getTransitionName())
        fragmentTransaction.replace(container, fragment)
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(fragment.javaClass.name + fragmentIdIterator++)
        }
        fragmentTransaction.commit()
        currentFragment = fragment;

    }
}