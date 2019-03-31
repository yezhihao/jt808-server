https://github.com/YeZhihao/jt808-server

 **JT808-Server 是基于Netty和Spring Boot框架，实现了JT/T 808部标协议的服务端程序；** 

整个项目分为两部分：

 **1.以framework包为核心的，TCP/IP协议服务端，设计上效仿Spring MVC使用注解标记方法，暴露对外的TCP接口，序列化效仿Hibernate使用注解标注字段的类型和所处的位置。** 

核心的注解有三个：

@org.yzh.framework.annotation.Endpoint，标记TCP服务的接入点，相当于SpringMVC中的 @Controller；

@org.yzh.framework.annotation.Mapping，types中定义消息ID，相当于SpringMVC中 @RequestMapping；

@org.yzh.framework.annotation.Property，定义协议中各个字段的类型和占用的字节位置，相当于Hibernate中 @Column，

MessageDecoder、MessageEncoder实现了对@Property的处理，
DefaultHandlerMapper(没有Spring的环境中)、SpringHandlerMapper实现了对@Endpoint和@Mapping的处理。


 **2.以web包为核心的，Http协议服务端。** 

TerminalController使用SpringMVC开放Restful API，接收对设备发送的请求，收到请求后通过JT808Endpoint中的send方法，将对象序列化为符合808协议的报文通过Netty服务发送至终端设备。

JT808Endpoint通过TCPServer统一接收终端发送的请求，由TCPServerHandler解析报文头得到消息ID，
HandlerMapper根据不同的消息ID找到对应的Handler，也就是JT808Endpoint中被@Mapping标注的方法。
