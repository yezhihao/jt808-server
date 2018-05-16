package org.yzh.framework.commons.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yzh.framework.commons.lang.RandomUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 使用jdk自带内省操作Bean
 * Created by Alan.ye on 2016/9/7.
 */
public class BeanUtils {

    private static final Logger log = LoggerFactory.getLogger(BeanUtils.class.getSimpleName());
    private static final List<String> ignores = new ArrayList();
    private static Cache<Class<?>, BeanInfo> beanInfoCache = new Cache(32);

    static {
        ignores.add("class");
        ignores.add("hibernateLazyInitializer");
        ignores.add("handler");
        ignores.add("fieldHandler");
    }

    public static <T> T copyBean(Object source, T target) {
        return copyBean(source, target, null);
    }

    public static <T> T copyBean(Object source, T target, String ignore) {
        Map<String, Object> map = toMap(source, ignore);
        return copyBean(map, target);
    }

    public static <T> T copyBeanNotNull(Object source, T target) {
        Map<String, Object> map = toMapNotNull(source);
        return copyBeanNotNull(map, target);
    }

    public static <T> T copyBean(Map<String, Object> source, T target) {
        BeanInfo beanInfo = getBeanInfo(target.getClass());
        PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor pd : pds) {
            String name = pd.getName();
            if (ignores.contains(name) || !source.containsKey(name))
                continue;
            Object value = source.get(name);
            setValue(target, pd.getWriteMethod(), value);
        }
        return target;
    }

    public static <T> T copyBeanNotNull(Map<String, Object> source, T target) {
        BeanInfo beanInfo = getBeanInfo(target.getClass());
        PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor pd : pds) {
            if (ignores.contains(pd.getName()))
                continue;
            Object value = source.get(pd.getName());
            if (value != null) {
                setValue(target, pd.getWriteMethod(), value);
            }
        }
        return target;
    }

    public static void cleanEmpty(Object obj) {
        BeanInfo beanInfo = getBeanInfo(obj.getClass());
        PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor pd : pds) {
            if (ignores.contains(pd.getName()))
                continue;
            Object value = getValue(obj, pd.getReadMethod());
            if ("".equals(value)) {
                setValue(obj, pd.getWriteMethod(), null);
            }
        }
    }

    public static Map<String, Object> toMap(Object obj, String ignore) {
        if (obj == null)
            return null;
        Map<String, Object> result = new HashMap<>();

        BeanInfo beanInfo = getBeanInfo(obj.getClass());
        PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor pd : pds) {
            String name = pd.getName();
            if (ignores.contains(name))
                continue;
            if (ignore != null && ignore.contains(name))
                continue;

            Object value = getValue(obj, pd.getReadMethod());
            result.put(name, value);
        }
        return result;
    }

    public static Map<String, Object> toMapNotNull(Object obj) {
        if (obj == null)
            return null;

        Map<String, Object> result = new HashMap<>();

        BeanInfo beanInfo = getBeanInfo(obj.getClass());
        PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor pd : pds) {
            String name = pd.getName();
            if (ignores.contains(name))
                continue;

            Object value = getValue(obj, pd.getReadMethod());
            if (value != null)
                result.put(name, value);
        }
        return result;
    }

    public static Object getValue(Object obj, String name, Object defValue) {
        Object value = getValue(obj, name);
        if (value != null)
            return value;
        return defValue;
    }

    public static Object getValue(Object obj, String name) {
        if (obj == null)
            return null;

        BeanInfo beanInfo = getBeanInfo(obj.getClass());
        PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor pd : pds)
            if (name.equals(pd.getName()))
                return getValue(obj, pd.getReadMethod());
        return null;
    }

    public static Object getValue(Object obj, Method getter) {
        if (getter != null)
            try {
                return getter.invoke(obj);
            } catch (Exception e) {
                log.error("获取对象值失败", e);
            }
        return null;
    }

    public static void setValue(Object obj, String name, Object value) {
        if (obj == null)
            return;

        BeanInfo beanInfo = getBeanInfo(obj.getClass());
        PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor pd : pds)
            if (name.equals(pd.getName())) {
                setValue(obj, pd.getWriteMethod(), value);
                return;
            }
    }

    public static void setValue(Object obj, Method setter, Object value) {

        if (setter != null) {
            if (value != null) {
                if (!setter.getParameterTypes()[0].isAssignableFrom(value.getClass())) {
                    log.error("设置对象值失败,类型不匹配{}", setter);
                    return;
                }
            }

            try {
                setter.invoke(obj, value);
            } catch (Exception e) {
                log.error("设置对象值失败", e);
            }
        }
    }

    public static Method getReadMethod(Class<?> key, String propertyName) {
        return getPropertyDescriptor(key, propertyName).getReadMethod();
    }

    public static Method getWriteMethod(Class<?> key, String propertyName) {
        return getPropertyDescriptor(key, propertyName).getWriteMethod();
    }

    public static PropertyDescriptor getPropertyDescriptor(Class<?> key, String propertyName) {
        BeanInfo beanInfo = getBeanInfo(key);
        PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor pd : pds) {
            if (pd.getName().equals(propertyName))
                return pd;
        }
        throw new RuntimeException("NoSuchProperty:" + propertyName);
    }

    public static BeanInfo getBeanInfo(Class<?> key) {
        return beanInfoCache.get(key, () -> {
            BeanInfo info = Introspector.getBeanInfo(key);
            Introspector.flushCaches();
            return info;
        });
    }

    public static <T> T newInstance(Class<T> target) {
        try {
            return target.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 生成供测试使用的Bean对象
     * 对象内部的属性均为随机数，默认对象深度为2
     */
    public static <T> T genBean(Class<T> target) throws Exception {
        return genBean(target, 2);
    }

    /**
     * 生成供测试使用的Bean对象
     * 对象内部的属性均为随机数，默认对象深度为2
     *
     * @param target 目标类类型
     * @param depth  对象深度（若对象内存在其他对象，深度则控制对象内对象的嵌套层数）
     */
    public static <T> T genBean(Class<T> target, int depth) throws Exception {
        if (depth-- < 1)
            return null;
        T result = target.newInstance();

        PropertyDescriptor[] pds = getBeanInfo(target).getPropertyDescriptors();
        for (PropertyDescriptor pd : pds) {
            Class<?> clazz = pd.getPropertyType();
            if (clazz.isAssignableFrom(Class.class))
                continue;
            Object value = genBaseData(clazz, depth, target, pd.getName());
            setValue(result, pd.getWriteMethod(), value);
        }
        return result;
    }

    private static <T> Object genBaseData(Class<T> clazz, int depth, Class rootClass, String name) throws Exception {
        ThreadLocalRandom random = ThreadLocalRandom.current();

        if (clazz.isAssignableFrom(Boolean.class) || clazz.isAssignableFrom(Boolean.TYPE))
            return random.nextBoolean();
        if (clazz.isAssignableFrom(Byte.class) || clazz.isAssignableFrom(Byte.TYPE))
            return (byte) random.nextInt();
        if (clazz.isAssignableFrom(Short.class) || clazz.isAssignableFrom(Short.TYPE))
            return (short) random.nextInt();
        if (clazz.isAssignableFrom(Integer.class) || clazz.isAssignableFrom(Integer.TYPE))
            return random.nextInt();
        if (clazz.isAssignableFrom(Long.class) || clazz.isAssignableFrom(Long.TYPE))
            return random.nextLong();
        if (clazz.isAssignableFrom(Float.class) || clazz.isAssignableFrom(Float.TYPE))
            return random.nextFloat();
        if (clazz.isAssignableFrom(Double.class) || clazz.isAssignableFrom(Double.TYPE))
            return random.nextDouble();
        if (clazz.isAssignableFrom(Character.class) || clazz.isAssignableFrom(Character.TYPE))
            return RandomUtils.nextString(1).charAt(0);
        if (clazz.isAssignableFrom(String.class))
            return RandomUtils.nextString(9);
        if (clazz.isAssignableFrom(Date.class))
            return new Date();
        if (clazz.isAssignableFrom(List.class)) {
            Class x = getGenericType(rootClass, name);
            Object o = genBaseData(x, depth, null, null);

            ArrayList list = new ArrayList(2);
            list.add(o);
            list.add(o);
            return list;
        }
        if (clazz.isArray()) {
            Object array = Array.newInstance(clazz.getComponentType(), 2);
            Object o = genBaseData(clazz.getComponentType(), depth, null, null);
            Array.set(array, 0, o);
            Array.set(array, 1, o);
            return array;
        }
        if (clazz.isEnum()) {
            Method method = clazz.getMethod("values");
            Object[] values = (Object[]) method.invoke(null);
            return values[random.nextInt(values.length)];
        }
        return genBean(clazz, depth);
    }

    /**
     * 获得泛型属性的泛型类型
     *
     * @param rootClass 根对象
     * @param fieldName 属性名
     * @return
     */
    public static Class getGenericType(Class rootClass, String fieldName) {
        try {
            Field field = rootClass.getDeclaredField(fieldName);
            ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
            return (Class) parameterizedType.getActualTypeArguments()[0];
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

}