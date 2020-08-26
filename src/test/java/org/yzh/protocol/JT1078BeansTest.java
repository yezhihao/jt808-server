package org.yzh.protocol;

import org.junit.Test;

import static org.yzh.protocol.BeanTest.selfCheck;
import static org.yzh.protocol.JT1078Beans.*;

/**
 * JT/T 1078协议单元测试类
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class JT1078BeansTest {

    @Test
    public void testT1003() {
        selfCheck(H2013(T1003()));
        selfCheck(H2019(T1003()));
    }

    @Test
    public void testT1005() {
        selfCheck(H2013(T1005()));
        selfCheck(H2019(T1005()));
    }

    @Test
    public void testT1205() {
        selfCheck(H2013(T1205()));
        selfCheck(H2019(T1205()));
    }

    @Test
    public void testT1206() {
        selfCheck(H2013(T1206()));
        selfCheck(H2019(T1206()));
    }

    @Test
    public void testT9101() {
        selfCheck(H2013(T9101()));
        selfCheck(H2019(T9101()));
    }

    @Test
    public void testT9102() {
        selfCheck(H2013(T9102()));
        selfCheck(H2019(T9102()));
    }

    @Test
    public void testT9105() {
        selfCheck(H2013(T9105()));
        selfCheck(H2019(T9105()));
    }

    @Test
    public void testT9201() {
        selfCheck(H2013(T9201()));
        selfCheck(H2019(T9201()));
    }

    @Test
    public void testT9202() {
        selfCheck(H2013(T9202()));
        selfCheck(H2019(T9202()));
    }

    @Test
    public void testT9205() {
        selfCheck(H2013(T9205()));
        selfCheck(H2019(T9205()));
    }

    @Test
    public void testT9206() {
        selfCheck(H2013(T9206()));
        selfCheck(H2019(T9206()));
    }

    @Test
    public void testT9207() {
        selfCheck(H2013(T9207()));
        selfCheck(H2019(T9207()));
    }

    @Test
    public void testT9301() {
        selfCheck(H2013(T9301()));
        selfCheck(H2019(T9301()));
    }

    @Test
    public void testT9302() {
        selfCheck(H2013(T9302()));
        selfCheck(H2019(T9302()));
    }
}