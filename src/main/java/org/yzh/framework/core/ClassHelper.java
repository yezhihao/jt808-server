package org.yzh.framework.core;

import org.yzh.framework.commons.ClassUtils;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt-server
 */
public class ClassHelper {

    /**
     * 获取基础包名
     */
    private static String basePackage = "org.yzh.web.jt.t808";

    public static void init(String packageName) {
        basePackage = packageName;
    }

    public static List<Class<?>> getClassList() {
        return ClassUtils.getClassList(basePackage);
    }

    public static List<Class<?>> getClassListByAnnotation(Class<? extends Annotation> annotationClass) {
        return ClassUtils.getClassList(basePackage, annotationClass);
    }
}
