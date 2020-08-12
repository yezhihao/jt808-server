package org.yzh.web.component.mybatis;

import java.util.List;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class Page {

    private static final ThreadLocal<PageInfo> LOCAL_PAGE = new ThreadLocal<>();

    public static Pagination start(ISelect select, int page, int limit) {
        return start(select, new PageInfo(page, limit));
    }

    public static Pagination start(ISelect select, PageInfo pageInfo) {
        try {
            LOCAL_PAGE.set(pageInfo);
            List<?> list = select.select();
            return new Pagination<>(pageInfo, list);
        } catch (Exception e) {
            throw e;
        } finally {
            LOCAL_PAGE.remove();
        }
    }

    protected static PageInfo get() {
        return LOCAL_PAGE.get();
    }

    @FunctionalInterface
    public interface ISelect {
        List<?> select();
    }
}