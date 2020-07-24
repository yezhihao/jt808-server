package org.yzh.framework.orm;

import org.yzh.framework.commons.bean.BeanUtils;
import org.yzh.framework.orm.annotation.Field;

/**
 * 获取消息模型的属性定义
 *
 * @author zhihao.ye (1527621790@qq.com)
 */
public abstract class PropertyUtils {

    public static int getLength(Object obj, Field field) {
        int length = field.length();
        if (length == -1)
            if ("".equals(field.lengthName()))
                length = field.type().length;
            else
                length = (int) BeanUtils.getValue(obj, field.lengthName(), 0);
        return length;
    }

    public static int getIndex(Object obj, Field field) {
        int index = field.index();
        for (String name : field.indexOffsetName())
            if (!"".equals(name))
                index += (int) BeanUtils.getValue(obj, name, 0);
        return index;
    }
}