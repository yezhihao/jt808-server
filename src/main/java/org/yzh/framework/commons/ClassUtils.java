package org.yzh.framework.commons;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.File;
import java.io.FileFilter;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ClassUtils {

    private static final Logger logger = LoggerFactory.getLogger(ClassUtils.class.getSimpleName());

    public static List<Class<?>> getClassList(String packageName, Class<? extends Annotation> annotationClass) {
        List<Class<?>> classList = getClassList(packageName);
        Iterator<Class<?>> iterator = classList.iterator();
        while (iterator.hasNext()) {
            Class<?> next = iterator.next();
            if (!next.isAnnotationPresent(annotationClass))
                iterator.remove();
        }
        return classList;
    }

	public static List<Class<?>> getClassList(String packageName) {
		List<Class<?>> classList = new LinkedList();
		try {

			ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
			Resource[] resources = resourcePatternResolver.getResources(packageName.replace(".", "/") + "/**/*.class");
			for (Resource resource : resources) {
				String url = resource.getURL().toString();
				String className = url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf("."));
				doAddClass(classList, packageName + "." + className);
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return classList;
	}

    private static void addClass(List<Class<?>> classList, String packagePath, String packageName) {
        try {
            File[] files = new File(packagePath).listFiles(new FileFilter() {
                @Override
                public boolean accept(File file) {
                    return (file.isFile() && file.getName().endsWith(".class")) || file.isDirectory();
                }
            });
            if (files != null)
                for (File file : files) {
                    String fileName = file.getName();
                    if (file.isFile()) {
                        String className = fileName.substring(0, fileName.lastIndexOf("."));
                        if (StringUtils.isNotEmpty(packageName)) {
                            className = packageName + "." + className;
                        }
                        doAddClass(classList, className);
                    } else {
                        String subPackagePath = fileName;
                        if (StringUtils.isNotEmpty(packagePath)) {
                            subPackagePath = packagePath + "/" + subPackagePath;
                        }
                        String subPackageName = fileName;
                        if (StringUtils.isNotEmpty(packageName)) {
                            subPackageName = packageName + "." + subPackageName;
                        }
                        addClass(classList, subPackagePath, subPackageName);
                    }
                }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void doAddClass(List<Class<?>> classList, String className) {
        Class<?> cls = loadClass(className, false);
        classList.add(cls);
    }

    public static Class<?> loadClass(String className, boolean isInitialized) {
        Class<?> cls;
        try {
            cls = Class.forName(className, isInitialized, getClassLoader());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return cls;
    }

    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

}