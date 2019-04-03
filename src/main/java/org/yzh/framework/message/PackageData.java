package org.yzh.framework.message;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class PackageData<T extends AbstractHeader> {

    protected T header;

    public PackageData() {
    }

    public PackageData(T header) {
        this.header = header;
    }

    @JsonIgnore
    public T getHeader() {
        return header;
    }

    public void setHeader(T header) {
        this.header = header;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}