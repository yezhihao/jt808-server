package org.yzh.web.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.yzh.protocol.commons.JSATL12;
import org.yzh.protocol.commons.JT1078;
import org.yzh.protocol.commons.transform.attribute.Alarm;
import org.yzh.protocol.jsatl12.T9208;
import org.yzh.protocol.t1078.T9206;

/**
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "jt-server.jt808")
public class JTProperties {

    /** 是否启用 */
    private boolean enabled;
    private int tcpPort;
    private int udpPort;
    /** 心跳超时(秒) */
    private int idleTimeout;
    /** 消息类包名 */
    private String messagePackage;
    private C0801 t0801;
    private C9208 t9208;
    private C9206 t9206;

    @Data
    public static class C0801 {
        /** T0801多媒体数据上传 文件存储路径 */
        private String path;
    }

    @Data
    public static class C9208 {
        /** T9208报警附件上传指令 附件服务器IP地址 */
        private String host;
        /** T9208报警附件上传指令 附件服务器TCP端口 */
        private int port;
        /** 是否启用内嵌的报警附件服务 */
        private boolean enabled;
        /** 报警附件本地存放目录 */
        private String path;
    }

    @Data
    public static class C9206 {
        /** T9206文件上传指令 FTP服务器地址 */
        private String host;
        /** T9206文件上传指令 FTP端口 */
        private int port;
        /** T9206文件上传指令 FTP用户名 */
        private String username;
        /** T9206文件上传指令 FTP密码 */
        private String password;
    }

    public T9206 newT9206() {
        T9206 request = new T9206();
        request.setMessageId(JT1078.文件上传指令);
        request.setIp(t9206.host);
        request.setPort(t9206.port);
        request.setUsername(t9206.username);
        request.setPassword(t9206.password);
        return request;
    }

    public T9206 configure(T9206 request) {
        if (!StringUtils.hasText(request.getIp()))
            request.setIp(t9206.host);
        if (request.getPort() == 0)
            request.setPort(t9206.port);
        if (!StringUtils.hasText(request.getUsername()))
            request.setUsername(t9206.username);
        if (!StringUtils.hasText(request.getPassword()))
            request.setPassword(t9206.password);
        return request;
    }

    public T9208 newT9208(Alarm alarm) {
        T9208 request = new T9208();
        request.setMessageId(JSATL12.报警附件上传指令);
        request.setIp(t9208.host);
        request.setTcpPort(t9208.port);
        request.setUdpPort(t9208.port);
        request.setDateTime(alarm.getDateTime());
        request.setSequenceNo(alarm.getSequenceNo());
        request.setFileTotal(alarm.getFileTotal());
        request.setPlatformAlarmId(alarm.getPlatformAlarmId());
        return request;
    }

    public T9208 configure(T9208 request) {
        if (!StringUtils.hasText(request.getIp()))
            request.setIp(t9208.host);
        if (request.getTcpPort() == 0)
            request.setTcpPort(t9208.port);
        if (request.getUdpPort() == 0)
            request.setUdpPort(t9208.port);
        return request;
    }
}