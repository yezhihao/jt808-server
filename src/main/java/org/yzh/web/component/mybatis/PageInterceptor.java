package org.yzh.web.component.mybatis;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

/**
 * MyBatis分页拦截器
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
@Intercepts(@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class}))
public class PageInterceptor implements Interceptor {

    private static final Logger log = LoggerFactory.getLogger(PageInterceptor.class);

    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        MetaObject metaObject = SystemMetaObject.forObject(statementHandler);

        if (SqlCommandType.SELECT == metaObject.getValue("delegate.mappedStatement.sqlCommandType")) {
            BoundSql boundSql = statementHandler.getBoundSql();
            Object parameterObject = boundSql.getParameterObject();

            PageInfo pageInfo = getPageInfo(parameterObject);

            if (pageInfo != null) {
                String sql = boundSql.getSql();
                if (pageInfo.isShowPages()) {
                    int totalRows = getTotalRows(sql, (Connection) invocation.getArgs()[0], statementHandler.getParameterHandler());
                    pageInfo.setCount(totalRows);
                }
                String pageSql = buildPageSql(sql, pageInfo);
                metaObject.setValue("delegate.boundSql.sql", pageSql);
            }
        }
        return invocation.proceed();
    }

    public static PageInfo getPageInfo(Object parameterObject) {
        PageInfo pageInfo = Page.get();
        if (pageInfo == null)
            if (parameterObject instanceof Map) {
                Map parameterMap = (Map) parameterObject;
                if (parameterMap.containsKey("pageInfo"))
                    pageInfo = (PageInfo) parameterMap.get("pageInfo");
            }
        return pageInfo;
    }

    /**
     * 获取总行数
     */
    private static int getTotalRows(String sql, Connection connection, ParameterHandler handler) {
        String countSql = new StringBuilder(sql.length() + 32)
                .append("select count(*) from (").append(sql).append(") as total").toString();

        int totalCount = 0;
        try (PreparedStatement statement = connection.prepareStatement(countSql)) {
            handler.setParameters(statement);
            ResultSet rs = statement.executeQuery();
            if (rs.next())
                totalCount = rs.getInt(1);
            rs.close();
        } catch (SQLException e) {
            log.error("SELECT COUNT(1) ERROR", e);
        }
        return totalCount;
    }

    /**
     * 构建分页SQL
     * 若无需展示总页数，则PageInfo中的hasNext 以大于limit一条记录的存在与否来决定
     */
    private static String buildPageSql(String sql, PageInfo page) {
        int limit = page.isShowPages() ? page.getLimit() : page.getLimit() + 1;
        return new StringBuilder(sql.length() + 14)
                .append(sql).append(" limit ")
                .append(page.getOffset()).append(",")
                .append(limit).toString();
    }

    @Override
    public void setProperties(Properties properties) {
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof StatementHandler)
            return Plugin.wrap(target, this);
        return target;
    }
}