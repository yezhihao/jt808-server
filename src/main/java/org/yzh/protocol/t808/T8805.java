package org.yzh.protocol.t808;

import org.yzh.framework.orm.model.DataType;
import org.yzh.framework.orm.annotation.Field;
import org.yzh.framework.orm.annotation.Message;
import org.yzh.framework.orm.model.AbstractMessage;
import org.yzh.protocol.basics.Header;
import org.yzh.protocol.commons.JT808;

/**
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt-server
 */
@Message(JT808.单条存储多媒体数据检索上传命令)
public class T8805 extends AbstractMessage<Header> {

    private Integer id;
    private Integer deleteSign;

    @Field(index = 0, type = DataType.DWORD, desc = "多媒体ID")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Field(index = 4, type = DataType.BYTE, desc = "删除标志:0.保留；1.删除；")
    public Integer getDeleteSign() {
        return deleteSign;
    }

    public void setDeleteSign(Integer deleteSign) {
        this.deleteSign = deleteSign;
    }
}