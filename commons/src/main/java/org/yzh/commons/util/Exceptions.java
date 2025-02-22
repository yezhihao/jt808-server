package org.yzh.commons.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public class Exceptions {

    public static String getStackTrace(Throwable error) {
        if (error == null)
            return null;
        StringWriter stackTrace = new StringWriter(7680);
        error.printStackTrace(new PrintWriter(stackTrace, true));
        stackTrace.flush();
        return stackTrace.toString();
    }

    public static <R> R ignore(Func<R, ?> f) {
        try {
            return f.apply();
        } catch (Throwable ignored) {
            return null;
        }
    }

    public static void ignore(Call<?> f) {
        try {
            f.apply();
        } catch (Throwable ignored) {
        }
    }

    @SuppressWarnings("unchecked")
    public static <E extends Throwable> void sneaky(Throwable e) throws E {
        throw (E) e;
    }

    @SuppressWarnings("unchecked")
    public static <R> R sneaky(Func<R, ?> f) {
        return ((Func<R, RuntimeException>) f).apply();
    }

    @SuppressWarnings("unchecked")
    public static void sneaky(Call<?> f) {
        ((Call<RuntimeException>) f).apply();
    }

    @FunctionalInterface
    public interface Func<R, E extends Throwable> {
        R apply() throws E;
    }

    @FunctionalInterface
    public interface Call<E extends Throwable> {
        void apply() throws E;
    }
}
