package org.yzh.protocol.jsatl12;

import io.github.yezhihao.protostar.annotation.Field;
import io.github.yezhihao.protostar.annotation.Message;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.commons.JSATL12;

/**
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
@Message({JSATL12.文件信息上传, JSATL12.文件上传完成消息})
public class T1211 extends JTMessage {

    @Field(lengthSize = 1, desc = "文件名称")
    private String name;
    @Field(length = 1, desc = "文件类型 0.图片 1.音频 2.视频 3.文本 4.面部特征图片(粤标) 5.其它")
    private int type;
    @Field(length = 4, desc = "文件大小")
    private long size;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}