package org.yzh.web.jt808.dto;

import org.yzh.framework.annotation.Property;
import org.yzh.framework.enums.DataType;
import org.yzh.framework.message.PackageData;
import org.yzh.web.jt808.dto.basics.Header;

/**
 * 终端控制
 */
public class TerminalControl extends PackageData<Header> {

    private Integer command;

    private String parameter;

    @Property(index = 0, type = DataType.BYTE, desc = "命令字")
    public Integer getCommand() {
        return command;
    }

    public void setCommand(Integer command) {
        this.command = command;
    }

    @Property(index = 1, type = DataType.STRING, desc = "命令参数")
    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }
}