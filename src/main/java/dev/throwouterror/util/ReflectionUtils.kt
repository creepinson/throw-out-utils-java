package dev.throwouterror.util

import java.lang.reflect.Field
import java.lang.reflect.Method
import java.lang.reflect.Modifier
import java.util.*

/**
 * Reflection utilities.
 */
object ReflectionUtils {
    /**
     * Uses Java's reflection API to get access to an unaccessible field
     *
     * @param typeOfClass Class that the field should be read from
     * @param typeOfField The type of the field
     * @return An Object of type Field
     */
    fun stealField(typeOfClass: Class<*>, typeOfField: Class<*>): Field {
        val fields = typeOfClass.declaredFields
        for (f in fields) {
            if (f.type == typeOfField) {
                return try {
                    f.isAccessible = true
                    f
                } catch (e: Exception) {
                    throw RuntimeException(
                            "Couldn't steal Field of type \""
                                    + typeOfField + "\" from class \"" + typeOfClass
                                    + "\" !", e)
                }
            }
        }
        throw RuntimeException(
                "Couldn't steal Field of type \""
                        + typeOfField + "\" from class \"" + typeOfClass
                        + "\" !")
    }

    @Throws(NoSuchFieldException::class, IllegalAccessException::class)
    fun <E : Enum<*>?> getEnumValues(enumClass: Class<E>): Array<E> {
        val f = enumClass.getDeclaredField("\$VALUES")
        f.isAccessible = true
        val o = f[null]
        return o as Array<E>
    }

    fun getEnumKeys(enumClass: Class<*>): Array<String> {
        val keys: MutableList<String> = ArrayList()
        val allFields = enumClass.declaredFields
        for (field in allFields) {
            if (Modifier.isPrivate(field.modifiers) && !field.isSynthetic) {
                keys.add(field.name)
            }
        }
        return keys.toTypedArray()
    }

    fun getParameterNames(method: Method): List<String> {
        val parameters = method.parameters
        val parameterNames: MutableList<String> = ArrayList()
        for (parameter in parameters) {
            require(parameter.isNamePresent) { "Parameter names are not present!" }
            val parameterName = parameter.name
            parameterNames.add(parameterName)
        }
        return parameterNames
    }

    /**
     * Uses Java's reflection API to get access to an unaccessible field
     *
     * @param object      Object that the field should be read from or the type of the
     * object if the field is static
     * @param typeOfField The type of the field
     * @return The value of the field
     */
    fun <T> stealAndGetField(`object`: Any, typeOfField: Class<T>): T {
        val typeOfObject: Class<*> = if (`object` is Class<*>) { // User asked for static field:
            `object`
        } else {
            `object`.javaClass
        }
        return try {
            val f = stealField(typeOfObject, typeOfField)
            typeOfField.cast(f[`object`])
        } catch (e: Exception) {
            throw RuntimeException(
                    "Couldn't get Field of type \""
                            + typeOfField + "\" from object \"" + `object`
                            + "\" !", e)
        }
    }

    /**
     * Uses Java's reflection API to set the value of an unaccessible field
     *
     * @param object      Object that the field should be read from or the type of the
     * object if the field is static
     * @param typeOfField The type of the field
     * @param value       The value to set the field to.
     */
    fun stealAndSetField(`object`: Any, typeOfField: Class<*>,
                         value: Any) {
        val typeOfObject: Class<*> = if (`object` is Class<*>) { // User asked for static field:
            `object`
        } else {
            `object`.javaClass
        }
        try {
            val f = stealField(typeOfObject, typeOfField)
            f[`object`] = value
        } catch (e: Exception) {
            throw RuntimeException(
                    "Couldn't set Field of type \""
                            + typeOfField + "\" from object \"" + `object`
                            + "\" to " + value + "!", e)
        }
    }

    /**
     * Uses Java's reflection API to get access to an unaccessible field
     *
     * @param object      Object that the field should be read from or the type of the
     * object if the field is static
     * @param typeOfField The type of the field
     * @return The value of the field
     */
    fun <T> stealAndGetField(`object`: Any, typeOfObject: Class<*>,
                             typeOfField: Class<T>): T {
        return try {
            val f = stealField(typeOfObject, typeOfField)
            typeOfField.cast(f[`object`])
        } catch (e: Exception) {
            throw RuntimeException(
                    "Couldn't get Field of type \""
                            + typeOfField + "\" from object \"" + `object`
                            + "\" !", e)
        }
    }

    /**
     * Uses Java's reflection API to set the value of an unaccessible field
     *
     * @param object       Object that the field should be read from or the type of the
     * object if the field is static
     * @param typeOfObject The type that the given field is located in.
     * @param typeOfField  The type of the field
     * @param value        The value to set the field to.
     */
    fun stealAndSetField(`object`: Any, typeOfObject: Class<*>,
                         typeOfField: Class<*>, value: Any) {
        try {
            val f = stealField(typeOfObject, typeOfField)
            f[`object`] = value
        } catch (e: Exception) {
            throw RuntimeException(
                    "Couldn't set Field of type \""
                            + typeOfField + "\" from object \"" + `object`
                            + "\" to " + value + "!", e)
        }
    }
}