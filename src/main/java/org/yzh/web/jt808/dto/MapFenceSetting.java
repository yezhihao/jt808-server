package org.yzh.web.jt808.dto;

import org.yzh.framework.annotation.Property;
import org.yzh.framework.enums.DataType;
import org.yzh.framework.message.PackageData;
import org.yzh.web.jt808.dto.basics.Header;

import java.util.ArrayList;
import java.util.List;

/**
 * 区域设置
 */
public class MapFenceSetting<T> extends PackageData<Header> {

    //更新（先清空，后追加）
    public static final int Update = 0;
    //追加
    public static final int Append = 1;
    //修改
    public static final int Modify = 2;

    private Integer type;

    private Integer total;

    private List<T> list;

    @Property(index = 0, type = DataType.BYTE, desc = "设置类型")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Property(index = 1, type = DataType.BYTE, desc = "区域总数")
    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @Property(index = 2, type = DataType.LIST, desc = "区域列表")
    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
        this.total = list.size();
    }

    public void addMapFence(long id) {
        if (this.list == null)
            this.list = new ArrayList();
        this.list.add((T) new MapFence(id));
        this.total = list.size();
    }

    public void addMapFence(T mapFence) {
        if (this.list == null)
            this.list = new ArrayList();
        this.list.add(mapFence);
        this.total = list.size();
    }

}