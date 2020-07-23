package org.yzh.framework;

import org.yzh.framework.core.ClassHelper;
import org.yzh.framework.core.JTConfig;
import org.yzh.framework.core.TCPServer;

public final class JTApplication {

    public static void run(Class aClass, JTConfig jtConfig) {
        ClassHelper.init(aClass.getPackage().getName());
        new TCPServer(jtConfig).startServer();
    }
}
