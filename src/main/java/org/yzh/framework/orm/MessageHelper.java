package org.yzh.framework.orm;

import java.util.Map;

/**
 * 消息ID关系映射
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class MessageHelper {

    private static volatile boolean Initial = false;

    private static LoadStrategy LOAD_STRATEGY = new DefaultLoadStrategy();

    public static void initial(String basePackage) {
        if (!Initial) {
            synchronized (MessageHelper.class) {
                if (!Initial) {
                    Initial = true;
                    LOAD_STRATEGY = new DefaultLoadStrategy(basePackage);
                }
            }
        }
    }

    public static BeanMetadata getBeanMetadata(Object typeId, Integer version) {
        return LOAD_STRATEGY.getBeanMetadata(typeId, version);
    }

    public static BeanMetadata getBeanMetadata(Class<?> typeClass, Integer version) {
        return LOAD_STRATEGY.getBeanMetadata(typeClass, version);
    }

    public static <T> Map<Integer, BeanMetadata<T>> getBeanMetadata(Class<T> typeClass) {
        return LOAD_STRATEGY.getBeanMetadata(typeClass);
    }
}