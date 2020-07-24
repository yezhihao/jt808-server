package org.yzh.framework;

import org.yzh.framework.netty.JTConfig;
import org.yzh.framework.netty.TCPServer;

public final class JTApplication {

    public static void run(JTConfig... jtConfig) {
        for (JTConfig config : jtConfig) {
            new TCPServer(config).startServer();
        }
    }
}
