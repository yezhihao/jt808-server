package org.yzh.component.area.model;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class TArea {

    private Area delegate;

    public TArea(Area delegate) {
        this.delegate = delegate;
    }

    public TArea update(Area delegate) {
        this.delegate = delegate;
        return this;
    }

    public Area delegate() {
        return delegate;
    }
}