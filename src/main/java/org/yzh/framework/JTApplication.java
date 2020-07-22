package org.yzh.framework;

import org.yzh.framework.commons.ClassUtils;
import org.yzh.framework.core.ClassHelper;
import org.yzh.framework.core.JTConfig;
import org.yzh.framework.core.TCPServer;
import org.yzh.framework.mvc.HandlerMapping;
import org.yzh.framework.orm.MessageHelper;

public final class JTApplication {

    public static void run(Class aClass, JTConfig jtConfig) {
        ClassHelper.init(aClass.getPackage().getName());

        Class<?>[] classList = {
                HandlerMapping.class,
                MessageHelper.class
        };

        for (Class<?> cls : classList) {
            ClassUtils.loadClass(cls.getName(), true);
        }

        new TCPServer(
                jtConfig.getPort(),
                jtConfig.getDecoder(),
                jtConfig.getEncoder(),
                jtConfig.getDelimiterBasedFrameDecoder()
        ).startServer();
    }
}
