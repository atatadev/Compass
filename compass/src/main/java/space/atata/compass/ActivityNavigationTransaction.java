package space.atata.compass;

import android.os.Bundle;
import android.view.View;

/**
 * Created by atata on 11/11/2017.
 */

public class ActivityNavigationTransaction implements NavigationTransaction {
    private NavigationManager navigationManager;
    private Bundle params;
    private int transitionId;
    private View sharedElement;
    private Class activity;

    public ActivityNavigationTransaction(NavigationManager navigationManager) {
        this.navigationManager = navigationManager;
    }


    @Override
    public NavigationTransaction setSharedElement(View sharedElement) {
        this.sharedElement = sharedElement;
        return this;
    }

    @Override
    public NavigationTransaction setTransitionId(int id) {
        transitionId = id;
        return this;
    }

    @Override
    public NavigationTransaction setParams(Bundle params) {
        this.params = params;
        return this;
    }

    @Override
    public void start() {

    }

    @Override
    public void setTarget(Class target) throws NoSuchMethodException {
       activity = target;
    }
}
