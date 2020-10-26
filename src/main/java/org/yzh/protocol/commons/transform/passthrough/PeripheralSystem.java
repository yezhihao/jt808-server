package org.yzh.protocol.commons.transform.passthrough;

import org.yzh.framework.orm.annotation.Field;
import org.yzh.framework.orm.model.DataType;

import java.util.ArrayList;
import java.util.List;

public class PeripheralSystem {

    public static final int ID = 0xF8;

    private int total;
    private List<Item> items;

    @Field(index = 0, type = DataType.BYTE)
    public int getTotal() {
        if (items != null)
            return items.size();
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Field(index = 1, type = DataType.LIST)
    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void addItem(String companyName, String productModel, String hardwareVersion, String firmwareVersion, String deviceId, String userCode) {
        if (items == null)
            items = new ArrayList<>();
        items.add(new Item(companyName, productModel, hardwareVersion, firmwareVersion, deviceId, userCode));
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(360);
        sb.append('{');
        sb.append("total=").append(total);
        sb.append(", items=").append(items);
        sb.append('}');
        return sb.toString();
    }

    public static class Item {

        private String companyName;
        private String productModel;
        private String hardwareVersion;
        private String firmwareVersion;
        private String deviceId;
        private String userCode;

        public Item() {
        }

        public Item(String companyName, String productModel, String hardwareVersion, String firmwareVersion, String deviceId, String userCode) {
            this.companyName = companyName;
            this.productModel = productModel;
            this.hardwareVersion = hardwareVersion;
            this.firmwareVersion = firmwareVersion;
            this.deviceId = deviceId;
            this.userCode = userCode;
        }

        @Field(index = 1, type = DataType.STRING, lengthSize = 1, version = 0)
        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        @Field(index = 2, type = DataType.STRING, lengthSize = 1, version = 0)
        public String getProductModel() {
            return productModel;
        }

        public void setProductModel(String productModel) {
            this.productModel = productModel;
        }

        @Field(index = 3, type = DataType.STRING, lengthSize = 1, version = 0)
        public String getHardwareVersion() {
            return hardwareVersion;
        }

        public void setHardwareVersion(String hardwareVersion) {
            this.hardwareVersion = hardwareVersion;
        }

        @Field(index = 4, type = DataType.STRING, lengthSize = 1, version = 0)
        public String getFirmwareVersion() {
            return firmwareVersion;
        }

        public void setFirmwareVersion(String firmwareVersion) {
            this.firmwareVersion = firmwareVersion;
        }

        @Field(index = 5, type = DataType.STRING, lengthSize = 1, version = 0)
        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        @Field(index = 6, type = DataType.STRING, lengthSize = 1, version = 0)
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
            sb.append("companyName='").append(companyName).append('\'');
            sb.append(", productModel='").append(productModel).append('\'');
            sb.append(", hardwareVersion='").append(hardwareVersion).append('\'');
            sb.append(", firmwareVersion='").append(firmwareVersion).append('\'');
            sb.append(", deviceId='").append(deviceId).append('\'');
            sb.append(", userCode='").append(userCode).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }
}