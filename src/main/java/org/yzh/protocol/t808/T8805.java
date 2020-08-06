package org.yzh.protocol.t808;

import org.yzh.framework.orm.annotation.Field;
import org.yzh.framework.orm.annotation.Message;
import org.yzh.framework.orm.model.AbstractMessage;
import org.yzh.framework.orm.model.DataType;
import org.yzh.protocol.basics.Header;
import org.yzh.protocol.commons.JT808;

/**
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt-server
 */
@Message(JT808.单条存储多媒体数据检索上传命令)
public class T8805 extends AbstractMessage<Header> {

    private int id;
    private int delete;

    public T8805() {
    }

    public T8805(String clientId, int id, int delete) {
        super(new Header(clientId, JT808.单条存储多媒体数据检索上传命令));
        this.id = id;
        this.delete = delete;
    }

    @Field(index = 0, type = DataType.DWORD, desc = "多媒体ID")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Field(index = 4, type = DataType.BYTE, desc = "删除标志:0.保留；1.删除；")
    public int getDelete() {
        return delete;
    }

    public void setDelete(int delete) {
        this.delete = delete;
    }
}