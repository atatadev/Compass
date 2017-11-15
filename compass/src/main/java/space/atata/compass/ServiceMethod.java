package space.atata.compass;

import android.os.Bundle;
import android.view.View;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import space.atata.compass.settings.Activity;
import space.atata.compass.settings.Fragment;
import space.atata.compass.settings.Params;
import space.atata.compass.settings.SharedElement;
import space.atata.compass.settings.TransitionId;

/**
 * Created by atata on 11/11/2017.
 */

public class ServiceMethod {

    private final Method method;
    private final Class activity;
    private final Class fragment;
    private final ArgumentHandler[] argumentHandlers;
    private final NavigationManager navigationManager;
    private final Class<?> returnType;

    public ServiceMethod(Builder builder) {
        method = builder.method;
        activity = builder.activity;
        fragment = builder.fragment;
        argumentHandlers = builder.argumentHandlers;
        navigationManager = builder.navigationManager;
        returnType = builder.returnType;
    }

    NavigationTransaction invoke(Object[] arguments) throws NoSuchMethodException {

        NavigationTransaction transaction;
        if(fragment != null) {
            transaction = new FragmentNavigationTransaction(navigationManager);
            transaction.setTarget(fragment);
        } else {
            transaction = new ActivityNavigationTransaction(navigationManager);
            transaction.setTarget(activity);
        }
        if (arguments != null) {
            if(arguments.length != argumentHandlers.length) {
                throw new IllegalArgumentException("Argument count of doesn't match expected count");
            }
            for (int i = 0; i < arguments.length; i++) {
                argumentHandlers[i].handle(transaction, arguments[i]);
            }
        }
        return transaction;
    }

    public boolean returnTransaction() {
        return returnType != void.class;
    }



    static class Builder {
        private final Annotation[] methodAnnotations;
        private final Type[] parameterTypes;
        private final Annotation[][] parameterAnnotationsArray;
        private final NavigationManager navigationManager;
        private Method method;
        private Class activity;
        private Class fragment;
        private ArgumentHandler[] argumentHandlers;
        private Class<?> returnType;


        public Builder(NavigationManager navigationManager, Method method) {
            this.navigationManager = navigationManager;
            this.method = method;
            this.methodAnnotations = method.getAnnotations();
            this.parameterTypes = method.getGenericParameterTypes();
            this.parameterAnnotationsArray = method.getParameterAnnotations();
        }

        public ServiceMethod build() {
            for (Annotation methodAnnotation : methodAnnotations) {
                handleMethodAnnotation(methodAnnotation);
            }
            argumentHandlers = new ArgumentHandler[parameterAnnotationsArray.length];
            for (int i = 0; i < parameterAnnotationsArray.length; i++) {
                argumentHandlers[i] = handleParameterAnnotations(parameterTypes[i], parameterAnnotationsArray[i]);
            }
            returnType = method.getReturnType();

            return new ServiceMethod(this);
        }

        private ArgumentHandler handleParameterAnnotations(Type parameterType, Annotation[] annotations) {
            for (Annotation annotation : annotations) {
                if (annotation instanceof Params) {
                    if (parameterType == Bundle.class) {
                        return new ParamsHandler();
                    } else {
                        throw new IllegalArgumentException("Argument 'Params' should have Bundle type");
                    }
                }
                if (annotation instanceof SharedElement) {
                    if (parameterType == View.class) {
                        return new SharedElementHandler();
                    } else {
                        throw new IllegalArgumentException("Argument 'SharedElement' should have view type");
                    }
                }
                if (annotation instanceof TransitionId) {
                    if (parameterType == int.class) {
                        return new TransitionHandler();
                    } else {
                        throw new IllegalArgumentException("Argument 'TransitionId' should have Bundle type");
                    }
                }
            }
            return null;
        }


        private void handleMethodAnnotation(Annotation annotation) {
            if (annotation instanceof Activity) {
                this.activity = ((Activity) annotation).value();
            }

            if(annotation instanceof Fragment) {
                this.fragment = ((Fragment) annotation).value();
            }
            if(fragment == null && activity == null) {
                throw new RuntimeException("at least one target should be specified");

            }
            if(fragment != null && activity != null) {
                throw new RuntimeException("only one target should be specified");
            }

        }


    }

    interface ArgumentHandler<T> {
        void handle(NavigationTransaction transaction, T argument);
    }

    static class ParamsHandler implements ArgumentHandler<Bundle> {

        @Override
        public void handle(NavigationTransaction transaction, Bundle argument) {
            transaction.setParams(argument);
        }
    }
    static class TransitionHandler implements ArgumentHandler<Integer> {

        @Override
        public void handle(NavigationTransaction transaction, Integer argument) {
            transaction.setTransitionId(argument);
        }
    }

    static class SharedElementHandler implements ArgumentHandler<View> {

        @Override
        public void handle(NavigationTransaction transaction, View argument) {
            transaction.setSharedElement(argument);
        }
    }



}
