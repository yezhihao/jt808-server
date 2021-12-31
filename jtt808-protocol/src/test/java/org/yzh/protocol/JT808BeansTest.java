package org.yzh.protocol;

import org.junit.jupiter.api.Test;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.commons.JT808;

import static org.yzh.protocol.BeanTest.*;
import static org.yzh.protocol.JT808Beans.*;

/**
 * JT/T 808协议单元测试类
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
public class JT808BeansTest {

    @Test
    public void testT8303() {
        selfCheck(H2013(T8303()));
        selfCheck(H2019(T8303()));
    }

    @Test
    public void testT0805() {
        selfCheck(H2013(T0805()));
        selfCheck(H2019(T0805()));
    }

    @Test
    public void testT0705() {
        selfCheck(H2013(T0705()));
        selfCheck(H2019(T0705()));
    }

    @Test
    public void testT0A00_8A00() {
        selfCheck(H2013(T0A00_8A00()));
        selfCheck(H2019(T0A00_8A00()));
    }

    @Test
    public void testT8103() {
        selfCheck(H2013(T8103()));
        selfCheck(H2019(T8103()));
    }

    @Test
    public void testT0001() {
        selfCheck(H2013(T0001()));
        selfCheck(H2019(T0001()));
    }

    @Test
    public void testT0002() {
        JTMessage message = new JTMessage();
        message.setMessageId(JT808.终端心跳);
        selfCheck(H2013(message));
        selfCheck(H2019(message));
    }

    @Test
    public void testT0200() {
        selfCheck(H2013(T0200Attributes()));
        selfCheck(H2019(T0200Attributes()));
    }

    @Test
    public void testT0200JSATL12() {
        selfCheck(H2013(T0200JSATL12()));
        selfCheck(H2019(T0200JSATL12()));
    }

    @Test
    public void testT0301() {
        selfCheck(H2013(T0301()));
        selfCheck(H2019(T0301()));
    }

    @Test
    public void testT0302() {
        selfCheck(H2013(T0302()));
        selfCheck(H2019(T0302()));
    }

    @Test
    public void testT0704() {
        selfCheck(H2013(T0704()));
        selfCheck(H2019(T0704()));
    }

    @Test
    public void testT0800() {
        selfCheck(H2013(T0800()));
        selfCheck(H2019(T0800()));
    }

    @Test
    public void testT8106() {
        selfCheck(H2013(T8106()));
        selfCheck(H2019(T8106()));
    }

    @Test
    public void testT8108() {
        selfCheck(H2013(T8108()));
        selfCheck(H2019(T8108()));
    }

    @Test
    public void testT0100() {
        selfCheck(H2013(T0100()));
        selfCheck(H2019(T0100()));
    }

    @Test
    public void testT0303() {
        selfCheck(H2013(T0303()));
        selfCheck(H2019(T0303()));
    }

    @Test
    public void testT0702() {
        selfCheck(H2013(T0702()));
        selfCheck(H2019(T0702()));
    }

    @Test
    public void testT0802() {
        selfCheck(H2013(T0802()));
        selfCheck(H2019(T0802()));
    }

    @Test
    public void testT0901() {
        selfCheck(H2013(T0901()));
        selfCheck(H2019(T0901()));
    }

    @Test
    public void testT8105() {
        selfCheck(H2013(T8105()));
        selfCheck(H2019(T8105()));
    }

    @Test
    public void testT8003() {
        selfCheck(H2013(T8003()));
        selfCheck(H2019(T8003()));
    }

    @Test
    public void testT8202() {
        selfCheck(H2013(T8202()));
        selfCheck(H2019(T8202()));
    }

    @Test
    public void testT8203() {
        selfCheck(H2013(T8203()));
        selfCheck(H2019(T8203()));
    }

    @Test
    public void testT8302() {
        selfCheck(H2013(T8302()));
        selfCheck(H2019(T8302()));
    }

    @Test
    public void testT0107() {
        selfCheck(H2013(T0107()));
        selfCheck(H2019(T0107()));
    }

    @Test
    public void testT0108() {
        selfCheck(H2013(T0108()));
        selfCheck(H2019(T0108()));
    }

    @Test
    public void testT0104() {
        selfCheck(H2013(T0104()));
        selfCheck(H2019(T0104()));
    }

    @Test
    public void testT0102() {
        selfCheck(H2013(T0102_2013()));
        selfCheck(H2019(T0102_2019()));
    }

    @Test
    public void testT8100() {
        selfCheck(H2013(T8100()));
        selfCheck(H2019(T8100()));
    }

    @Test
    public void testT0801() {
        selfCheck(H2013(T0801()));
        selfCheck(H2019(T0801()));
    }

    @Test
    public void testT0201_0500() {
        selfCheck(H2013(T0201_0500()));
        selfCheck(H2019(T0201_0500()));
    }

    @Test
    public void testT8600() {
        selfCheck(H2013(T8600(2013)));
        selfCheck(H2019(T8600(2019)));
    }

    @Test
    public void testT8401() {
        selfCheck(H2013(T8401()));
        selfCheck(H2019(T8401()));
    }

    @Test
    public void testT8804() {
        selfCheck(H2013(T8804()));
        selfCheck(H2019(T8804()));
    }

    @Test
    public void testT8604() {
        selfCheck(H2013(T8604(2013)));
        selfCheck(H2019(T8604(2019)));
    }

    @Test
    public void testT8900_0900() {
        selfCheck(H2013(T0900()));
        selfCheck(H2019(T0900()));
    }

    @Test
    public void testT8608() {
        selfCheck(H2013(T8608()));
        selfCheck(H2019(T8608()));
    }

    @Test
    public void testT8800() {
        selfCheck(H2013(T8800()));
        selfCheck(H2019(T8800()));
    }

    @Test
    public void testT8400() {
        selfCheck(H2013(T8400()));
        selfCheck(H2019(T8400()));
    }

    @Test
    public void testT8304() {
        selfCheck(H2013(T8304()));
        selfCheck(H2019(T8304()));
    }

    @Test
    public void testT8301() {
        selfCheck(H2013(T8301()));
        selfCheck(H2019(T8301()));
    }

    @Test
    public void testT8500() {
        selfCheck(H2013(T8500()));
        selfCheck(H2019(T8500()));
    }

    @Test
    public void testT8606() {
        selfCheck(H2013(T8606(2013)));
        selfCheck(H2019(T8606(2019)));
    }

    @Test
    public void testT8802() {
        selfCheck(H2013(T8802()));
        selfCheck(H2019(T8802()));
    }

    @Test
    public void testT8601() {
        selfCheck(H2013(T8601()));
        selfCheck(H2019(T8601()));
    }

    @Test
    public void testT8300() {
        selfCheck(H2013(T8300_2013()));
        selfCheck(H2019(T8300_2019()));
    }

    @Test
    public void testT8805() {
        selfCheck(H2013(T8805()));
        selfCheck(H2019(T8805()));
    }

    @Test
    public void testT8801() {
        selfCheck(H2013(T8801()));
        selfCheck(H2019(T8801()));
    }

    @Test
    public void testT8602() {
        selfCheck(H2013(T8602(2013)));
        selfCheck(H2019(T8602(2019)));
    }

    @Test
    public void testT8803() {
        selfCheck(H2013(T8803()));
        selfCheck(H2019(T8803()));
    }

    //===================================== 1078

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

    //===================================== JSATL

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