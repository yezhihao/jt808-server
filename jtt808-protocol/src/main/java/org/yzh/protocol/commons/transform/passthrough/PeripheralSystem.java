package org.yzh.protocol.commons.transform.passthrough;

import io.netty.buffer.ByteBuf;
import org.yzh.protocol.commons.Charsets;

import java.util.ArrayList;
import java.util.List;

/**
 * 信息查询
 * 外设传感器的基本信息：公司信息、产品代码、版本号、外设ID、客户代码
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class PeripheralSystem {

    public static final int ID = 0xF8;

    private List<Item> items;

    public PeripheralSystem() {
    }

    public PeripheralSystem(List<Item> items) {
        this.items = items;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void addItem(byte id, String companyName, String productModel, String hardwareVersion, String firmwareVersion, String deviceId, String userCode) {
        if (items == null)
            items = new ArrayList<>(4);
        items.add(new Item(id, companyName, productModel, hardwareVersion, firmwareVersion, deviceId, userCode));
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(360);
        sb.append('{');
        sb.append("items=").append(items);
        sb.append('}');
        return sb.toString();
    }

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

        public byte getId() {
            return id;
        }

        public void setId(byte id) {
            this.id = id;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getProductModel() {
            return productModel;
        }

        public void setProductModel(String productModel) {
            this.productModel = productModel;
        }

        public String getHardwareVersion() {
            return hardwareVersion;
        }

        public void setHardwareVersion(String hardwareVersion) {
            this.hardwareVersion = hardwareVersion;
        }

        public String getFirmwareVersion() {
            return firmwareVersion;
        }

        public void setFirmwareVersion(String firmwareVersion) {
            this.firmwareVersion = firmwareVersion;
        }

        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public String getUserCode() {
            return userCode;
        }

        public void setUserCode(String userCode) {
            this.userCode = userCode;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder(180);
            sb.append('{');
            sb.append("id=").append(id);
            sb.append(",companyName=").append(companyName);
            sb.append(",productModel=").append(productModel);
            sb.append(",hardwareVersion=").append(hardwareVersion);
            sb.append(",firmwareVersion=").append(firmwareVersion);
            sb.append(",deviceId=").append(deviceId);
            sb.append(",userCode=").append(userCode);
            sb.append('}');
            return sb.toString();
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
            return new PeripheralSystem(list);
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