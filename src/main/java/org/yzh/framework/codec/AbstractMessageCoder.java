package org.yzh.framework.codec;

import org.yzh.framework.annotation.Property;
import org.yzh.framework.commons.bean.BeanUtils;
import org.yzh.framework.commons.bean.Cache;

import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 基础消息解码
 */
public abstract class AbstractMessageCoder {

    private static final Cache<Class<?>, PropertyDescriptor[]> propertyDescriptorCache = new Cache(32);

    protected Charset charset;

    public AbstractMessageCoder(Charset charset) {
        this.charset = charset;
    }

    public static PropertyDescriptor[] getPropertyDescriptor(Class<?> key) {
        return propertyDescriptorCache.get(key, () -> {
            BeanInfo beanInfo = BeanUtils.getBeanInfo(key);
            PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
            List<PropertyDescriptor> result = new ArrayList(pds.length);

            for (PropertyDescriptor pd : pds)
                if (pd.getReadMethod().isAnnotationPresent(Property.class))
                    result.add(pd);

            Collections.sort(result, new PropertyDescriptorComparator());
            return result.toArray(new PropertyDescriptor[result.size()]);
        });
    }

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

    private static class PropertyDescriptorComparator implements Comparator<PropertyDescriptor> {
        @Override
        public int compare(PropertyDescriptor pd1, PropertyDescriptor pd2) {
            Property p1 = pd1.getReadMethod().getAnnotation(Property.class);
            Property p2 = pd2.getReadMethod().getAnnotation(Property.class);
            if (p1.index() < p2.index())
                return -1;
            if (p1.index() > p2.index())
                return 1;
            return 0;
        }
    }
}