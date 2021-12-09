package org.yzh.commons.mybatis;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
public class PageInfo {

    @Schema(description = "当前页码", maxProperties = 111)
    private int page = 1;
    @Schema(description = "每页显示行数", maxProperties = 112)
    private int limit = 5;
    @Schema(description = "是否显示总页数", maxProperties = 113)
    private boolean showPages = true;
    @Schema(description = "是否有下一页", hidden = true)
    private boolean hasNext;
    @Schema(description = "总行数", hidden = true)
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

    public boolean isHasNext() {
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
        int pages = pages();
        this.setHasNext(page < pages);
        if (count != 0 && page > pages)
            this.setPage(pages);
    }

    /** @return 总页数 */
    public int pages() {
        return (count - 1) / limit + 1;
    }

    /** @return 当前页偏移量 */
    public int offset() {
        return (page - 1) * limit;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("page:").append(page);
        sb.append(",pages:").append(pages());
        sb.append(",limit:").append(limit);
        sb.append(",offset:").append(offset());
        sb.append(",count:").append(count);
        sb.append("}");
        return sb.toString();
    }
}