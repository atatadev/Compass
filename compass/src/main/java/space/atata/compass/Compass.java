package space.atata.compass;

import android.support.v7.app.AppCompatActivity;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by atata on 11/11/2017.
 */

public class Compass {
    private final Map<Method, ServiceMethod> methodCache = new ConcurrentHashMap<>();
    private NavigationManager navigationManager;
    private Compass(NavigationManager navigationManager){
        this.navigationManager = navigationManager;
    }
    public static Compass with(AppCompatActivity activity, int container) {
        return new Compass(new NavigationManager(activity, container));
    }


    public <T> T create(final Class<T> navigation) {
        return (T) Proxy.newProxyInstance(navigation.getClassLoader(), new Class<?>[]{navigation},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                        if (method.getDeclaringClass() == Object.class) {
                            return method.invoke(this, objects);
                        }
                        ServiceMethod serviceMethod = loadMethod(method);
                        NavigationTransaction transaction = serviceMethod.invoke(objects);
                        if (serviceMethod.returnTransaction()) {
                            return transaction;
                        } else {
                            transaction.start();
                        }
                        return null;
                    }
                }
        );

    }


    private ServiceMethod loadMethod(Method method) {
        ServiceMethod serviceMethod = methodCache.get(method);
        synchronized (methodCache) {
            if (serviceMethod == null) {
                serviceMethod = new ServiceMethod.Builder(navigationManager, method).build();
                methodCache.put(method, serviceMethod);
            }
        }
        return serviceMethod;
    }
}
