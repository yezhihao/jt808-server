部标808协议快速开发包
===============
# 项目介绍
* 基于netty、spring boot框架，实现了JT/T 808部标协议的服务端程序
* 最简洁、清爽、易用的部标开发框架。
技术交流QQ群：[906230542]

项目会不定时进行更新，建议star和watch一份。

# 主要特性
* 代码足够精简，便于二次开发。
* 致敬Spring、Hibernate设计理念，熟悉Web开发的同学上手极快。
* 使用注解描述协议，告别繁琐的封包、解包。
* 支持分包请求。
* 支持异步批量处理，高并发下MySQL不再是瓶颈。
* 支持2013、2019两个版本的部标协议。
* 内置封包&解包过程的分析工具，便于查错。
* 完善的测试用例，稳定发版

# 代码仓库
 * Gitee仓库地址：[https://gitee.com/yezhihao/jt808-server/tree/master](https://gitee.com/yezhihao/jt808-server/tree/master)
 * Github仓库地址：[https://github.com/yezhihao/jt808-server/tree/master](https://github.com/yezhihao/jt808-server/tree/master)

# 下载方式
 * Gitee下载命令：`git clone https://gitee.com/yezhihao/jt808-server -b master`
 * Github下载命令：`git clone https://github.com/yezhihao/jt808-server -b master`


# 使用说明

#### 整个项目分为四部分：

#### 1.framework，核心模块，不推荐修改，有BUG或者扩展的需求，建议提交issues或者联系本人
 codec 抽象的编码解码工具 
 mvc 消息分发，消息处理
 netty netty相关的实现
 orm 消息元数据的描述
 session 消息和会话的管理
 
核心注解：

@org.yzh.framework.mvc.annotation.Endpoint，标记TCP服务的接入点，相当于SpringMVC中的 @Controller；
@org.yzh.framework.mvc.annotation.Mapping，types中定义消息ID，相当于SpringMVC中 @RequestMapping；
@org.yzh.framework.mvc.annotation.AsyncBatch, 对于发送频率较高的消息，例如0x0200(位置信息汇报)，使用该注解批量处理，显著提升MySQL入库性能。

@org.yzh.framework.orm.annotation.Message，定义协议类型，类比@Table，
@org.yzh.framework.orm.annotation.Field，定义协议中各属性占用的字节位置，类比@Column，
@org.yzh.framework.orm.annotation.Fs，支持多版本协议

MessageDecoder、MessageEncoder实现了对@Field的处理，
同样SimpleHandler -> @Mapping
同样AsyncBatchHandler -> @Mapping、@AsyncBatch 


#### 2.protocol包存放部标协议相关，一般不推荐做大量改动，会定期补充协议，希望其他朋友能够将定义的协议模型分享给大家
basics 部标协议通用消息头，以及复用的消息定义
codec 部标编码解码工具
commons 部标协议ID，附加信息工具类等
t808 jt/t808协议定义
t1078 jt/t1078协议

#### 3.web包显然是常见的springboot项目模块，一般的需求都在这个包下开发，可随意修改

component.mybatis 一个简易的mybatis分页组件
endpoint 部标协议接入点，所有netty进入的请求都会根据@Mapping转发到endpoint
application.yml 默认使用h2database作为开发环境

代码示例：

消息接入：
```java
@Endpoint
@Component
public class JT808Endpoint {

    private static final Logger log = LoggerFactory.getLogger(JT808Endpoint.class.getSimpleName());

    private MessageManager messageManager = MessageManager.getInstance();

    private SessionManager sessionManager = SessionManager.getInstance();

    @Autowired
    private LocationService locationService;

    //异步批量处理 队列大小20000 最大累积200处理一次 最大等待时间5秒
    @AsyncBatch(capacity = 20000, maxElements = 200, maxWait = 5000)
    @Mapping(types = 位置信息汇报, desc = "位置信息汇报")
    public void 位置信息汇报(List<T0200> list) {
        locationService.batchInsert(list);
    }

    @Mapping(types = 终端注册, desc = "终端注册")
    public T8100 register(T0100 message, Session session) {
        Header header = message.getHeader();
        //TODO
        session.setTerminalId(header.getTerminalId());
        sessionManager.put(Session.buildId(session.getChannel()), session);

        T8100 result = new T8100(header.getSerialNo(), T8100.Success, "test_token");
        result.setHeader(new Header(终端注册应答, session.currentFlowId(), header.getMobileNo()));
        return result;
    }
}
```

消息下发：
```java
@Api(description = "terminal api")
@Controller
@RequestMapping("terminal")
public class TerminalController {

    private MessageManager messageManager = MessageManager.getInstance();

    @ApiOperation(value = "设置终端参数")
    @RequestMapping(value = "{terminalId}/parameters", method = RequestMethod.POST)
    @ResponseBody
    public T0001 updateParameters(@PathVariable("terminalId") String terminalId, @RequestBody List<TerminalParameter> parameters) {
        T8103 message = new T8103();
        message.setHeader(new Header(JT808.设置终端参数, terminalId));
        message.setItems(parameters);
        T0001 response = (T0001) messageManager.request(message);
        return response;
    }
}
```
#### 4.Test目录下为JT/T 808协议的测试用例和报文解释器

org.yzh.jt808.codec.TestBeans 消息对象的封包解包
org.yzh.jt808.codec.TestHex 原始报文测试
org.yzh.jt808.codec.Beans 测试数据

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

有任何疑问或者BUG 请联系我，非常感谢。
技术交流QQ群：[906230542]
