package org.yzh.protocol.commons.transform.passthrough;

import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.yzh.protocol.commons.Charsets;

import java.util.ArrayList;
import java.util.List;

/**
 * 信息查询
 * 外设传感器的基本信息：公司信息、产品代码、版本号、外设ID、客户代码
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
@ToString
@Data
@Accessors(chain = true)
public class PeripheralSystem {

    public static final Integer key = 0xF8;

    private List<Item> items;

    public void addItem(byte id, String companyName, String productModel, String hardwareVersion, String firmwareVersion, String deviceId, String userCode) {
        if (items == null)
            items = new ArrayList<>(4);
        items.add(new Item(id, companyName, productModel, hardwareVersion, firmwareVersion, deviceId, userCode));
    }

    @ToString
    @Data
    @Accessors(chain = true)
    public static class Item {
        private byte id;
        private String companyName;
        private String productModel;
        private String hardwareVersion;
        private String firmwareVersion;
        private String deviceId;
        private String userCode;

        public Item() {
        }

        public Item(byte id, String companyName, String productModel, String hardwareVersion, String firmwareVersion, String deviceId, String userCode) {
            this.id = id;
            this.companyName = companyName;
            this.productModel = productModel;
            this.hardwareVersion = hardwareVersion;
            this.firmwareVersion = firmwareVersion;
            this.deviceId = deviceId;
            this.userCode = userCode;
        }
    }

    public static class Schema implements io.github.yezhihao.protostar.Schema<PeripheralSystem> {

        public static final Schema INSTANCE = new Schema();

        private Schema() {
        }

        @Override
        public PeripheralSystem readFrom(ByteBuf input) {
            byte total = input.readByte();
            List<Item> list = new ArrayList<>(total);
            while (input.isReadable()) {
                Item item = new Item();
                item.id = input.readByte();
                input.readByte();
                item.companyName = input.readCharSequence(input.readByte(), Charsets.GBK).toString();
                item.productModel = input.readCharSequence(input.readByte(), Charsets.GBK).toString();
                item.hardwareVersion = input.readCharSequence(input.readByte(), Charsets.GBK).toString();
                item.firmwareVersion = input.readCharSequence(input.readByte(), Charsets.GBK).toString();
                item.deviceId = input.readCharSequence(input.readByte(), Charsets.GBK).toString();
                item.userCode = input.readCharSequence(input.readByte(), Charsets.GBK).toString();
                list.add(item);
            }
            return new PeripheralSystem().setItems(list);
        }

        @Override
        public void writeTo(ByteBuf output, PeripheralSystem message) {
            List<Item> items = message.getItems();
            output.writeByte(items.size());

            byte[] bytes;
            for (Item item : items) {
                output.writeByte(item.id);
                int begin = output.writerIndex();
                output.writeByte(0);
                bytes = item.companyName.getBytes(Charsets.GBK);
                output.writeByte(bytes.length).writeBytes(bytes);
                bytes = item.productModel.getBytes(Charsets.GBK);
                output.writeByte(bytes.length).writeBytes(bytes);
                bytes = item.hardwareVersion.getBytes(Charsets.GBK);
                output.writeByte(bytes.length).writeBytes(bytes);
                bytes = item.firmwareVersion.getBytes(Charsets.GBK);
                output.writeByte(bytes.length).writeBytes(bytes);
                bytes = item.deviceId.getBytes(Charsets.GBK);
                output.writeByte(bytes.length).writeBytes(bytes);
                bytes = item.userCode.getBytes(Charsets.GBK);
                output.writeByte(bytes.length).writeBytes(bytes);
                int len = output.writerIndex() - begin - 1;
                output.setByte(begin, len);
            }
        }
    }
}