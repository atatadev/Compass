package space.atata.compass

import android.support.v4.app.Fragment

/**
 * Created by atata on 11/11/2017.
 */
class Route<T: Fragment>(val navigationManager: NavigationManager, val fragment: T) {
    private var addToBackStack = false

//    fun notSaveNextAction(): Route<T> {
//        this.addToBackStack = false
//        return this
//    }
//
//    fun open() {
//        navigationManager.navigateTo(fragment, addToBackStack)
//        addToBackStack = true
//    }
//
//    fun open(sharedElement: View) {
//        navigationManager.navigateTo(fragment, addToBackStack, sharedElement)
//        addToBackStack = true
//    }
//
//    fun open(sharedElement: View, transition: Int) {
//        navigationManager.navigateTo(fragment, addToBackStack, transition, sharedElement)
//        addToBackStack = true
//    }
}

