package org.yzh.web.model.vo;

import io.github.yezhihao.protostar.util.KeyValuePair;
import io.swagger.v3.oas.annotations.media.Schema;
import org.yzh.protocol.commons.transform.passthrough.PeripheralStatus;
import org.yzh.protocol.commons.transform.passthrough.PeripheralSystem;

public class Passthroughs {

    @Schema(description = "状态查询(外设状态信息：外设工作状态、设备报警信息)")
    private PeripheralStatus peripheralStatus;

    @Schema(description = "信息查询(外设传感器的基本信息：公司信息、产品代码、版本号、外设ID、客户代码)")
    private PeripheralSystem peripheralSystem;

    public KeyValuePair<Integer, Object> toKeyValuePair() {
        KeyValuePair<Integer, Object> message = new KeyValuePair<>();

        if (peripheralStatus != null) {
            message.setKey(PeripheralStatus.key);
            message.setValue(peripheralStatus);

        } else if (peripheralSystem != null) {
            message.setKey(PeripheralSystem.key);
            message.setValue(peripheralSystem);
        }
        return message;
    }

    public PeripheralStatus getPeripheralStatus() {
        return peripheralStatus;
    }

    public void setPeripheralStatus(PeripheralStatus peripheralStatus) {
        this.peripheralStatus = peripheralStatus;
    }

    public PeripheralSystem getPeripheralSystem() {
        return peripheralSystem;
    }

    public void setPeripheralSystem(PeripheralSystem peripheralSystem) {
        this.peripheralSystem = peripheralSystem;
    }
}