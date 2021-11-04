package org.yzh.commons.mybatis;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
@JsonIgnoreType
public class PageInfo {

    @Schema(description = "当前页码")
    private int page = 1;
    @Schema(description = "每页显示行数")
    private int limit = 5;
    @Schema(description = "是否显示总页数")
    private boolean showPages = true;
    //是否有下一页
    @JsonIgnore
    private boolean hasNext;
    //总行数
    @JsonIgnore
    private int count;

    public PageInfo() {
    }

    public PageInfo(int page) {
        if (page > 0)
            this.page = page;
    }

    public PageInfo(int page, int limit) {
        this(page);
        if (limit > 0)
            this.limit = limit;
    }

    public PageInfo(int page, int limit, boolean showPages) {
        this(page);
        if (limit > 0)
            this.limit = limit;
        this.showPages = showPages;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        if (page > 0)
            this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        if (limit > 0)
            this.limit = limit;
    }

    public boolean isShowPages() {
        return showPages;
    }

    public void setShowPages(boolean showPages) {
        this.showPages = showPages;
    }

    public boolean hasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
        int pages = getPages();
        this.hasNext = page < pages;
        if (count != 0 && page > pages)
            page = pages;
    }

    /**
     * 获取总页数
     */
    @JsonIgnore
    public int getPages() {
        return (count - 1) / limit + 1;
    }

    /**
     * 获取当页偏移量
     */
    @JsonIgnore
    public int getOffset() {
        return (page - 1) * limit;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("page:").append(page);
        sb.append(",pages:").append(getPages());
        sb.append(",limit:").append(limit);
        sb.append(",offset:").append(getOffset());
        sb.append(",count:").append(count);
        sb.append("}");
        return sb.toString();
    }
}