package org.yzh.framework.commons;

import org.yzh.framework.orm.annotation.Property;
import org.yzh.framework.commons.bean.BeanUtils;

/**
 * 获取消息模型的属性定义
 *
 * @author zhihao.ye (1527621790@qq.com)
 */
public abstract class PropertyUtils {

    public static int getLength(Object obj, Property prop) {
        int length = prop.length();
        if (length == -1)
            if ("".equals(prop.lengthName()))
                length = prop.type().length;
            else
                length = (int) BeanUtils.getValue(obj, prop.lengthName(), 0);
        return length;
    }

    public static int getIndex(Object obj, Property prop) {
        int index = prop.index();
        for (String name : prop.indexOffsetName())
            if (!"".equals(name))
                index += (int) BeanUtils.getValue(obj, name, 0);
        return index;
    }
}