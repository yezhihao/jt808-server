 **

### 技术交流群：906230542
** 

https://github.com/yezhihao/jt808-server 

 **JT808-Server 是基于Netty和Spring Boot框架，实现了JT/T 808部标协议的服务端程序；** 

#### 整个项目分为三部分：

#### 1.以framework包为核心的，TCP/IP协议服务端，设计上效仿Spring MVC使用注解标记方法，暴露对外的TCP接口，序列化效仿Hibernate使用注解标注字段的类型和所处的位置。** 

核心的注解有三个：

@org.yzh.framework.annotation.Endpoint，标记TCP服务的接入点，相当于SpringMVC中的 @Controller；

@org.yzh.framework.annotation.Mapping，types中定义消息ID，相当于SpringMVC中 @RequestMapping；

@org.yzh.framework.annotation.Property，定义协议中各个字段的类型和占用的字节位置，相当于Hibernate中 @Column，

MessageDecoder、MessageEncoder实现了对@Property的处理，
DefaultHandlerMapper(没有Spring的环境中)、SpringHandlerMapper实现了对@Endpoint和@Mapping的处理。


#### 2.以web包为核心的，Http协议服务端。

TerminalController使用SpringMVC开放Restful API，接收对设备发送的请求，收到请求后通过JT808Endpoint中的send方法，将对象序列化为符合808协议的报文通过Netty服务发送至终端设备。

JT808Endpoint通过TCPServer统一接收终端发送的请求，由TCPServerHandler解析报文头得到消息ID，
HandlerMapper根据不同的消息ID找到对应的Handler，也就是JT808Endpoint中被@Mapping标注的方法。


#### 3.Test目录下为JT/T 808协议的测试用例和报文解释器

org.yzh.jt808.codec.Elucidator（报文解释器）

可分析报文内每个属性所处的位置以及转换后的值，以便查询报文解析出错的原因。

运行效果如下：
```
0200002d010000000000007b000000070000000600000001000000020003000400051904061915541206000000000000110100e3040000000bfe

0       0200		消息ID	512
2	002d		消息体属性	45
4	010000000000		终端手机号	010000000000
10	007b		流水号	123
12	0000		消息包总数	0
14	0007		包序号	7

0	00000007		报警标志	7
4	00000006		状态	6
8	00000001		纬度	1
12	00000002		经度	2
16	0003		海拔	3
18	0004		速度	4
20	0005		方向	5
22	190406191554		时间	190406191554
28	1206000000000000110100e3040000000b		位置附加信息	
0	12		参数ID	18
1	06		参数长度	6
2	000000000000		参数值	[B@33afa13b
0	11		参数ID	17
1	01		参数长度	1
2	00		参数值	[B@7a4ccb53
0	e3		参数ID	227
1	04		参数长度	4
2	0000000b		参数值	[B@309e345f[PositionAttribute[id=18,length=6,bytesValue={0,0,0,0,0,0},value=<null>], PositionAttribute[id=17,length=1,bytesValue={0},value=<null>], PositionAttribute[id=227,length=4,bytesValue={0,0,0,11},value=<null>]]
```

![使用发包工具模拟请求](https://images.gitee.com/uploads/images/2019/0705/162745_9becaf08_670717.png)
使用发包工具模拟请求

7e0200002d010000000000007b000000070000000600000001000000020003000400051904061915541206000000000000110100e3040000000bfe7e



