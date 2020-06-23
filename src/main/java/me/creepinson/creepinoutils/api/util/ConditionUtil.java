package me.creepinson.creepinoutils.api.util;

import java.math.BigInteger;

import static me.creepinson.creepinoutils.api.util.StringUtil.lenientFormat;

/**
 * @author Creepinson https:/theoparis.com/about
 **/
public class ConditionUtil {
    /**
     * Ensures that an object reference passed as a parameter to the calling method is not null.
     *
     * @param reference an object reference
     * @return the non-null reference that was validated
     * @throws NullPointerException if {@code reference} is null
     */
    public static <T extends Object> T checkNotNull(T reference) {
        if (reference == null) {
            throw new NullPointerException();
        }
        return reference;
    }

    /**
     * Ensures that an object reference passed as a parameter to the calling method is not null.
     *
     * @param reference    an object reference
     * @param errorMessage the exception message to use if the check fails; will be converted to a
     *                     string using {@link String#valueOf(Object)}
     * @return the non-null reference that was validated
     * @throws NullPointerException if {@code reference} is null
     */
    public static <T extends Object> T checkNotNull(
            T reference, Object errorMessage) {
        if (reference == null) {
            throw new NullPointerException(String.valueOf(errorMessage));
        }
        return reference;
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     *
     * @param expression a boolean expression
     * @throws IllegalArgumentException if {@code expression} is false
     */
    public static void checkArgument(boolean expression) {
        if (!expression) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     *
     * @param expression   a boolean expression
     * @param errorMessage the exception message to use if the check fails; will be converted to a
     *                     string using {@link String#valueOf(Object)}
     * @throws IllegalArgumentException if {@code expression} is false
     */
    public static void checkArgument(boolean expression, Object errorMessage) {
        if (!expression) {
            throw new IllegalArgumentException(String.valueOf(errorMessage));
        }
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     *
     * @param expression           a boolean expression
     * @param errorMessageTemplate a template for the exception message should the check fail. The
     *                             message is formed by replacing each {@code %s} placeholder in the template with an
     *                             argument. These are matched by position - the first {@code %s} gets {@code
     *                             errorMessageArgs[0]}, etc. Unmatched arguments will be appended to the formatted message in
     *                             square braces. Unmatched placeholders will be left as-is.
     * @param errorMessageArgs     the arguments to be substituted into the message template. Arguments
     *                             are converted to strings using {@link String#valueOf(Object)}.
     * @throws IllegalArgumentException if {@code expression} is false
     */
    public static void checkArgument(
            boolean expression,
            String errorMessageTemplate,
            Object... errorMessageArgs) {
        if (!expression) {
            throw new IllegalArgumentException(lenientFormat(errorMessageTemplate, errorMessageArgs));
        }
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     *
     * <p>See {@link #checkArgument(boolean, String, Object...)} for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    public static void checkArgument(boolean b, String errorMessageTemplate, char p1) {
        if (!b) {
            throw new IllegalArgumentException(lenientFormat(errorMessageTemplate, p1));
        }
    }

    public static int checkNonNegative(String role, int x) {
        if (x < 0) {
            throw new IllegalArgumentException(role + " (" + x + ") must be >= 0");
        }
        return x;
    }

    public static long checkNonNegative(String role, long x) {
        if (x < 0) {
            throw new IllegalArgumentException(role + " (" + x + ") must be >= 0");
        }
        return x;
    }

    public static BigInteger checkNonNegative(String role, BigInteger x) {
        if (x.signum() < 0) {
            throw new IllegalArgumentException(role + " (" + x + ") must be >= 0");
        }
        return x;
    }

    public static double checkNonNegative(String role, double x) {
        if (!(x >= 0)) { // not x < 0, to work with NaN.
            throw new IllegalArgumentException(role + " (" + x + ") must be >= 0");
        }
        return x;
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     *
     * <p>See {@link #checkArgument(boolean, String, Object...)} for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    public static void checkArgument(boolean b, String errorMessageTemplate, int p1) {
        if (!b) {
            throw new IllegalArgumentException(lenientFormat(errorMessageTemplate, p1));
        }
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     *
     * <p>See {@link #checkArgument(boolean, String, Object...)} for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    public static void checkArgument(boolean b, String errorMessageTemplate, long p1) {
        if (!b) {
            throw new IllegalArgumentException(lenientFormat(errorMessageTemplate, p1));
        }
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     *
     * <p>See {@link #checkArgument(boolean, String, Object...)} for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    public static void checkArgument(
            boolean b, String errorMessageTemplate, Object p1) {
        if (!b) {
            throw new IllegalArgumentException(lenientFormat(errorMessageTemplate, p1));
        }
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     *
     * <p>See {@link #checkArgument(boolean, String, Object...)} for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    public static void checkArgument(
            boolean b, String errorMessageTemplate, char p1, char p2) {
        if (!b) {
            throw new IllegalArgumentException(lenientFormat(errorMessageTemplate, p1, p2));
        }
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     *
     * <p>See {@link #checkArgument(boolean, String, Object...)} for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    public static void checkArgument(
            boolean b, String errorMessageTemplate, char p1, int p2) {
        if (!b) {
            throw new IllegalArgumentException(lenientFormat(errorMessageTemplate, p1, p2));
        }
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     *
     * <p>See {@link #checkArgument(boolean, String, Object...)} for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    public static void checkArgument(
            boolean b, String errorMessageTemplate, char p1, long p2) {
        if (!b) {
            throw new IllegalArgumentException(lenientFormat(errorMessageTemplate, p1, p2));
        }
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     *
     * <p>See {@link #checkArgument(boolean, String, Object...)} for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    public static void checkArgument(
            boolean b, String errorMessageTemplate, char p1, Object p2) {
        if (!b) {
            throw new IllegalArgumentException(lenientFormat(errorMessageTemplate, p1, p2));
        }
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     *
     * <p>See {@link #checkArgument(boolean, String, Object...)} for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    public static void checkArgument(
            boolean b, String errorMessageTemplate, int p1, char p2) {
        if (!b) {
            throw new IllegalArgumentException(lenientFormat(errorMessageTemplate, p1, p2));
        }
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     *
     * <p>See {@link #checkArgument(boolean, String, Object...)} for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    public static void checkArgument(
            boolean b, String errorMessageTemplate, int p1, int p2) {
        if (!b) {
            throw new IllegalArgumentException(lenientFormat(errorMessageTemplate, p1, p2));
        }
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     *
     * <p>See {@link #checkArgument(boolean, String, Object...)} for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    public static void checkArgument(
            boolean b, String errorMessageTemplate, int p1, long p2) {
        if (!b) {
            throw new IllegalArgumentException(lenientFormat(errorMessageTemplate, p1, p2));
        }
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     *
     * <p>See {@link #checkArgument(boolean, String, Object...)} for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    public static void checkArgument(
            boolean b, String errorMessageTemplate, int p1, Object p2) {
        if (!b) {
            throw new IllegalArgumentException(lenientFormat(errorMessageTemplate, p1, p2));
        }
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     *
     * <p>See {@link #checkArgument(boolean, String, Object...)} for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    public static void checkArgument(
            boolean b, String errorMessageTemplate, long p1, char p2) {
        if (!b) {
            throw new IllegalArgumentException(lenientFormat(errorMessageTemplate, p1, p2));
        }
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     *
     * <p>See {@link #checkArgument(boolean, String, Object...)} for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    public static void checkArgument(
            boolean b, String errorMessageTemplate, long p1, int p2) {
        if (!b) {
            throw new IllegalArgumentException(lenientFormat(errorMessageTemplate, p1, p2));
        }
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     *
     * <p>See {@link #checkArgument(boolean, String, Object...)} for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    public static void checkArgument(
            boolean b, String errorMessageTemplate, long p1, long p2) {
        if (!b) {
            throw new IllegalArgumentException(lenientFormat(errorMessageTemplate, p1, p2));
        }
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     *
     * <p>See {@link #checkArgument(boolean, String, Object...)} for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    public static void checkArgument(
            boolean b, String errorMessageTemplate, long p1, Object p2) {
        if (!b) {
            throw new IllegalArgumentException(lenientFormat(errorMessageTemplate, p1, p2));
        }
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     *
     * <p>See {@link #checkArgument(boolean, String, Object...)} for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    public static void checkArgument(
            boolean b, String errorMessageTemplate, Object p1, char p2) {
        if (!b) {
            throw new IllegalArgumentException(lenientFormat(errorMessageTemplate, p1, p2));
        }
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     *
     * <p>See {@link #checkArgument(boolean, String, Object...)} for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    public static void checkArgument(
            boolean b, String errorMessageTemplate, Object p1, int p2) {
        if (!b) {
            throw new IllegalArgumentException(lenientFormat(errorMessageTemplate, p1, p2));
        }
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     *
     * <p>See {@link #checkArgument(boolean, String, Object...)} for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    public static void checkArgument(
            boolean b, String errorMessageTemplate, Object p1, long p2) {
        if (!b) {
            throw new IllegalArgumentException(lenientFormat(errorMessageTemplate, p1, p2));
        }
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     *
     * <p>See {@link #checkArgument(boolean, String, Object...)} for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    public static void checkArgument(
            boolean b, String errorMessageTemplate, Object p1, Object p2) {
        if (!b) {
            throw new IllegalArgumentException(lenientFormat(errorMessageTemplate, p1, p2));
        }
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     *
     * <p>See {@link #checkArgument(boolean, String, Object...)} for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    public static void checkArgument(
            boolean b,
            String errorMessageTemplate,
            Object p1,
            Object p2,
            Object p3) {
        if (!b) {
            throw new IllegalArgumentException(lenientFormat(errorMessageTemplate, p1, p2, p3));
        }
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     *
     * <p>See {@link #checkArgument(boolean, String, Object...)} for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    public static void checkArgument(
            boolean b,
            String errorMessageTemplate,
            Object p1,
            Object p2,
            Object p3,
            Object p4) {
        if (!b) {
            throw new IllegalArgumentException(lenientFormat(errorMessageTemplate, p1, p2, p3, p4));
        }
    }
}
