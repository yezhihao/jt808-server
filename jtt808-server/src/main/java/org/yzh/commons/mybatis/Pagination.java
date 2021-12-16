package org.yzh.commons.mybatis;

import io.swagger.v3.oas.annotations.media.Schema;
import org.yzh.commons.model.APIResult;

import java.io.Serializable;
import java.util.List;

/**
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
public class Pagination<E> extends APIResult<List<E>> implements Serializable {

    @Schema(description = "当前页码")
    private int page;
    @Schema(description = "总页数")
    private int pages;
    @Schema(description = "总行数")
    private int count;
    @Schema(description = "是否有下一页")
    private boolean hasNext;

    public Pagination(PageInfo pageInfo, List<E> list) {
        if (pageInfo != null) {
            if (pageInfo.isShowPages()) {
                this.hasNext = pageInfo.isHasNext();
            } else {
                //若无需展示总页数，则PageInfo中的hasNext 以大于limit一条记录的存在与否来决定
                this.hasNext = list.size() > pageInfo.getLimit();
                if (hasNext) {
                    list.remove(list.size() - 1);
                }
            }

            this.page = pageInfo.getPage();
            this.pages = pageInfo.pages();
            this.count = pageInfo.getCount();
        }
        this.data = list;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    @Override
    public String toString() {
        int size = data == null ? 0 : data.size();
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        sb.append("page:").append(page);
        sb.append(",pages:").append(pages);
        sb.append(",count:").append(count);
        sb.append(",hasNext:").append(hasNext);
        sb.append(",listSize:").append(size);
        sb.append('}');
        return sb.toString();
    }
}