package org.yzh.web.jt808.dto.position;

public interface Attribute {

    int getAttributeId();

    <T extends Attribute> T formBytes(byte... bytes);

    byte[] toBytes();
}