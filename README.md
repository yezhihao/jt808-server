部标JT808 808协议网关
====================

<p>
    <img src="https://img.shields.io/badge/JDK-1.8+-green.svg"/>
    <img src="https://img.shields.io/badge/License-Apache 2.0-green.svg"/>
    <img src="https://img.shields.io/badge/QQ群-323100134-blue"/>
</p>

# 项目介绍
* 基于Netty，实现JT808 808协议、1078协议、苏标、粤标的消息处理，与编码解码；
* 无需修改代码，同时支持TCP、UDP协议；
* 使用Spring WebFlux 提供支持高并发的Web接口服务；
* 不依赖Spring，可移除Spring独立运行（编码解码可支持Android）；
* 最简洁、清爽、易用的部标开发框架。

# 主要特性
* 代码足够精简，便于二次开发；
* 致敬Spring、Hibernate设计理念，熟悉Web开发的同学上手极快；
* 使用注解描述协议，告别繁琐的封包、解包；
* 支持异步批量处理，显著提升MySQL入库性能；
* 提供报文解释器（解析过程分析工具），编码解码不再抓瞎；
* 全覆盖的测试用例，稳定发版。

# 协议支持（传输层协议支持TCP、UDP）
|协议名称|版本|是否支持|备注|
|---|---|---|---|
|JT/T 808|2011|支持|
|JT/T 808|2013|支持|
|JT/T 808|2019|支持|
|JT/T 1078|2016|支持|需自建流媒体服务|
|T/JSATL 12(主动安全-苏标)|2017|支持|基于JT/T808-2013|
|T/GDRTA 002(主动安全-粤标)|2019|支持|基于JT/T808-2019|

备注：无需手动配置，同时兼容2011、2013、2019协议版本，支持分包请求、分包应答及超时分包补传。
1078协议支持音视频指令，流媒体服务需自行搭建。

# 代码仓库
 * Gitee仓库地址：[https://gitee.com/yezhihao/jt808-server/tree/master](https://gitee.com/yezhihao/jt808-server/tree/master)
 * Github仓库地址：[https://github.com/yezhihao/jt808-server/tree/master](https://github.com/yezhihao/jt808-server/tree/master)

# 演示
 * 设备接入：127.0.0.1:7611
 * 日志监控：http://127.0.0.1:8000/ws.html
 * 接口文档：http://127.0.0.1:8000/doc.html

# 验证步骤

## 1.验证消息定义
解码分析工具：org.yzh.Elucidator（报文 <=> 对象）

使用src\test\java\Elucidator
分析报文内每个属性所处的位置以及转换后的值，以便查询报文解析出错的原因
```java
package org.yzh;

public class Elucidator extends JT808Beans {

    public static final JTMessageAdapter coder = new JTMessageAdapter("org.yzh.protocol");

    public static void main(String[] args) {
        String hex = "020000d40123456789017fff000004000000080006eeb6ad02633df7013800030063200707192359642f000000400101020a0a02010a1e00640001b2070003640e200707192359000100000061646173200827111111010101652f000000410202020a0000000a1e00c8000516150006c81c20070719235900020000000064736d200827111111020202662900000042031e012c00087a23000a2c2a200707192359000300000074706d732008271111110303030067290000004304041e0190000bde31000d90382007071923590004000000006273642008271111110404049d";
        JTMessage msg = H2019(T0200JSATL12());

        msg = decode(hex);
        hex = encode(msg);
    }
}
```
Elucidator 运行效果如下：
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
运行 \协议文档\发包工具.exe
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
* Swagger UI：[http://127.0.0.1:8000/swagger-ui/index.html](http://127.0.0.1:8000/swagger-ui/index.html)

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

    @Field(desc = "省域ID")
    private short provinceId;
    @Field(desc = "市县域ID")
    private short cityId;
    @Field(length = 11, desc = "制造商ID")
    private String makerId;
    @Field(length = 30, desc = "终端型号")
    private String deviceModel;
    @Field(length = 30, desc = "终端ID")
    private String deviceId;
    @Field(desc = "车牌颜色：0.未上车牌 1.蓝色 2.黄色 3.黑色 4.白色 9.其他")
    private byte plateColor;
    @Field(desc = "车辆标识")
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
@RequestMapping("device")
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
* @Async，异步消息处理，用于较为耗时的操作（例如文件写入）。
* @AsyncBatch，消息批量处理，对于高并发的消息（例如：位置信息汇报），合并同类消息，提升入库性能。

目录结构
```sh
├── 协议文档
│   ├── 808-2011协议文档
│   ├── 808-2013协议文档
│   ├── 808-2019协议文档
│   ├── 1078-2016协议文档
│   ├── 粤标-2020协议文档
│   ├── 苏标-2016协议文档
│   └── 发包工具.exe
│
├──jtt808-protocol
│   │
│   ├──main
│   │   ├── t808 消息定义
│   │   ├── t1078 消息定义
│   │   ├── jstal12 消息定义
│   │   └── codec 编码解码
│   └──test
│       ├── codec 协议解析工具
│       └── protocol 协议单元测试
│
├──jtt808-server
│   ├──main
│   │   └── web SpringBoot微服务
│   │      ├── config 808服务配置项
│   │      └── endpoint 808消息入口,通过netty收到的请求会根据@Mapping转发到此
│   └──test
│      ├── ClientTest 客户端
│      └── StressTest 压力测试
 ```

项目创立于2017年9月，至今，jt808-server已接入多家公司的线上产品线，接入场景如车辆管理平台，IOT业务和大数据作业等，截止最新统计时间为止，jt808-server已接入的公司包括不限于：

	- 1.福建九桃贸易有限公司
	- 2.深圳市特维视科技有限公司
	- 3.厦门河联信息科技有限公司
	- 4.北京华盾互联科技有限公司
	- 5.宜昌华维物流有限责任公司
	- 6.江苏推米信息科技有限公司
	- 7.山东六度信息科技有限公司
	- 8.亚信创新技术(南京)有限公司
	- 9.无锡创趣网络科技有限公司
	- 10.新疆智联云信息科技有限公司
	- 11.杭州品铂科技有限公司
	- 12.萍乡萍钢安源钢铁有限公司
	- 13.承德统凯网络科技有限公司
	- 14.陕西省君凯电子科技有限公司
    - ……

> 更多接入的公司，欢迎在 [登记地址](https://gitee.com/yezhihao/jt808-server/issues/I36WKD ) 登记，登记仅仅为了项目推广(登记后可提供一次技术支持)。

欢迎大家的关注和使用，jt808-server也将拥抱变化，持续发展。

项目会不定期进行更新，建议star和watch一份，您的支持是我最大的动力。

如有任何疑问或者BUG，请联系我，非常感谢。

另提供技术支持、协议扩展、数据入库、二次开发等服务。

项目负责人QQ：[1527621790]

技术交流QQ群：[323100134]

- JT808泛指JT/T808协议，是指交通部制定的运输行业通信标准，全称《交通运输行业标准 - 道路运输车辆卫星定位系统终端通信协议及数据格式》

