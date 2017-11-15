package space.atata.compass;

import android.os.Bundle;
import android.view.View;

/**
 * Created by atata on 11/11/2017.
 */

public interface NavigationTransaction {
    NavigationTransaction setSharedElement(View sharedElement);
    NavigationTransaction setTransitionId(int id);
    NavigationTransaction setParams(Bundle params);
    void start();
    void setTarget(Class target) throws NoSuchMethodException;
}
