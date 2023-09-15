package net.alive.api.event.utility;

import net.alive.api.event.listener.IListener;

import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author SoonTM
 * @since 8/4/2019
 */
public final class ReflectionUtil {

    @SuppressWarnings("unchecked")
    public static <T> Class<T> resolveListenerTarget(IListener<T> listener) {
        final Class<? extends IListener> subType = listener.getClass();
        if (subType.isSynthetic()) {
            return (Class<T>) getLambdaMethod(getSerializedLambda(listener)).getParameterTypes()[0];
        } else {
            ParameterizedType lookupType;
            for (Type type : subType.getGenericInterfaces()) {
                if (type instanceof ParameterizedType && (lookupType = ((ParameterizedType) type)).getRawType().equals(IListener.class)) {
                    return (Class<T>) lookupType.getActualTypeArguments()[0];
                }
            }
        }
        throw new RuntimeException("Unable to resolve the target of the supplied listener");
    }

    private static SerializedLambda getSerializedLambda(IListener<?> listener) {
        for (Class<?> clazz = listener.getClass(); clazz != null; clazz = clazz.getSuperclass()) {
            try {
                final Method replaceMethod = clazz.getDeclaredMethod("writeReplace");
                replaceMethod.setAccessible(true);
                return (SerializedLambda) replaceMethod.invoke(listener);
            } catch (NoSuchMethodException e) {
                /* fall through the loop and try the next class */
            } catch (Throwable t) {
                throw new RuntimeException("Error while extracting serialized lambda", t);
            }
        }
        throw new RuntimeException("writeReplace method not found");
    }

    private static Method getLambdaMethod(SerializedLambda lambda) {
        Class<?> implClass; String implClassName = lambda.getImplClass().replace('/', '.');
        try {
            implClass = Class.forName(implClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to instantiate lambda class");
        }
        final String lambdaName = lambda.getImplMethodName();
        for (Method m : implClass.getDeclaredMethods()) {
            if (m.getName().equals(lambdaName)) {
                return m;
            }
        }
        throw new RuntimeException("Lambda Method not found");
    }
}
