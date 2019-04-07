package org.yzh.web.jt808.dto;

import org.yzh.framework.annotation.Type;
import org.yzh.framework.message.PackageData;
import org.yzh.web.jt808.common.MessageId;
import org.yzh.web.jt808.dto.basics.Header;

@Type(MessageId.终端心跳)
public class TerminalHeartbeat extends PackageData<Header> {

}