部标JT808 JT/T808协议快速开发包
====================

<p align="center">
    <img src="https://img.shields.io/badge/JDK-1.8+-green.svg"></img>
    <img src="https://img.shields.io/badge/License-Apache 2.0-green.svg"></img>
    <img src="https://img.shields.io/badge/QQ群-906230542-blue"></img>
</p>

# 项目介绍
* 基于Netty，实现JT808 JT/T808部标协议的消息处理，与编码解码；
* 使用SpringBoot + MyBatis提供数据入库、Web接口服务；
* 协议部分不依赖Spring，可移除Spring独立运行（支持Android客户端）；
* 最简洁、清爽、易用的部标开发框架。

# 主要特性
* 代码足够精简，便于二次开发；
* 致敬Spring、Hibernate设计理念，熟悉Web开发的同学上手极快；
* 使用注解描述协议，告别繁琐的封包、解包；
* 支持异步批量处理，显著提升MySQL入库性能；
* 提供报文解释器（解析过程分析工具），编码解码不再抓瞎；
* 全覆盖的测试用例，稳定发版。

# 协议支持
|协议名称|版本|是否支持|备注|
|---|---|---|---|
|JT/T 808|2011|支持|
|JT/T 808|2013|支持|
|JT/T 808|2019|支持|
|JT/T 1078|2016|支持|需自建流媒体服务|
|T/JSATL 12(主动安全-苏标)|2017|支持|基于JT/T808-2013|
|T/GDRTA 002(主动安全-粤标)|2019|后续支持|基于JT/T808-2019|

备注：无需手动配置，同时兼容2011、2013、2019协议版本，支持分包请求、分包应答及超时分包补传。
1078协议支持音视频指令，流媒体服务需自行搭建。

# 代码仓库
 * Gitee仓库地址：[https://gitee.com/yezhihao/jt808-server/tree/master](https://gitee.com/yezhihao/jt808-server/tree/master)
 * Github仓库地址：[https://github.com/yezhihao/jt808-server/tree/master](https://github.com/yezhihao/jt808-server/tree/master)

# 验证步骤

## 1.验证消息定义
编码分析工具：DarkRepulsor（对象 => 报文）

解码分析工具：Elucidator（报文 => 对象）

使用src\test\java\codec\DarkRepulsor
分析报文内每个属性所处的位置以及转换后的值，以便查询报文解析出错的原因
```java
package org.yzh.codec;

public class DarkRepulsor {

    private static JTMessageEncoder encoder;

    static {
        FieldFactory.EXPLAIN = true;
        encoder = new JTMessageEncoder("org.yzh.protocol");
    }

    public static void main(String[] args) {
        ByteBuf byteBuf = encoder.encode(new T0200());
        System.out.println();
        System.out.println(ByteBufUtil.hexDump(byteBuf));
    }
}
```
DarkRepulsor 运行效果如下：
```
0	[001f] 省域ID: 31
2	[0073] 市县域ID: 115
4	[0000000034] 制造商ID: 4
9	[000000000000000000000042534a2d47462d3036] 终端型号: BSJ-GF-06
29	[74657374313233] 终端ID: test123
36	[01] 车牌颜色: 1
37	[b2e241383838383838] 车辆标识: 测A888888
0	[0100] 消息ID: 256
2	[002e] 消息体属性: 46
4	[012345678901] 终端手机号: 12345678901
10	[7fff] 流水号: 32767

7e0100002e0123456789017fff001f00730000000034000000000000000000000042534a2d47462d30367465737431323301b2e241383838383838157e
```
## 2.模拟设备请求
运行src\test\resources\发包工具.exe
1. 协议类型：【TCP Client】
2. 远程主机地址：127.0.0.1
3. 远程主机端口：7611
4. 接收设置：⊙HEX
5. 发送设置：⊙HEX
6. 点击按钮【连接】
7. 将上一个步骤中生成的报文，粘贴到文本框
8. 点击按钮【发送】

注意：先选择HEX，再粘贴报文
```
7e0100002e0123456789017fff001f00730000000034000000000000000000000042534a2d47462d30367465737431323301b2e241383838383838157e
```
如下图所示
![使用发包工具模拟请求](https://images.gitee.com/uploads/images/2020/1231/150635_85de7ac4_670717.jpeg)

## 3.下发命令到终端
 
已集成OpenAPI文档，启动后可访问如下地址
* Knife4j UI：[http://127.0.0.1:8000/doc.html](http://127.0.0.1:8000/doc.html)
* Swagger UI：[http://127.0.0.1:8000/swagger-ui/](http://127.0.0.1:8000/swagger-ui/)

录入参数，点击发送
![Knife4j UI](https://images.gitee.com/uploads/images/2020/1231/115947_bb39bcd0_670717.jpeg)
 
* 设备消息监控：[http://127.0.0.1:8000/ws.html](http://127.0.0.1:8000/ws.html)

![Console](https://images.gitee.com/uploads/images/2021/0714/171301_9f44b193_670717.jpeg)

# 开发步骤

## 1.定义消息
 ```java
package org.yzh.protocol.t808;

@Message(JT808.终端注册)
public class T0100 extends JTMessage {

    @Field(index = 0, type = DataType.WORD, desc = "省域ID")
    private int provinceId;
    @Field(index = 2, type = DataType.WORD, desc = "市县域ID")
    private int cityId;
    @Field(index = 4, type = DataType.BYTES, length = 11, desc = "制造商ID")
    private String makerId;
    @Field(index = 15, type = DataType.BYTES, length = 30, desc = "终端型号")
    private String deviceModel;
    @Field(index = 45, type = DataType.BYTES, length = 30, desc = "终端ID")
    private String deviceId;
    @Field(index = 75, type = DataType.BYTE, desc = "车牌颜色：0.未上车牌 1.蓝色 2.黄色 3.黑色 4.白色 9.其他")
    private int plateColor;
    @Field(index = 76, type = DataType.STRING, desc = "车辆标识")
    private String plateNo;
}
```

## 2.处理终端上报的消息
```java
package org.yzh.web.endpoint;

@Endpoint
public class JT808Endpoint {
    
    @Autowired
    private DeviceService deviceService;

    @Mapping(types = 0x0100, desc = "终端注册")
    public T8100 register(T0100 message, Session session) {
        T8100 result = new T8100();
        result.setResponseSerialNo(message.getSerialNo());

        DeviceInfo device = deviceService.register(message);
        if (device != null) {
            session.register(message);

            result.setToken("1234567890A");
            result.setResultCode(T8100.Success);
        } else {
            result.setResultCode(T8100.NotFoundTerminal);
        }
        return result;
    }
}
```

## 3.下发消息到终端（通过Web接口）
```java
package org.yzh.web.controller;

@RestController
@RequestMapping("terminal")
public class JT808Controller {

    @Autowired
    private MessageManager messageManager;

    @Operation(summary = "8103 设置终端参数")
    @PutMapping("parameters")
    public T0001 setParameters(@Parameter(description = "终端手机号") @RequestParam String clientId, @RequestBody Parameters parameters) {
        Map<Integer, Object> map = parameters.toMap();
        T8103 request = new T8103(map);
        T0001 response = messageManager.request(clientId, request, T0001.class);
        return response;
    }
}
```

注解说明：
* @Message，消息类型，等价Hibernate的 @Table
* @Field，消息属性，等价Hibernate的 @Column

* @Endpoint，消息接入点，等价SpringMVC的 @Controller
* @Mapping，消息映射到方法，等价SpringMVC中 @RequestMapping
* @AsyncBatch，消息批量处理，对于高并发的消息（例如：位置信息汇报），合并同类消息，提升入库性能。

目录结构
```sh
src
│      
├──main
│     ├── protocol
│     │   ├── t808 消息定义
│     │   └── codec 消息编码解码
│     └── web
│         ├── config 808服务配置项
│         └── endpoint 808消息入口，通过netty收到的请求会根据@Mapping转发到此
│         
└──test
      ├── java
      │   ├── codec 解析工具
      │   └── protocol 单元测试
      └── resources
          ├── 协议文档.pdf
          └── 发包工具.exe
 ```

项目会不定期进行更新，建议star和watch一份，您的支持是我最大的动力。

如有任何疑问或者BUG，请联系我，非常感谢。

另提供技术支持、协议扩展、数据入库、二次开发等服务。

项目负责人QQ：[1527621790]

技术交流QQ群：[906230542]

- JT808泛指JT/T808协议，是指交通部制定的运输行业通信标准，全称《交通运输行业标准 - 道路运输车辆卫星定位系统终端通信协议及数据格式》


项目创立于2017年9月，至今，jt808-server已接入多家公司的线上产品线，接入场景如车辆管理平台，IOT业务和大数据作业等，截止最新统计时间为止，jt808-server已接入的公司包括不限于：
    
	- 1.福建九桃贸易有限公司
	- 2.深圳市特维视科技有限公司
	- 3.厦门河联信息科技有限公司
	- 4.北京华盾互联科技有限公司
    - ……
    
> 更多接入的公司，欢迎在 [登记地址](https://gitee.com/yezhihao/jt808-server/issues/I36WKD ) 登记，登记仅仅为了项目推广。