package org.yzh.jt808.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import org.junit.Test;
import org.yzh.framework.commons.transform.JsonUtils;
import org.yzh.framework.message.PackageData;
import org.yzh.web.config.Charsets;
import org.yzh.web.jt808.codec.JT808MessageDecoder;
import org.yzh.web.jt808.codec.JT808MessageEncoder;
import org.yzh.web.jt808.dto.*;
import org.yzh.web.jt808.dto.basics.Header;

import java.util.ArrayList;
import java.util.List;

public class CoderTest {

    private static final JT808MessageDecoder decoder = new JT808MessageDecoder(Charsets.GBK);

    private static final JT808MessageEncoder encoder = new JT808MessageEncoder(Charsets.GBK);

    public static void main(String[] args) throws Exception {
//        byte[] bytes = HexStringUtils.toBytes("0b");
//        System.out.println(BitOperator.byteToInteger(ByteBufUtil.decodeHexDump("8401")));
        int i = Unpooled.wrappedBuffer(ByteBufUtil.decodeHexDump("8401")).readUnsignedShort();
        System.out.println(i);
//
//        System.out.println(HexStringUtils.toHexString("JS5-HD-105V".getBytes(Charsets.GBK)));
//        System.out.println(HexStringUtils.toHexString("T17051604-V705061".getBytes(Charsets.GBK)));
//
//        System.out.println(new String(HexStringUtils.toBytes("073731363033"), Charsets.GBK));
//        System.out.println(new String(HexStringUtils.toBytes("4a53352d4c000000000000000000000000000000"), Charsets.GBK));
//        System.out.println(new String(HexStringUtils.toBytes("8401"), Charsets.GBK));
//
//        System.out.println(BCD8421Operator.bcd2String(HexStringUtils.toBytes("3689860225131650397533")));
    }

    public static PackageData<Header> register() {
        Register b = new Register();
        b.setHeader(header());
        b.setProvinceId(44);
        b.setCityId(307);
        b.setManufacturerId("测试");
        b.setTerminalType("TEST");
        b.setTerminalId("粤B8888");
        b.setLicensePlateColor(0);
        b.setLicensePlate("粤B8888");
        return b;
    }

    public static PackageData<Header> questionMessage() {
        QuestionMessage bean = new QuestionMessage();
        List<QuestionMessage.Option> options = new ArrayList();
        bean.setHeader(header());
        bean.setContent("123");
        bean.setSigns(new int[]{0});
        bean.setOptions(options);

        options.add(new QuestionMessage.Option(1, "asd1"));
        options.add(new QuestionMessage.Option(2, "zxc2"));
        return bean;
    }

    public static Header header() {
        Header header = new Header();
        header.setType(1);
        header.setTerminalPhone("020000000015");
        header.setFlowId(37);
        header.setEncryptionType(0);
        header.setReservedBit(0);
        return header;
    }

    public static void selfCheck(Class<? extends PackageData> clazz, String hex) throws Exception {
        for (int i = 0; i < 3; i++) {
            System.out.println(">>>" + i);
            PackageData<Header> bean = transform(clazz, hex);
            hex = transform(bean);
        }
    }

    public static void selfCheck(PackageData<Header> bean) throws Exception {
        for (int i = 0; i < 3; i++) {
            System.out.println(">>>" + i);
            String hex = transform(bean);
            bean = transform(bean.getClass(), hex);
        }
    }

    public static <T extends PackageData> T transform(Class<T> clazz, String hex) {
        ByteBuf buf = Unpooled.wrappedBuffer(ByteBufUtil.decodeHexDump(hex));
        Header header = decoder.decodeHeader(buf);
        ByteBuf slice = buf.slice(header.getHeaderLength(), header.getBodyLength());
        PackageData<Header> body = decoder.decodeBody(slice, clazz);
        body.setHeader(header);
        System.out.println(JsonUtils.toJson(body));
        return (T) body;
    }

    public static String transform(PackageData<Header> packageData) {
        ByteBuf buf = encoder.encodeAll(packageData);
        String hex = ByteBufUtil.hexDump(buf);
        System.out.println(hex);
        return hex;
    }

    @Test
    public void testPositionReportBatch() {
        PositionReportBatch bean1 = transform(PositionReportBatch.class, "070400bd0177018402070001000301003c000000000000000001dde6700731068affee0000011c170805144904010400001c5403020000250400000000310100020200003001182b0400000001003c000000000000000001dde6700731068affee0000011c170805145904010400001c54030200002504000000003101000202000030011a2b0400010001003c000000000000000001dde6700731068affee0000011c170805150904010400001c54030200002504000000003101000202000030011a2b0400010001c3");

        String hex1 = transform(bean1);

        PackageData bean2 = transform(PositionReportBatch.class, hex1);
        String hex2 = transform(bean2);

        assert hex1.equals(hex2);
    }

    @Test
    public void testPhoneBook() {
//        PhoneBook bean1 = new PhoneBook();
//        bean1.setHeader(header());
//        bean1.setType(PhoneBook.Append);
//        bean1.add(new PhoneBook.Item(2, "18217341802", "张三"));
//        bean1.add(new PhoneBook.Item(1, "123123", "李四"));
//        bean1.add(new PhoneBook.Item(3, "123123", "王五"));
        PhoneBook bean1 = transform(PhoneBook.class, "0001002e02000000001500250203020b043138323137333431383032d5c5c8fd010604313233313233c0eecbc4030604313233313233cdf5cee535");

        String hex1 = transform(bean1);

        PackageData bean2 = transform(PhoneBook.class, hex1);
        String hex2 = transform(bean2);

        assert hex1.equals(hex2);
    }

    @Test
    public void testEventSetting() {
//        EventSetting bean1 = new EventSetting();
//        bean1.setHeader(header());
//        bean1.setType(EventSetting.Append);
//        bean1.addEvent(1, "test");
//        bean1.addEvent(2, "测试2");
//        bean1.addEvent(3, "t试2");

        EventSetting bean1 = transform(EventSetting.class, "83010010017701840207000c0202010574657374310205746573743268");

        String hex1 = transform(bean1);

        PackageData bean2 = transform(EventSetting.class, hex1);
        String hex2 = transform(bean2);

        assert hex1.equals(hex2);
    }

    @Test
    public void testPositionReport() {
        PositionReport bean1 = transform(PositionReport.class, "0200003c017701840207000600000000000008010000000000000000000000000000171025155537010400001c54030200002504000000003101000202000030011d2b0400000000cc");

        String hex1 = transform(bean1);

        PackageData bean2 = transform(PositionReport.class, hex1);
        String hex2 = transform(bean2);

        assert hex1.equals(hex2);
    }

    @Test
    public void testCommonResult() {
        PackageData bean1 = transform(CommonResult.class, "0001000501770184020701840038810300cd");
        String hex1 = transform(bean1);

        PackageData bean2 = transform(CommonResult.class, hex1);
        String hex2 = transform(bean2);

        assert hex1.equals(hex2);
    }

    @Test
    public void testTerminalAttributeReply() {
        PackageData bean1 = transform(TerminalAttributeReply.class, "0107004c017701840207047d01000737313630334a53352d4c00000000000000000000000000000032303030313836898602251316503975330b4a53352d48442d31303556115431373035313630342d5637303530363103ff0d");
        String hex1 = transform(bean1);

        PackageData bean2 = transform(TerminalAttributeReply.class, hex1);
        String hex2 = transform(bean2);

        assert hex1.equals(hex2);
    }

    @Test
    public void testParameterSettingReply() {
        PackageData bean1 = transform(ParameterSettingReply.class, "0104030a017701840207018400005b0000000104000000140000000204000000000000000304000000000000000404000000000000000504000000000000000604000000000000000704000000000000001005636d6e6574000000110463617264000000120463617264000000130c3139322e3136382e322e3939000000140000000015000000001600000000170d3139322e3136382e302e31353500000018040000270f0000001904000000580000001a0e3230322e39362e34322e313133000000001b0400002af90000001c04000000000000001d0000000020040000000000000021040000000000000022040000000000000027040000025800000028040000000000000029040000001e0000002c04000000050000002d04000000000000002e04000000000000002f040000000000000030040000002d0000003102000000000040000000004100000000420000000043000000004400000000450400000000000000460400000000000000470400000000000000480000000049000000005004000000000000005104000000000000005204000000000000005304000000000000005404000000000000005504000000000000005604000000050000005704000000000000005804000000000000005904000000000000005a04000000000000005b0200320000005c0200000000005d0200000000005e0200000000006404000000000000006504000000000000007004000000000000007104000000000000007204000000000000007304000000000000007404000000000000007d04000000000000007f0400000000000000800400001c540000008102002c0000008202012c000000830530303030300000008401020000009001000000009101000000009201000000009304000000000000009401000000009504000000000000010004000000000000010102000000000102040000000000000103020000000001100800000000000000000000f00004000000000000f00104000000000000f00204000000000000f00304000000000000f00404000000000000ff0104000000000000ff02043c0000000000ffff0405000000a4");
        String hex1 = transform(bean1);

        PackageData bean2 = transform(ParameterSettingReply.class, hex1);
        String hex2 = transform(bean2);

        assert hex1.equals(hex2);
    }

    @Test
    public void testQuestionMessage() {
//        PackageData bean1 = JsonUtils.toObj(QuestionMessage.class, "{\"content\":\",,,,!\",\"options\":[{\"content\":\"1\",\"id\":1},{\"content\":\"2\",\"id\":2},{\"content\":\"3\",\"id\":3}],\"signs\":[4]}");
        PackageData bean1 = transform(QuestionMessage.class, "8302001a017701840207001010062c2c2c2c2c2101000331323302000334353603000337383954");
        bean1.setHeader(header());
        String hex1 = transform(bean1);

        PackageData bean2 = transform(QuestionMessage.class, hex1);
        String hex2 = transform(bean2);

        assert hex1.equals(hex2);
    }

    @Test
    public void testRegister() {
        PackageData bean1 = register();
        String hex1 = transform(bean1);

        PackageData bean2 = transform(Register.class, hex1);
        String hex2 = transform(bean2);

        assert hex1.equals(hex2);
    }

}