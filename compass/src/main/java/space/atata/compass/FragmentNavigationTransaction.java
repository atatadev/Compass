package space.atata.compass;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by atata on 11/11/2017.
 */

public class FragmentNavigationTransaction implements NavigationTransaction {
    private NavigationManager navigationManager;
    private Bundle params;
    private int transitionId;
    private View sharedElement;
    private Fragment fragment;

    public FragmentNavigationTransaction(NavigationManager navigationManager) {
        this.navigationManager = navigationManager;
    }
    @Override
    public FragmentNavigationTransaction setParams(Bundle params) {
        this.params = params;
        return this;
    }

    @Override
    public FragmentNavigationTransaction setTransitionId(int transitionId) {
        this.transitionId = transitionId;
        return this;
    }

    @Override
    public FragmentNavigationTransaction setSharedElement(View sharedElement) {
        this.sharedElement = sharedElement;
        return this;
    }

    @Override
    public void start() {

        if (sharedElement != null) {
            navigationManager.navigateTo(fragment, sharedElement);
        } else {
            navigationManager.navigateTo(fragment);
        }
    }

    @Override
    public void setTarget(Class target) throws NoSuchMethodException {
        try {
            Constructor constructor = target.getConstructor();
            fragment = (Fragment) constructor.newInstance();
        } catch (NoSuchMethodException e) {
            throw new NoSuchMethodException("Fragment should have empty constructor");
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException ignored) {
        }
    }
}
