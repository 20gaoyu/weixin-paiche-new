package cn.gy.util;


import cn.gy.core.web.Env;

public class EnvUtil {
    private static ThreadLocal<Env> threadLocal = new ThreadLocal<>();

    public static void setEnv(Env env) {
        threadLocal.set(env);
    }

    public static Env getEnv() {
        return threadLocal.get();
    }

    public static void removeEnv() {
        threadLocal.remove();
    }

}
