package org.yzh.commons.mybatis;

import java.util.List;
import java.util.function.Supplier;

/**
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
public class Page {

    private static final ThreadLocal<PageInfo> LOCAL_PAGE = new ThreadLocal<>();

    public static <T> Pagination<T> start(Supplier<List<T>> select, int page, int limit) {
        return start(select, new PageInfo(page, limit));
    }

    public static <T> Pagination<T> start(Supplier<List<T>> select, PageInfo pageInfo) {
        try {
            LOCAL_PAGE.set(pageInfo);
            List<T> list = select.get();
            return new Pagination<>(pageInfo, list);
        } finally {
            LOCAL_PAGE.remove();
        }
    }

    protected static PageInfo get() {
        return LOCAL_PAGE.get();
    }
}