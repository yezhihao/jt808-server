package org.yzh.protocol.commons.transform;

import org.yzh.framework.orm.IdStrategy;
import org.yzh.framework.orm.PrepareLoadStrategy;
import org.yzh.protocol.commons.transform.passthrough.PeripheralStatus;
import org.yzh.protocol.commons.transform.passthrough.PeripheralSystem;

/**
 * 透传消息注册
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class PassthroughType extends PrepareLoadStrategy {

    public static final IdStrategy INSTANCE = new PassthroughType();

    @Override
    protected void addSchemas(PrepareLoadStrategy schemaRegistry) {
        schemaRegistry
                .addSchema(PeripheralStatus.ID, PeripheralStatus.class)
                .addSchema(PeripheralSystem.ID, PeripheralSystem.class);
    }
}