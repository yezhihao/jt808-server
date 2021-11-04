package org.yzh.commons.mybatis;

import io.swagger.v3.oas.annotations.media.Schema;
import org.yzh.commons.model.APIResult;

import java.io.Serializable;
import java.util.List;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class Pagination<E> extends APIResult<List<E>> implements Serializable {

    @Schema(description = "当前页码")
    private Integer page;
    @Schema(description = "总页数")
    private Integer pages;
    @Schema(description = "总行数")
    private Integer count;
    @Schema(description = "是否有下一页")
    private Boolean hasNext;

    public Pagination() {
    }

    public Pagination(PageInfo pageInfo, List<E> list) {
        if (pageInfo != null) {
            if (pageInfo.isShowPages()) {
                this.hasNext = pageInfo.hasNext();
            } else {
                //若无需展示总页数，则PageInfo中的hasNext 以大于limit一条记录的存在与否来决定
                this.hasNext = list.size() > pageInfo.getLimit();
                if (hasNext) {
                    list.remove(list.size() - 1);
                }
            }

            this.page = pageInfo.getPage();
            this.pages = pageInfo.getPages();
            this.count = pageInfo.getCount();
        }
        this.data = list;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Boolean getHasNext() {
        return hasNext;
    }

    public void setHasNext(Boolean hasNext) {
        this.hasNext = hasNext;
    }

    @Override
    public String toString() {
        int size = data == null ? 0 : data.size();
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("page:").append(page);
        sb.append(",pages:").append(pages);
        sb.append(",count:").append(count);
        sb.append(",hasNext:").append(hasNext);
        sb.append(",listSize:").append(size);
        sb.append("}");
        return sb.toString();
    }
}