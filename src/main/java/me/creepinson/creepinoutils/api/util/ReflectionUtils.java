package me.creepinson.creepinoutils.api.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

/**
 * Reflection utilities.
 */
public class ReflectionUtils {

    /**
     * Uses Java's reflection API to get access to an unaccessible field
     *
     * @param typeOfClass Class that the field should be read from
     * @param typeOfField The type of the field
     * @return An Object of type Field
     */
    public static Field stealField(Class<?> typeOfClass, Class<?> typeOfField) {
        Field[] fields = typeOfClass.getDeclaredFields();

        for (Field f : fields) {
            if (f.getType().equals(typeOfField)) {
                try {
                    f.setAccessible(true);
                    return f;
                } catch (Exception e) {
                    throw new RuntimeException(
                            "WorldDownloader: Couldn't steal Field of type \""
                                    + typeOfField + "\" from class \"" + typeOfClass
                                    + "\" !", e);
                }
            }
        }

        throw new RuntimeException(
                "WorldDownloader: Couldn't steal Field of type \""
                        + typeOfField + "\" from class \"" + typeOfClass
                        + "\" !");
    }

    public static <E extends Enum> E[] getEnumValues(Class<E> enumClass)
            throws NoSuchFieldException, IllegalAccessException {
        Field f = enumClass.getDeclaredField("$VALUES");
        f.setAccessible(true);
        Object o = f.get(null);
        return (E[]) o;
    }

    public static String[] getEnumKeys(Class<?> enumClass) {
        List<String> keys = new ArrayList<>();
        Field[] allFields = enumClass.getDeclaredFields();
        for (Field field : allFields) {
            if (Modifier.isPrivate(field.getModifiers()) && !field.isSynthetic()) {
                keys.add(field.getName());
            }
        }
        return keys.toArray(new String[0]);
    }

    public static List<String> getParameterNames(Method method) {
        Parameter[] parameters = method.getParameters();
        List<String> parameterNames = new ArrayList<>();

        for (Parameter parameter : parameters) {
            if (!parameter.isNamePresent()) {
                throw new IllegalArgumentException("Parameter names are not present!");
            }

            String parameterName = parameter.getName();
            parameterNames.add(parameterName);
        }

        return parameterNames;
    }


    /**
     * Uses Java's reflection API to get access to an unaccessible field
     *
     * @param object      Object that the field should be read from or the type of the
     *                    object if the field is static
     * @param typeOfField The type of the field
     * @return The value of the field
     */
    public static <T> T stealAndGetField(Object object, Class<T> typeOfField) {
        Class<?> typeOfObject;

        if (object instanceof Class<?>) { // User asked for static field:
            typeOfObject = (Class<?>) object;
            object = null;
        } else {
            typeOfObject = object.getClass();
        }

        try {
            Field f = stealField(typeOfObject, typeOfField);
            return typeOfField.cast(f.get(object));
        } catch (Exception e) {
            throw new RuntimeException(
                    "WorldDownloader: Couldn't get Field of type \""
                            + typeOfField + "\" from object \"" + object
                            + "\" !", e);
        }
    }

    /**
     * Uses Java's reflection API to set the value of an unaccessible field
     *
     * @param object      Object that the field should be read from or the type of the
     *                    object if the field is static
     * @param typeOfField The type of the field
     * @param value       The value to set the field to.
     */
    public static void stealAndSetField(Object object, Class<?> typeOfField,
                                        Object value) {
        Class<?> typeOfObject;

        if (object instanceof Class) { // User asked for static field:
            typeOfObject = (Class<?>) object;
            object = null;
        } else {
            typeOfObject = object.getClass();
        }

        try {
            Field f = stealField(typeOfObject, typeOfField);
            f.set(object, value);
        } catch (Exception e) {
            throw new RuntimeException(
                    "WorldDownloader: Couldn't set Field of type \""
                            + typeOfField + "\" from object \"" + object
                            + "\" to " + value + "!", e);
        }
    }

    /**
     * Uses Java's reflection API to get access to an unaccessible field
     *
     * @param object      Object that the field should be read from or the type of the
     *                    object if the field is static
     * @param typeOfField The type of the field
     * @return The value of the field
     */
    public static <T> T stealAndGetField(Object object, Class<?> typeOfObject,
                                         Class<T> typeOfField) {
        try {
            Field f = stealField(typeOfObject, typeOfField);
            return typeOfField.cast(f.get(object));
        } catch (Exception e) {
            throw new RuntimeException(
                    "WorldDownloader: Couldn't get Field of type \""
                            + typeOfField + "\" from object \"" + object
                            + "\" !", e);
        }
    }

    /**
     * Uses Java's reflection API to set the value of an unaccessible field
     *
     * @param object       Object that the field should be read from or the type of the
     *                     object if the field is static
     * @param typeOfObject The type that the given field is located in.
     * @param typeOfField  The type of the field
     * @param value        The value to set the field to.
     */
    public static void stealAndSetField(Object object, Class<?> typeOfObject,
                                        Class<?> typeOfField, Object value) {
        try {
            Field f = stealField(typeOfObject, typeOfField);
            f.set(object, value);
        } catch (Exception e) {
            throw new RuntimeException(
                    "WorldDownloader: Couldn't set Field of type \""
                            + typeOfField + "\" from object \"" + object
                            + "\" to " + value + "!", e);
        }
    }

}