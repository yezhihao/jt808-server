package org.yzh.web.jt.t808;

import org.yzh.framework.orm.annotation.Property;
import org.yzh.framework.orm.annotation.Type;
import org.yzh.framework.enums.DataType;
import org.yzh.framework.orm.model.AbstractBody;
import org.yzh.web.jt.common.JT808;
import org.yzh.web.jt.basics.TerminalParameter;

import java.util.List;

/**
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt-server
 */
@Type(JT808.设置终端参数)
public class T8103 extends AbstractBody {

    private Integer total;
    private List<TerminalParameter> parameters;

    @Property(index = 0, type = DataType.BYTE, desc = "参数总数")
    public Integer getTotal() {
        if (parameters == null || parameters.isEmpty())
            return 0;
        return parameters.size();
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @Property(index = 1, type = DataType.LIST, desc = "参数项列表")
    public List<TerminalParameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<TerminalParameter> parameters) {
        this.parameters = parameters;
    }
}