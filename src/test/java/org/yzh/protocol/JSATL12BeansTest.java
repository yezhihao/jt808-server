package org.yzh.protocol;

import org.junit.jupiter.api.Test;

import static org.yzh.protocol.BeanTest.*;
import static org.yzh.protocol.JSATL12Beans.*;

/**
 * T/JSATL12 协议单元测试类
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class JSATL12BeansTest {

    @Test
    public void testT1210() {
        selfCheck(H2013(T1210()));
        selfCheck(H2019(T1210()));
    }

    @Test
    public void testT1211() {
        selfCheck(H2013(T1211()));
        selfCheck(H2019(T1211()));
    }

    @Test
    public void testT9208() {
        selfCheck(H2013(T9208()));
        selfCheck(H2019(T9208()));
    }

    @Test
    public void testT9212() {
        selfCheck(H2013(T9212()));
        selfCheck(H2019(T9212()));
    }
}